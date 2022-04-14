package com.example.baseapp.PaymentMethod

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baseapp.AddNewCard.AddNewCard
import com.example.baseapp.Locations.Locations
import com.example.baseapp.Locations.LocationsAdapter
import com.example.baseapp.R
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_dashboard.tvTitle
import kotlinx.android.synthetic.main.activity_payment_method.*
import kotlinx.android.synthetic.main.header.*
import kotlinx.android.synthetic.main.paymet_popup.*

class PaymentMethodActivity : AppCompatActivity() {
    private lateinit var rvpayment: RecyclerView
    private lateinit var list_method: ArrayList<String>
    lateinit var rlPaynow:RelativeLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_method)

        rvpayment = findViewById(R.id.rvpayment)
        rlPaynow = findViewById<RelativeLayout>(R.id.rlPaynow)
        tvTitle.setText("Payment Method")

        rlBack.setOnClickListener {
            onBackPressed()
        }
        rlPaynow.setOnClickListener {
            showDialog()
        }

        list_method = ArrayList()
        list_method.add("")
        list_method.add("")
        list_method.add("")

        list_method()
        onclick()
    }

    fun onclick() {
        llAddcard.setOnClickListener {
            var intent = Intent(this, AddNewCard::class.java)
            startActivity(intent)
        }
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.getWindow()!!
            .setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            );
        lateinit var cvOk: CardView
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.paymet_popup)
        cvOk=dialog.findViewById(R.id.cvOk)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()

//        dialog.window?.setLayout(1050, 1100)
        cvOk.setOnClickListener {
            dialog.dismiss()
        }
    }


    private fun list_method() {
        rvpayment.layoutManager = LinearLayoutManager(this)
        rvpayment.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvpayment.adapter = PaymentAdapter(this, list_method)
    }
}

