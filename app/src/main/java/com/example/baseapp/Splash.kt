package com.example.baseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.baseapp.AuthenticationModule.StartActivity
import com.example.baseapp.Dashboard.Dashboard
import com.example.baseapp.Utils.SharedPreferenceUtils
import com.metaled.Utils.ConstantUtils

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(Runnable {
            //code t go new activity


            if (SharedPreferenceUtils.getInstance(this)?.getStringValue(
                    ConstantUtils.EMAIL, ""
                )?.isEmpty()!!
            ) {
                val intent = Intent(this, StartActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, Dashboard::class.java)
                startActivity(intent)
                finish()
            }

        }, 3000L)
    }
}