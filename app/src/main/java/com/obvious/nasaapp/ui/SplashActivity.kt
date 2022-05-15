package com.obvious.nasaapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.obvious.nasaapp.R
import java.util.*


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // launch MainActivity
        launchActivityAfterDelay()
    }

    /**
     * This method launches the MainActivity after 5000 milliseconds
     */
    private fun launchActivityAfterDelay() {
        val timerTask = object : TimerTask() {
            override fun run() {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        val timer = Timer()
        timer.schedule(timerTask, 5000)
    }
}