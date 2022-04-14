package com.example.baseapp.MyBooking

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.baseapp.BookingsDetails.BookingDetails
import com.example.baseapp.databinding.BookingBinding

class BookingAdapter (
    val context: Context,
    val list:ArrayList<String>?

): RecyclerView.Adapter<BookingAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //val v= LayoutInflater.from(parent.context).inflate(R.layout.company_rows,parent,false)
        //return Holder(v)
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: BookingBinding = BookingBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {

        return list!!.size
    }

    override  fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.cvBooking.setOnClickListener {
            var intent = Intent(context, BookingDetails::class.java)
            context.startActivity(intent)
        }
    }
    class ViewHolder(binding: BookingBinding) : RecyclerView.ViewHolder(binding.getRoot())
    {
        var binding: BookingBinding

        init {
            this.binding = binding
        }
    }
}
