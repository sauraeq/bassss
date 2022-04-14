package com.example.baseapp.About

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.baseapp.R
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_dashboard.tvTitle
import kotlinx.android.synthetic.main.header.*

class About : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        tvTitle.setText("About")

        rlBack.setOnClickListener {
            onBackPressed()
        }
    }
}