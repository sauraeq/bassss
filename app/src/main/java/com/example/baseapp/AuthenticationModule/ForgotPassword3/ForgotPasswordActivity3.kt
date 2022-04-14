package com.example.baseapp.AuthenticationModule.ForgotPassword3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.API.APIUtils
import com.example.baseapp.AuthenticationModule.Login.LoginActivity
import com.example.baseapp.AuthenticationModule.Signup.SignupResponse
import com.example.baseapp.R
import com.example.baseapp.Utils.NetworkUtils
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_forgot_password3.*
import kotlinx.android.synthetic.main.activity_forgot_password3.etPassword
import kotlinx.android.synthetic.main.activity_forgot_password3.etconfPassword
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity3 : AppCompatActivity() {
    var confpassword = ""
    var password = ""
    var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password3)
        email=intent.getStringExtra("email").toString()
        onClick()

    }
    fun onClick(){
        tvSubmit.setOnClickListener() {
            password = etPassword.text.toString()
            confpassword = etconfPassword.text.toString()

            if (password.isEmpty()) {
                Toast.makeText(
                    this, resources.getString(com.example.baseapp.R.string.plzpass),
                    Toast.LENGTH_LONG
                ).show()
            } else if (confpassword.isEmpty()) {
                Toast.makeText(
                    this, resources.getString(com.example.baseapp.R.string.confmpass),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                if (NetworkUtils.checkInternetConnection(this)) {
                    updatePasswordApi()
                }

            }
        }
    }

    fun updatePasswordApi() {
        rlLoader2.visibility = View.VISIBLE
        val request = HashMap<String, String>()
        request.put("email", email)
        request.put("password", password)
        request.put("confirm_password", confpassword)

        var signup: Call<ForgotPassword3Response> = APIUtils.getServiceAPI()!!.updatePasswordApi(request)

        signup.enqueue(object : Callback<ForgotPassword3Response> {
            override fun onResponse(
                call: Call<ForgotPassword3Response>,
                response: Response<ForgotPassword3Response>
            ) {
                try {
                    rlLoader2.visibility = View.GONE
                    if (response.body()!!.status.equals(true)) {
                        Toast.makeText(this@ForgotPasswordActivity3, response.body()!!.message, Toast.LENGTH_LONG).show()
                        var intent=Intent(this@ForgotPasswordActivity3,LoginActivity::class.java)
                        startActivity(intent)
                        finishAffinity()

                    } else {


                        Toast.makeText(this@ForgotPasswordActivity3, response.body()!!.message, Toast.LENGTH_LONG).show()
                        rlLoader2.visibility = View.GONE

                    }

                } catch (e: Exception) {
                    Log.e("nitish", e.toString())
                    Toast.makeText(this@ForgotPasswordActivity3, e.message.toString(), Toast.LENGTH_LONG).show()
                    rlLoader2.visibility = View.GONE
                }

            }

            override fun onFailure(call: Call<ForgotPassword3Response>, t: Throwable) {
                Log.e("nitish", t.message.toString())
                Toast.makeText(this@ForgotPasswordActivity3, t.message.toString(), Toast.LENGTH_LONG).show()
                rlLoader2.visibility = View.GONE

            }

        })
    }

}


