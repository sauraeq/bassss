package com.example.baseapp.Dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import com.example.baseapp.About.About
import com.example.baseapp.AuthenticationModule.Login.LoginActivity
import com.example.baseapp.Contact.Contact
import com.example.baseapp.Locations.Locations
import com.example.baseapp.MyBooking.MyBooking
import com.example.baseapp.PaymentMethod.PaymentMethodActivity
import com.example.baseapp.Profile.ProfileActivity
import com.example.baseapp.R
import com.example.baseapp.databinding.ActivityDashboardBinding
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.menu_footer.*
import kotlinx.android.synthetic.main.menu_header.*

class Dashboard : AppCompatActivity() {
    private lateinit var binding:ActivityDashboardBinding
            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                binding = ActivityDashboardBinding.inflate(layoutInflater)
                setContentView(binding.root)
        setContentView(R.layout.activity_dashboard)

        onclick()
    }

    private fun onclick() {
        ivCloseMenu.setOnClickListener {
            closeDrower()
        }
        ivMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        llProfile.setOnClickListener {
            var intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            closeDrower()
        }

        llBookigs.setOnClickListener {
            var intent = Intent(this, MyBooking::class.java)
            startActivity(intent)
            closeDrower()
        }
        llABoutus.setOnClickListener {
            var intent = Intent(this, About::class.java)
            startActivity(intent)
            closeDrower()
        }
        llLoc.setOnClickListener {
            var intent = Intent(this, Locations::class.java)
            startActivity(intent)
            closeDrower()
        }
        llContact.setOnClickListener {
            var intent = Intent(this, Contact::class.java)
            startActivity(intent)
            closeDrower()
        }

         llLogout.setOnClickListener() {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            closeDrower()
            finishAffinity()
        }
        tvSelectLoc.setOnClickListener {
            var intent = Intent(this, Locations::class.java)
            startActivity(intent)
        }
        tvMakepayment.setOnClickListener {
            var intent = Intent(this, PaymentMethodActivity::class.java)
            startActivity(intent)
        }
        ivProfile.setOnClickListener {
            var intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

    }

    fun closeDrower() {
        drawerLayout.closeDrawer(GravityCompat.START)
    }
}