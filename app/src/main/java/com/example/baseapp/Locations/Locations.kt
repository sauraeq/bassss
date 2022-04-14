package com.example.baseapp.Locations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baseapp.MyBooking.BookingAdapter
import com.example.baseapp.R
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_dashboard.tvTitle
import kotlinx.android.synthetic.main.header.*

class Locations : AppCompatActivity() {
    private lateinit var rvLocation: RecyclerView
    private lateinit var list_location: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations)

        rvLocation = findViewById(R.id.rvLocation)
        tvTitle.setText("Locations")

        rlBack.setOnClickListener {
            onBackPressed()
        }
        list_location = ArrayList()
        list_location.add("")
        list_location.add("")
        list_location.add("")

        locationList()

    }

    private fun locationList() {
        rvLocation.layoutManager = LinearLayoutManager(this)
        rvLocation.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvLocation.adapter = LocationsAdapter(this, list_location)
    }
}
