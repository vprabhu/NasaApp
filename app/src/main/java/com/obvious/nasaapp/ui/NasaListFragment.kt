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

    private lateinit var viewModel: NasaViewModel
    private var _binding: FragmentNasaListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNasaListBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(NasaViewModel::class.java)
        setupRecyclerVIew()
        return _binding?.root
    }

    private fun setupRecyclerVIew() {
        val orientation: Int
        var spanCount = 0
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 3
            orientation = RecyclerView.HORIZONTAL
        } else {
            spanCount = 2
            orientation = RecyclerView.VERTICAL
        }
        val list = viewModel.nasaItems.sortedByDescending { it.date }
        val adapter =
            NasaListAdapter(
                list,
                NasaListAdapter.OnClickListener { item, position ->
                    // launch details fragment
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame_layout_container,
                            NasaDetailsFragment.newInstance(position)
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
    ): Int { // For example columnWidthdp=180
        val displayMetrics: DisplayMetrics = context.getResources().getDisplayMetrics()
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp + 0.5).toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}