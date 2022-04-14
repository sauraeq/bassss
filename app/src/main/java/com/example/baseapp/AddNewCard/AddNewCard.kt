package com.example.baseapp.AddNewCard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.baseapp.R
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_dashboard.tvTitle
import kotlinx.android.synthetic.main.header.*

class AddNewCard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_card)
        tvTitle.setText("Add new card")

        rlBack.setOnClickListener {
            onBackPressed()
        }
    }
}