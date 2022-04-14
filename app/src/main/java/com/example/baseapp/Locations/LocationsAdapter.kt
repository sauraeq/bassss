package com.example.baseapp.Locations

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baseapp.MyBooking.BookingAdapter
import com.example.baseapp.databinding.BookingBinding
import com.example.baseapp.databinding.LocationsBinding

class LocationsAdapter (
    val context: Context,
    val list:ArrayList<String>?

): RecyclerView.Adapter<LocationsAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //val v= LayoutInflater.from(parent.context).inflate(R.layout.company_rows,parent,false)
        //return Holder(v)
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: LocationsBinding = LocationsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {

        return list!!.size
    }

    override  fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }
    class ViewHolder(binding: LocationsBinding) : RecyclerView.ViewHolder(binding.getRoot())
    {
        var binding: LocationsBinding

        init {
            this.binding = binding
        }
    }
}
