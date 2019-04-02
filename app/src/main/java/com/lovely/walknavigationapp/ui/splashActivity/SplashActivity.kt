package com.lovely.walknavigationapp.ui.splashActivity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.LinearLayout
import com.lovely.walknavigationapp.R
import com.lovely.walknavigationapp.ui.getLocationActivity.GetLocationActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var llLayoutLoading: LinearLayout
    private val LOADING_TIME: Long = 2000L;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    override fun onResume() {
        super.onResume()

        // wait for some time and then navigate to get location screen
        waitForSomeTime()
    }

    /**
     * initialising views
     */
    private fun init() {
        llLayoutLoading = findViewById(R.id.llLayoutLoading)
    }

    /**
     * wait for some time
     */
    private fun waitForSomeTime() {

        Handler().postDelayed({
            naviagateToGetLocationScreen()
        }, LOADING_TIME)
    }

    private fun naviagateToGetLocationScreen() {

        val intentToGetLocation = Intent(this@SplashActivity, GetLocationActivity::class.java)
        startActivity(intentToGetLocation)
        finish()
    }
}
