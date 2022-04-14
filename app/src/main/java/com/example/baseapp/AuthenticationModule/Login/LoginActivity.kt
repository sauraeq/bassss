package com.example.baseapp.AuthenticationModule.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.API.APIUtils
import com.example.baseapp.AuthenticationModule.ForgotPassword1.ForgotPasswordActivity
import com.example.baseapp.AuthenticationModule.Signup.SignupActivity
import com.example.baseapp.Dashboard.Dashboard
import com.example.baseapp.R
import com.example.baseapp.Utils.NetworkUtils
import com.example.baseapp.Utils.SharedPreferenceUtils
import com.metaled.Utils.ConstantUtils
import kotlinx.android.synthetic.main.activity_login2.*
import kotlinx.android.synthetic.main.activity_login2.etEmail
import kotlinx.android.synthetic.main.activity_login2.etPassword
import kotlinx.android.synthetic.main.activity_login2.tvSignIn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    var emailid = ""
    var password = ""
    var logintype = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
        onclick()

    }

    private fun onclick() {
        tvForgot.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
        tvSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        tvSignIn.setOnClickListener {

            emailid = etEmail.text.toString()
            password = etPassword.text.toString()

            if (emailid.isEmpty()) {
                Toast.makeText(
                    this,
                    resources.getString(com.example.baseapp.R.string.plzemail),
                    Toast.LENGTH_LONG
                ).show()
            } else if (password.isEmpty()) {
                Toast.makeText(
                    this,
                    resources.getString(com.example.baseapp.R.string.plzpass),
                    Toast.LENGTH_LONG
                ).show()

            } else if (NetworkUtils.checkInternetConnection(this)) {
                login("normal")
            } else {

                Toast.makeText(
                    this@LoginActivity,
                    "Check Internet connection",
                    Toast.LENGTH_LONG
                ).show()

            }

        }
    }

    fun login(logintype: String) {

        val request = HashMap<String, String>()
        request.put("email", emailid)
        request.put("password", password)

        rlLoginLoader.visibility = View.VISIBLE

        var login: Call<LoginResponse> =
            APIUtils.getServiceAPI()!!.LoginActivity(emailid, password, logintype)

        login.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                try {

                    rlLoginLoader.visibility = View.GONE
                    if (response.body()!!.status.equals(true)) {

//                        SharedPreferenceUtils.getInstance(this@LoginActivity)?.
//                        setStringValue(ConstantUtils.EMAIL, response.body()!!.data.email)
//                        SharedPreferenceUtils.getInstance(this@LoginActivity)
//                            ?.setStringValue(ConstantUtils.PASSWORD, response.body()!!.data.password)
//                        SharedPreferenceUtils.getInstance(this@LoginActivity)
//                            ?.setStringValue(ConstantUtils.LOGINTYPE, response.body()!!.data.login_type)
//                        SharedPreferenceUtils.getInstance(this@LoginActivity)
//                            ?.setStringValue(ConstantUtils.USER_ID, response.body()!!.data.id)


                        if (!response.body()!!.data.email_verified_at.isNullOrEmpty()) {
                            SharedPreferenceUtils.getInstance(this@LoginActivity)
                                ?.setStringValue(ConstantUtils.EMAIL_VERIFIED_AT, response.body()!!.data.email_verified_at
                                )
                        }
                        if (!response.body()!!.data.facebook_id.isNullOrEmpty()) {
                            SharedPreferenceUtils.getInstance(this@LoginActivity)
                                ?.setStringValue(ConstantUtils.FACEBOOK_ID, response.body()!!.data.facebook_id
                                )
                        }
                        if (!response.body()!!.data.google_id.isNullOrEmpty()) {
                            SharedPreferenceUtils.getInstance(this@LoginActivity)
                                ?.setStringValue(ConstantUtils.GOOGLE_ID, response.body()!!.data.google_id
                                )
                        }
                        if (!response.body()!!.data.image_url.isNullOrEmpty()) {
                            SharedPreferenceUtils.getInstance(this@LoginActivity)
                                ?.setStringValue(ConstantUtils.IMAGE_URL, response.body()!!.data.image_url
                                )
                        }
                        if (!response.body()!!.data.lat.isNullOrEmpty()) {
                            SharedPreferenceUtils.getInstance(this@LoginActivity)
                                ?.setStringValue(ConstantUtils.LAT, response.body()!!.data.lat)
                        }
                        if (!response.body()!!.data.lng.isNullOrEmpty()) {
                            SharedPreferenceUtils.getInstance(this@LoginActivity)
                                ?.setStringValue(ConstantUtils.LNG, response.body()!!.data.lng)
                        }
                        if (!response.body()!!.data.work_email.isNullOrEmpty()) {
                            SharedPreferenceUtils.getInstance(this@LoginActivity)
                                ?.setStringValue(ConstantUtils.WORK_EMAIL, response.body()!!.data.work_email)
                        }
                        if (!response.body()!!.data.status.isNullOrEmpty()) {
                            SharedPreferenceUtils.getInstance(this@LoginActivity)
                                ?.setStringValue(ConstantUtils.STATUS, response.body()!!.data.status)
                        }
                        if (!response.body()!!.data.remember_token.isNullOrEmpty()) {
                            SharedPreferenceUtils.getInstance(this@LoginActivity)
                                ?.setStringValue(ConstantUtils.REMEMBER_TOKEN, response.body()!!.data.remember_token)
                        }

                        SharedPreferenceUtils.getInstance(this@LoginActivity)
                            ?.setStringValue(ConstantUtils.IS_LOGIN, "true")
                        var intent = Intent(this@LoginActivity, Dashboard::class.java)
                        startActivity(intent)
                        finishAffinity()

                    } else {

                        Toast.makeText(
                            this@LoginActivity,
                            response.body()!!.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } catch (e: Exception) {
                    Log.e("Nitish", e.toString())
                    rlLoginLoader.visibility = View.GONE

                }

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("Nitish", t.message.toString())
                rlLoginLoader.visibility = View.GONE

            }

        })
    }

}
