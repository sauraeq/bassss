package com.example.baseapp.AuthenticationModule.ForgotPassword1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.API.APIUtils
import com.example.baseapp.AuthenticationModule.ForgotPassword2.ForgotPasswordActivity2
import com.example.baseapp.AuthenticationModule.Login.LoginActivity
import com.example.baseapp.R
import com.example.baseapp.Utils.NetworkUtils
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_forgot_password3.*
import kotlinx.android.synthetic.main.activity_login2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class ForgotPasswordActivity : AppCompatActivity() {
    var emailid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        tvSend.setOnClickListener() {
            emailid = et_email_forgot.text.toString()

            if (emailid.isNullOrEmpty()) {
                Toast.makeText(
                    this, resources.getString(com.example.baseapp.R.string.plzemail),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                if (NetworkUtils.checkInternetConnection(this)) {
                    forgotpassword()

                }
            }


        }

    }


        fun forgotpassword() {
        val request = HashMap<String, String>()
        request.put("email", emailid)
        rlLoader.visibility = View.VISIBLE

        var call: Call<ForgotPasswordResponse> = APIUtils.getServiceAPI()!!.forgotPasswordApi(request)
        call.enqueue(object : Callback<ForgotPasswordResponse> {
            override fun onResponse(
                call: Call<ForgotPasswordResponse>,
                response: Response<ForgotPasswordResponse>
            ) {
                try {

                    rlLoader.visibility = View.GONE
                    if (response.body()!!.status.equals(true)) {



                        intent =
                            Intent(this@ForgotPasswordActivity, ForgotPasswordActivity2::class.java)
//                        intent.putExtra("userid",response.body()!!.data.customer_id)
                        intent.putExtra("email", emailid)
                        startActivity(intent)
                        Toast.makeText(this@ForgotPasswordActivity,response.body()!!.message,Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@ForgotPasswordActivity,response.body()!!.message,Toast.LENGTH_LONG).show()
                    }

                } catch (e: Exception) {
                    Toast.makeText(this@ForgotPasswordActivity, e.message.toString(),Toast.LENGTH_LONG).show()
                    Log.e("nitish", e.message.toString())
                    rlLoader.visibility = View.GONE

                }

            }

            override fun onFailure(call: Call<ForgotPasswordResponse>, t: Throwable) {
                Toast.makeText(this@ForgotPasswordActivity, t.message.toString(),Toast.LENGTH_LONG).show()
                Log.e("nitish", t.message.toString())
                rlLoader.visibility = View.GONE

            }

        })
    }

}

