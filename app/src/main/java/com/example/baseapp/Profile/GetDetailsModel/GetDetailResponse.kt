package com.example.baseapp.Profile.GetDetailsModel

data class GetDetailResponse(
    val `data`: Data,
    val image_url: String,
    val message: String,
    val status: Boolean
){
    data class Data(
        val created_at: String,
        val date_of_birth: String,
        val email: String,
        val email_verified_at: String,
        val facebook_id: String,
        val gender: String,
        val google_id: String,
        val id: String,
        val image: String,
        val lat: String,
        val lng: String,
        val location: String,
        val login_type: String,
        val name: String,
        val password: String,
        val phone: String,
        val remember_token: String,
        val status: String,
        val updated_at: String,
        val work_email: String
    )
}