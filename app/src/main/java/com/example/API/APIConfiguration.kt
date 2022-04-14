package com.API

import com.example.baseapp.AuthenticationModule.ForgotPassword1.ForgotPasswordResponse
import com.example.baseapp.AuthenticationModule.ForgotPassword2.ForgotPassword2Response
import com.example.baseapp.AuthenticationModule.ForgotPassword2.ForgotPasswordActivity2
import com.example.baseapp.AuthenticationModule.ForgotPassword3.ForgotPassword3Response
import com.example.baseapp.AuthenticationModule.ForgotPassword3.ForgotPasswordActivity3
import com.example.baseapp.AuthenticationModule.Login.LoginActivity
import com.example.baseapp.AuthenticationModule.Login.LoginResponse
import com.example.baseapp.AuthenticationModule.Signup.SignupResponse
import com.example.baseapp.Profile.GetDetailsModel.GetDetailResponse
import com.example.baseapp.Profile.Model.EditProfileResponse
import com.example.baseapp.Profile.UpdatePhotoModel.UpdatePhotoResponse

import retrofit2.Call
import retrofit2.http.*

interface APIConfiguration {
    @FormUrlEncoded
    @POST("singup")
    fun SignupActivity(
        @Field ("username") username:String,
        @Field ("email") email:String,
        @Field ("password") password:String,
        @Field ("confirm_password") confirm_password:String,
        @Field ("gender") gender:String,
    ): Call<SignupResponse>

    @FormUrlEncoded
    @POST("singin")
    fun LoginActivity(
        @Field ("email") email :String,
        @Field ("password") password:String,
        @Field ("logintype") logintype:String,
    ): Call<LoginResponse>

   /* @FormUrlEncoded
    @POST("sendotp")
    fun ForgotPasswordActivity(
        @Field ("email") email: String,
    ) : Call<ForgotPasswordResponse>*/


    @POST("sendotp")
    fun forgotPasswordApi(
        @Body request: HashMap<String, String>
    ) : Call<ForgotPasswordResponse>

/*    @FormUrlEncoded
    @POST("verifyotp")
    fun ForgotPasswordActivity2(
        @Field ("otp") otp: String,
    ) : Call<ForgotPassword2Response>*/


    @POST("verifyotp")
    fun verifyOtpApi(
        @Body request: HashMap<String, String>
    ) : Call<ForgotPassword2Response>

    /*@FormUrlEncoded
    @POST("updatepassword")
    fun ForgotPasswordActivity3(
        @Field ("confirm_password") confirm_password: String,
        @Field ("password") password: String,
        @Field ("email") email: String,
    ) : Call<ForgotPassword3Response>*/


    @POST("updatepassword")
    fun updatePasswordApi(
        @Body request: HashMap<String,String>
    ) : Call<ForgotPassword3Response>

    @POST("edituserdetail")
    fun editUserDetailApi(
        @Body request: HashMap<String,String>
    ) : Call<EditProfileResponse>

    @GET("userdetail/{id}")
    fun UserDetailApi(
            @Path("id") id: String
    ):      Call<GetDetailResponse>

    @POST("edituserimage")
    fun editUserImageApi(
        @Body request: java.util.HashMap<String, String>
    ) : Call<UpdatePhotoResponse>








}