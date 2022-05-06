package com.obvious.nasaapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.obviouc.network.api.ApiHelper
import com.obviouc.network.api.RetrofitBuilder
import com.obviouc.network.utils.Status
import com.obvious.nasaapp.R
import com.obvious.nasaapp.viewmodel.NasaViewModel
import com.obvious.nasaapp.viewmodel.ViewModelFactory

class NasaListFragment : Fragment() {
    private lateinit var viewModel: NasaViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        setupObservers()
        return inflater.inflate(R.layout.fragment_nasa_list, container, false)
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

}