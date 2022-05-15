package com.obvious.nasaapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.obviouc.network.api.ApiHelper
import com.obviouc.network.api.RetrofitBuilder
import com.obvious.nasaapp.databinding.FragmentBottomSheetItemDeatilsBinding
import com.obvious.nasaapp.viewmodel.NasaViewModel
import com.obvious.nasaapp.viewmodel.ViewModelFactory

const val ARG_ITEM_COUNT = "item_count"

class NasaItemDetailsFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetItemDeatilsBinding? = null
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
    ): View {
        _binding = FragmentBottomSheetItemDeatilsBinding.inflate(
            inflater,
            container,
            false
        )
        setupData()
        return binding.root
    }

    /**
     * This method loads the date in the respective views
     */
    private fun setupData() {
        val viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(NasaViewModel::class.java)
        val list = viewModel.nasaItemsList.sortedByDescending { it.date }
        val item = list[_selectedPosition]
        _binding?.textViewNasaDetailsTitle?.text = item.title
        if (item.copyright.isNullOrEmpty()) {
            _binding?.textViewNasaDetailsCopyright?.visibility = View.GONE
        } else {
            _binding?.textViewNasaDetailsCopyright?.text = item.copyright
        }
        _binding?.textViewNasaDetailsDescription?.text = item.explanation
        _binding?.textViewNasaDetailsDate?.text = item.date.toString()
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