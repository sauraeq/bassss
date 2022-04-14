package com.example.baseapp.BookingsDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.baseapp.R
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_dashboard.tvTitle
import kotlinx.android.synthetic.main.header.*

class BookingDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_details)
        tvTitle.setText("Dumbbell rows")

        rlBack.setOnClickListener {
            onBackPressed()
        }
    }
}