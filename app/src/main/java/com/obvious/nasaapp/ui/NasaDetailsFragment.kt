package com.obvious.nasaapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.obviouc.network.api.ApiHelper
import com.obviouc.network.api.RetrofitBuilder
import com.obvious.nasaapp.R
import com.obvious.nasaapp.viewmodel.NasaViewModel
import com.obvious.nasaapp.viewmodel.ViewModelFactory


private const val ARG_PARAM1 = "param1"


class NasaDetailsFragment : Fragment() {

    private lateinit var viewModel: NasaViewModel
    private var param1: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(NasaViewModel::class.java)
        val list = viewModel.nasaItems.sortedByDescending { it.date }
        Log.d(
            "NasaListFragment",
            "inside : NasaDetailsFragment reverse  ${list}"
        )
        val view = inflater.inflate(R.layout.fragment_nasa_details, container, false)

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager_nasa_items)
        return view
    }


    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int) =
            NasaDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }

}