package com.example.baseapp.AuthenticationModule.Signup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.API.APIUtils
import com.example.baseapp.AuthenticationModule.Login.LoginActivity
import com.example.baseapp.R
import com.example.baseapp.Utils.NetworkUtils
import com.example.baseapp.Utils.SharedPreferenceUtils
import com.metaled.Utils.ConstantUtils
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.etEmail
import kotlinx.android.synthetic.main.activity_signup.etPassword
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignupActivity : AppCompatActivity() {

    var username = ""
    var email = ""
    var password = ""
    var confpassword = ""
    var gender =""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        var genderlist = ArrayList<String>()
        genderlist.add("Male")
        genderlist.add("Female")
        var arrayAdapterView = ArrayAdapter(
            this,
            R.layout.spinner_row, genderlist
        )
        spGender.adapter = arrayAdapterView
        onclick()
         val login = findViewById<TextView>(R.id.tvSignIn)
        login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
 }

    private fun onclick() {
//        tvSignIn.setOnClickListener {
//            onBackPressed()
//        }

        spGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                (view as TextView).setTextColor(Color.WHITE) //Change selected text color
                var spGender = parent?.getItemAtPosition(position).toString()
                gender=spGender
//                Toast.makeText(this@SignupActivity, gender, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        tvSignUp.setOnClickListener() {
            username = etUsername.text.toString()
            email = etEmail.text.toString()
            password = etPassword.text.toString()
            confpassword = etconfPassword.text.toString()


            if (username.isEmpty()) {
                Toast.makeText(
                    this, resources.getString(com.example.baseapp.R.string.entrusername),
                    Toast.LENGTH_LONG
                ).show()
            } else if (email.isEmpty()) {
                Toast.makeText(
                    this, resources.getString(com.example.baseapp.R.string.plzemail),
                    Toast.LENGTH_LONG
                ).show()
            } else if (password.isEmpty()) {
                Toast.makeText(
                    this, resources.getString(com.example.baseapp.R.string.plzpass),
                    Toast.LENGTH_LONG
                ).show()
            } else if (confpassword.isEmpty()) {
                Toast.makeText(
                    this, resources.getString(com.example.baseapp.R.string.confmpass),
                    Toast.LENGTH_LONG
                ).show()
            }
            else {
                if (NetworkUtils.checkInternetConnection(this)) {
                    signup()
                }

            else {
                tvSignUp.setOnClickListener {
                    onBackPressed()
                }

            }

        }

        }
    }

    fun signup() {
        rlLoader.visibility = View.VISIBLE
        val stringStringHashMap = HashMap<String, String>()
        stringStringHashMap.put("username", username)
        stringStringHashMap.put("email", email)
        stringStringHashMap.put("password", password)
        stringStringHashMap.put("confirm_password", confpassword)
        stringStringHashMap.put("gender", gender)

        var signup: Call<SignupResponse> = APIUtils.getServiceAPI()!!.SignupActivity(username,email,password,confpassword,gender)

        signup.enqueue(object : Callback<SignupResponse> {
            override fun onResponse(
                call: Call<SignupResponse>,
                response: Response<SignupResponse>
            ) {
                try {

                    rlLoader.visibility = View.GONE
                    if (response.body()!!.status.equals(true)) {

                        if (!response.body()!!.data.created_at.isNullOrEmpty()) {
                            SharedPreferenceUtils.getInstance(this@SignupActivity)
                                ?.setStringValue(ConstantUtils.CREATED_AT, response.body()!!.data.created_at)
                        }
                        if (!response.body()!!.data.email.isNullOrEmpty()) {
                            SharedPreferenceUtils.getInstance(this@SignupActivity)
                                ?.setStringValue(ConstantUtils.EMAIL, response.body()!!.data.email)
                        }
                        if (!response.body()!!.data.gender.isNullOrEmpty()) {
                            SharedPreferenceUtils.getInstance(this@SignupActivity)
                                ?.setStringValue(ConstantUtils.GENDER, response.body()!!.data.gender)
                        }
                        if (!response.body()!!.data.image.isNullOrEmpty()) {
                            SharedPreferenceUtils.getInstance(this@SignupActivity)
                                ?.setStringValue(ConstantUtils.IMAGE, response.body()!!.data.image)
                        }
                        if (!response.body()!!.data.login_type.isNullOrEmpty()) {
                            SharedPreferenceUtils.getInstance(this@SignupActivity)
                                ?.setStringValue(ConstantUtils.LOGIN_TYPE, response.body()!!.data.login_type)
                        }
                        if (!response.body()!!.data.name.isNullOrEmpty()) {
                            SharedPreferenceUtils.getInstance(this@SignupActivity)
                                ?.setStringValue(ConstantUtils.NAME, response.body()!!.data.name)
                        }
                        if (!response.body()!!.data.updated_at.isNullOrEmpty()) {
                            SharedPreferenceUtils.getInstance(this@SignupActivity)
                                ?.setStringValue(ConstantUtils.UPDATED_AT, response.body()!!.data.updated_at)
                        }
                        if (!response.body()!!.data.id.toString().isNullOrEmpty()) {
                            SharedPreferenceUtils.getInstance(this@SignupActivity)
                                ?.setStringValue(ConstantUtils.ID, response.body()!!.data.id.toString())
                        }
                        if (!response.body()!!.data.date_of_birth.isNullOrEmpty()) {
                            SharedPreferenceUtils.getInstance(this@SignupActivity)
                                ?.setStringValue(ConstantUtils.DATE_OF_BIRTH, response.body()!!.data.date_of_birth)
                        }
                        if (!response.body()!!.data.phone.isNullOrEmpty()) {
                            SharedPreferenceUtils.getInstance(this@SignupActivity)
                                ?.setStringValue(ConstantUtils.PHONE, response.body()!!.data.phone)
                        }

                        Toast.makeText(this@SignupActivity, response.body()!!.message, Toast.LENGTH_LONG).show()
                        var intent=Intent(this@SignupActivity,LoginActivity::class.java)
                        startActivity(intent)

                        Log.e("nitish", response.body()!!.data.toString())
                        //LoginActivity()


                    } else {

                        Toast.makeText(this@SignupActivity, response.body()!!.message, Toast.LENGTH_LONG).show()
                        rlLoader.visibility = View.GONE

                    }


                } catch (e: Exception) {
                    Log.e("nitish", e.toString())
                    Toast.makeText(this@SignupActivity, e.message.toString(), Toast.LENGTH_LONG).show()
                    rlLoader.visibility = View.GONE
                }

            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                Log.e("nitish", t.message.toString())
                Toast.makeText(this@SignupActivity, t.message.toString(), Toast.LENGTH_LONG).show()
                rlLoader.visibility = View.GONE

            }

        })
    }

}

