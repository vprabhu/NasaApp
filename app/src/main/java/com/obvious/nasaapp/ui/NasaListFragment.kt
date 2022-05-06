package com.obvious.nasaapp.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.obviouc.network.api.ApiHelper
import com.obviouc.network.api.RetrofitBuilder
import com.obviouc.network.utils.Status
import com.obvious.nasaapp.adapter.NasaListAdapter
import com.obvious.nasaapp.databinding.FragmentNasaListBinding
import com.obvious.nasaapp.viewmodel.NasaViewModel
import com.obvious.nasaapp.viewmodel.ViewModelFactory


class NasaListFragment : Fragment() {

    private lateinit var viewModel: NasaViewModel
    private lateinit var _binding: FragmentNasaListBinding

    @RecyclerView.Orientation
    private var orientation = RecyclerView.VERTICAL

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
        setupObservers()
        return _binding.root
    }

    private fun setupObservers() {
        viewModel.getUsers().observe(requireActivity(), Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data.let { nasalist ->
                            Log.d(
                                "NasaListFragment",
                                "inside : NasaListFragment reverse  ${nasalist?.sortedByDescending { it.date }}"
                            )
                            var orientation: Int
                            var spanCount = 0
                            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                                spanCount = 3
                                orientation = RecyclerView.HORIZONTAL
                            } else {
                                spanCount = 2
                                orientation = RecyclerView.VERTICAL
                            }
                            val adapter =
                                NasaListAdapter(nasalist!!, NasaListAdapter.OnClickListener {
                                    // launch details fragment
                                })
                            val layoutmanager =
                                GridLayoutManager(requireActivity(), spanCount, orientation, false)
                            _binding.recyclerViewNasaItems.adapter = adapter
                            _binding.recyclerViewNasaItems.layoutManager = layoutmanager
                        }
                    }
                    Status.ERROR -> {
                        /*recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE*/
//                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        /*progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE*/
                    }
                }
            }
        })
    }

    fun calculateNoOfColumns(
        context: Context,
        columnWidthDp: Float
    ): Int { // For example columnWidthdp=180
        val displayMetrics: DisplayMetrics = context.getResources().getDisplayMetrics()
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp + 0.5).toInt()
    }

}