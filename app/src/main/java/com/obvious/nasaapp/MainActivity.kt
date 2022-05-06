package com.obvious.nasaapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.obviouc.network.api.ApiHelper
import com.obviouc.network.api.RetrofitBuilder
import com.obviouc.network.utils.Status
import com.obvious.nasaapp.ui.NasaListFragment
import com.obvious.nasaapp.viewmodel.NasaViewModel
import com.obvious.nasaapp.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

//    private lateinit var viewModel: NasaViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(
            R.id.frame_layout_container,
            NasaListFragment()
        ).commit()

        /*viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(NasaViewModel::class.java)*/

    }




}