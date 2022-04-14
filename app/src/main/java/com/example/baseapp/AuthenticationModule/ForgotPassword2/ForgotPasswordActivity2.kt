package com.example.baseapp.AuthenticationModule.ForgotPassword2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
import android.view.View
import android.widget.Toast
import com.API.APIUtils
import com.example.baseapp.AuthenticationModule.ForgotPassword1.ForgotPasswordResponse
import com.example.baseapp.AuthenticationModule.ForgotPassword3.ForgotPasswordActivity3
import com.example.baseapp.R
import com.example.baseapp.Utils.NetworkUtils
import com.example.baseapp.Utils.SharedPreferenceUtils
import com.example.baseapp.Utils.ToastUtil
import com.metaled.Utils.ConstantUtils
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_forgot_password2.*
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class ForgotPasswordActivity2 : AppCompatActivity() {
    var userid=""
    var emailid=""
    var otp=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password2)

        userid=intent.getStringExtra("userid").toString()
        emailid=intent.getStringExtra("email").toString()
        tvEmailid.setText(emailid)
          tvResend
        click()

    }

    private fun click() {
        tvVerify.setOnClickListener() {
            otp=etCode1.text.toString()+etCode2.text.toString()+etCode3.text.toString()+etCode4.text.toString()
            if(otp.isEmpty()){
                Toast.makeText(
                    this, resources.getString(com.example.baseapp.R.string.plzotp),
                    Toast.LENGTH_LONG
                ).show()
            }else if(otp.length!=4){
                Toast.makeText(
                    this, resources.getString(com.example.baseapp.R.string.plzcorectotp),
                    Toast.LENGTH_LONG
                ).show()
            }else{
                if (NetworkUtils.checkInternetConnection(this))
                {
                    verifyotp()
                }

            }

        }
        tvResend.setOnClickListener {
            val request = HashMap<String, String>()
            request.put("email", emailid)
            rlLoader1.visibility = View.VISIBLE

            var call: Call<ForgotPasswordResponse> = APIUtils.getServiceAPI()!!.forgotPasswordApi(request)

            call.enqueue(object : Callback<ForgotPasswordResponse> {
                override fun onResponse(
                    call: Call<ForgotPasswordResponse>,
                    response: Response<ForgotPasswordResponse>
                ) {
                    try {

                        rlLoader1.visibility = View.GONE
                        if (response.body()!!.status.equals(true)) {

                            ToastUtil.toast_Long(this@ForgotPasswordActivity2,"Otp Resend Successfully")


                        } else {
                            ToastUtil.toast_Long(this@ForgotPasswordActivity2,response.body()!!.message)

                        }

                    } catch (e: Exception) {
                        Log.e("nitish", e.toString())
                        rlLoader1.visibility = View.GONE

                    }

                }

                override fun onFailure(call: Call<ForgotPasswordResponse>, t: Throwable) {
                    Log.e("nitish", t.message.toString())
                    rlLoader1.visibility = View.GONE

                }

            })
        }
    }

    fun verifyotp() {
        /*{"customer_id":"36", "otp":"4802"}*/
        val request = HashMap<String, String>()
//        request.put("customer_id", userid)
        request.put("email", emailid)
        request.put("otp", otp)
        rlLoader1.visibility = View.VISIBLE

        var call: Call<ForgotPassword2Response> = APIUtils.getServiceAPI()!!.verifyOtpApi(request)

        call.enqueue(object : Callback<ForgotPassword2Response> {
            override fun onResponse(
                call: Call<ForgotPassword2Response>,
                response: Response<ForgotPassword2Response>
            ) {
                try {

                    rlLoader1.visibility = View.GONE
                    if (response.body()!!.status.equals(true)) {
                        intent = Intent(this@ForgotPasswordActivity2, ForgotPasswordActivity3::class.java)
                        intent.putExtra("email",emailid)
                        startActivity(intent)

                    } else {

                        ToastUtil.toast_Long(this@ForgotPasswordActivity2,response.body()!!.message.toString())
                    }

                } catch (e: Exception) {
                    Log.e("nitish", e.toString())
                    rlLoader1.visibility = View.GONE

                }

            }

            override fun onFailure(call: Call<ForgotPassword2Response>, t: Throwable) {

                ToastUtil.toast_Long(this@ForgotPasswordActivity2,t.message.toString())
                rlLoader1.visibility = View.GONE

            }

        })
    }


    }

