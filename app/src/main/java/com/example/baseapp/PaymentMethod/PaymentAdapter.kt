package com.example.baseapp.PaymentMethod

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baseapp.Locations.LocationsAdapter
import com.example.baseapp.databinding.LocationsBinding
import com.example.baseapp.databinding.PaymentMethodBinding

class PaymentAdapter  (
    val context: Context,
    val list:ArrayList<String>?

): RecyclerView.Adapter<PaymentAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //val v= LayoutInflater.from(parent.context).inflate(R.layout.company_rows,parent,false)
        //return Holder(v)
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: PaymentMethodBinding = PaymentMethodBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {

        return list!!.size
    }

    override  fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }
    class ViewHolder(binding: PaymentMethodBinding) : RecyclerView.ViewHolder(binding.getRoot())
    {
        var binding: PaymentMethodBinding

        init {
            this.binding = binding
        }
    }
}
