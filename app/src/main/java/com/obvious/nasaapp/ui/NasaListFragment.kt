package com.obvious.nasaapp.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obviouc.network.api.ApiHelper
import com.obviouc.network.api.RetrofitBuilder
import com.obvious.nasaapp.R
import com.obvious.nasaapp.adapter.NasaListAdapter
import com.obvious.nasaapp.databinding.FragmentNasaListBinding
import com.obvious.nasaapp.viewmodel.NasaViewModel
import com.obvious.nasaapp.viewmodel.ViewModelFactory


class NasaListFragment : Fragment() {

    // _biding that interacts with views
    private var _binding: FragmentNasaListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNasaListBinding.inflate(layoutInflater, container, false)
        setupRecyclerView()
        return _binding?.root
    }

    /**
     * This method  does the following
     *  - get the list from viewmodel
     *  - calculate the spanCount according to configuration
     *  - setup the recyclerview
     *  - launches the NasaImageFullScreenFragment on an item click
     */
    private fun setupRecyclerView() {
        val viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(NasaViewModel::class.java)
        val list = viewModel.nasaItemsList.sortedByDescending { it.date }
        val orientation: Int = RecyclerView.VERTICAL
        val spanCount: Int =
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                3
            } else {
                2
            }
        val adapter =
            NasaListAdapter(
                list,
                NasaListAdapter.OnClickListener { _, position ->
                    // launch details fragment
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame_layout_container,
                            NasaImageFullScreenFragment.newInstance(position)
                        ).addToBackStack(null).commit()
                })
        val layoutManager =
            GridLayoutManager(requireActivity(), spanCount, orientation, false)
        _binding?.recyclerViewNasaItems?.adapter = adapter
        _binding?.recyclerViewNasaItems?.layoutManager = layoutManager
    }

    /**
     * calculates the number of columns dynamically
     */
    fun calculateNoOfColumns(
        context: Context,
        columnWidthDp: Float
    ): Int { // For example columnWidthIndp=180
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp + 0.5).toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}