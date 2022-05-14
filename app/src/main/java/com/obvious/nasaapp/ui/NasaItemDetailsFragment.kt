package com.obvious.nasaapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.obviouc.network.api.ApiHelper
import com.obviouc.network.api.RetrofitBuilder
import com.obvious.nasaapp.databinding.FragmentNasaItemDeatilsListDialogBinding
import com.obvious.nasaapp.viewmodel.NasaViewModel
import com.obvious.nasaapp.viewmodel.ViewModelFactory

// TODO: Customize parameter argument names
const val ARG_ITEM_COUNT = "item_count"

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    NasaItemDeatilsFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class NasaItemDetailsFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentNasaItemDeatilsListDialogBinding? = null
    private lateinit var viewModel: NasaViewModel
    private var _selectedPosition: Int = 0
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _selectedPosition = it.getInt(ARG_ITEM_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNasaItemDeatilsListDialogBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(NasaViewModel::class.java)
        val list = viewModel.nasaItems.sortedByDescending { it.date }
        Log.d(
            "NasaDetailsFragment",
            "inside : NasaItemDetailsFragment reverse  ${list[_selectedPosition].title}"
        )
        val item = list[_selectedPosition]
        _binding?.textViewNasaDetailsTitle?.text = list[_selectedPosition].title
        if (item.copyright.isNullOrEmpty()) {
            _binding?.textViewNasaDetailsCopyright?.visibility = View.GONE
        } else {
            _binding?.textViewNasaDetailsCopyright?.text = list[_selectedPosition].copyright
        }

        _binding?.textViewNasaDetailsDescription?.text = list[_selectedPosition].explanation
        _binding?.textViewNasaDetailsDate?.text = list[_selectedPosition].date.toString()
        return binding.root
    }

    companion object {
        fun newInstance(itemCount: Int): NasaItemDetailsFragment =
            NasaItemDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ITEM_COUNT, itemCount)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}