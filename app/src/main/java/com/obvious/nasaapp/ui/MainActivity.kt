package com.obvious.nasaapp.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.obviouc.network.api.ApiHelper
import com.obviouc.network.api.RetrofitBuilder
import com.obviouc.network.utils.Status
import com.obvious.nasaapp.R
import com.obvious.nasaapp.databinding.ActivityMainBinding
import com.obvious.nasaapp.viewmodel.NasaViewModel
import com.obvious.nasaapp.viewmodel.ViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NasaViewModel
    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(NasaViewModel::class.java)

        checkNetworkConnectivity()

    }

    private fun setupObservers() {
        viewModel.getUsers().observe(this@MainActivity) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        showListFragment()
                        resource.data.let { nasalist ->
                            supportFragmentManager.beginTransaction().replace(
                                R.id.frame_layout_container,
                                NasaListFragment()
                            ).commit()
                        }
                    }
                    Status.ERROR -> {
                        _binding.viewAnimation.visibility = View.GONE
                        _binding.viewNoInternetAnimation.visibility = View.VISIBLE
                    }
                    Status.LOADING -> {
                        _binding.viewAnimation.visibility = View.VISIBLE
                        _binding.frameLayoutContainer.visibility = View.GONE
                    }
                }
            }
        }
    }


    private fun checkNetworkConnectivity() {
        val mgr = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = mgr.activeNetworkInfo
        if (netInfo != null) {
            if (netInfo.isConnected) {
                // Internet Available
                setupObservers()
            } else {
                showNetworkError()
            }
        } else {
            showNetworkError()
        }
    }

    private fun showNetworkError() {
        _binding.viewAnimation.visibility = View.GONE
        _binding.viewNoInternetAnimation.visibility = View.VISIBLE
        Snackbar.make(
            _binding.layoutBase,
            "Please connect to network and restart application",
            Snackbar.LENGTH_INDEFINITE
        ).show()
    }

    private fun showListFragment() {
        _binding.viewAnimation.visibility = View.GONE
        _binding.frameLayoutContainer.visibility = View.VISIBLE
    }
}