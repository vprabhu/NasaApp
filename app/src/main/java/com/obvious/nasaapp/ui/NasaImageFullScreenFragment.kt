package com.obvious.nasaapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.obviouc.network.api.ApiHelper
import com.obviouc.network.api.RetrofitBuilder
import com.obvious.nasaapp.adapter.NasaDetailsAdapter
import com.obvious.nasaapp.databinding.FragmentNasaDetailsBinding
import com.obvious.nasaapp.viewmodel.NasaViewModel
import com.obvious.nasaapp.viewmodel.ViewModelFactory


private const val ARGS_LIST_SELECTED_POSITION = "args_list_selected_position"


class NasaImageFullScreenFragment : Fragment() {

    private lateinit var viewModel: NasaViewModel
    private var _selectedPosition: Int = 0
    private var _binding: FragmentNasaDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _selectedPosition = it.getInt(ARGS_LIST_SELECTED_POSITION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNasaDetailsBinding.inflate(
            layoutInflater,
            container,
            false
        )
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(NasaViewModel::class.java)
        val list = viewModel.nasaItems.sortedByDescending { it.date }
        Log.d(
            "NasaDetailsFragment",
            "inside : NasaDetailsFragment reverse  ${list[_selectedPosition].title}"
        )
        val viewPagerAdapter =
            NasaDetailsAdapter(list,
                NasaDetailsAdapter.OnClickListener { meme, position ->
                    run {
                        val fragment = NasaItemDetailsFragment.newInstance(position)
                        fragment.show(requireActivity().supportFragmentManager, "")
                    }
                })
        _binding?.viewPagerNasaItems?.adapter = viewPagerAdapter
        _binding?.viewPagerNasaItems?.setCurrentItem(_selectedPosition, false)
        _binding?.viewPagerNasaItems?.setPageTransformer(ZoomOutPageTransformer())

        return _binding?.root
    }

    companion object {
        @JvmStatic
        fun newInstance(positionParams: Int) =
            NasaImageFullScreenFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARGS_LIST_SELECTED_POSITION, positionParams)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}