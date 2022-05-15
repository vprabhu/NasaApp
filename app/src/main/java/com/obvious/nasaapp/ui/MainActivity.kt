package com.obvious.nasaapp.ui

import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
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

    // _biding that interacts with views
    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        // checks the network connectivity
        checkNetworkConnectivity()
    }

    /**
     * This method does the foloowing
     * - gets the info from API
     * - load the NasaListFragment via viewmodel and livedata
     */
    private fun setupObservers() {
        val viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(NasaViewModel::class.java)
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

    /**
     * This method checks network connectivity and does the following
     *  - loads the list fragment when internet is connected
     *  - shows network connectivity error when internet is disconnected
     */
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

    /**
     * This method does the following
     *  - shows no internet animation
     *  - shows Snackbar to show the relaunch info to user
     */
    private fun showNetworkError() {
        _binding.viewAnimation.visibility = View.GONE
        _binding.viewNoInternetAnimation.visibility = View.VISIBLE
        Snackbar.make(
            _binding.layoutBase,
            "Please connect to network and restart application",
            Snackbar.LENGTH_INDEFINITE
        ).show()
    }

    /**
     * This method shows fragment container and hides loading animation
     */
    private fun showListFragment() {
        _binding.viewNoInternetAnimation.visibility = View.GONE
        _binding.viewAnimation.visibility = View.GONE
        _binding.frameLayoutContainer.visibility = View.VISIBLE
    }
}