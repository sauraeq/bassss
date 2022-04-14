package com.example.baseapp.MyBooking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baseapp.BookingsDetails.BookingDetails
import com.example.baseapp.Locations.Locations
import com.example.baseapp.R
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_dashboard.tvTitle
import kotlinx.android.synthetic.main.activity_my_booking.*
import kotlinx.android.synthetic.main.booking.*
import kotlinx.android.synthetic.main.header.*

class MyBooking : AppCompatActivity() {
     lateinit var cvBooking: CardView
     lateinit var list_Booking: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_my_booking)

        tvTitle.setText("Bookings")

        rlBack.setOnClickListener {
            onBackPressed()

        }

        list_Booking = ArrayList()
        list_Booking.add("")
        list_Booking.add("")
        list_Booking.add("")

        bookingsList()
    }

    private fun bookingsList() {
        rvBooking?.layoutManager = LinearLayoutManager(this)
        rvBooking?.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        rvBooking?.adapter= BookingAdapter(this, list_Booking)
    }
}





