package com.example.baseapp.Profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.API.APIUtils
import com.bumptech.glide.Glide
import com.example.baseapp.Profile.GetDetailsModel.GetDetailResponse
import com.example.baseapp.Profile.Model.EditProfileResponse
import com.example.baseapp.Profile.UpdatePhotoModel.UpdatePhotoResponse
import com.example.baseapp.R
import com.example.baseapp.Utils.Camerautils.FileCompressor
import com.example.baseapp.Utils.Camerautils.PermissionUtils
import com.example.baseapp.Utils.Camerautils.Utility
import com.example.baseapp.Utils.NetworkUtils
import com.example.baseapp.Utils.SharedPreferenceUtils
import com.metaled.Utils.ConstantUtils
import com.rehabcare.user.datePickerHelper
import kotlinx.android.synthetic.main.activity_dashboard.tvTitle
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.header.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ProfileActivity : AppCompatActivity() {

    var image=""

    var currentPhotoPath = ""
    var userChoosenTask = ""
    var profileimage = ""
    var REQUEST_CODE: Int = 0
    var filepath_presc: Uri? = null
    var image1path = ""


    var mCompressor: FileCompressor? = null
    private var CAMERA_REQUEST: Int = 1
    private var PICK_IMAGE_REQUEST: Int = 1

    lateinit var datePicker: datePickerHelper
    var date:String =""
    private var oneWayTripDate: Date? = null

    var  name:String=""
    var gender:String=""
    var date_of_birth:String=""
    var phone_number:String=""
    var location:String=""
    var email:String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        rl_Details.visibility=View.GONE


        if(NetworkUtils.checkInternetConnection(this))
        {
           // editUserImageApi()
        }



        mCompressor = FileCompressor(this)

        if(NetworkUtils.checkInternetConnection(this))
        {
            SetUserDetailApi()
        }

        datePicker = datePickerHelper(this)
        tvTitle.setText("Profile")
        rlBack.setOnClickListener {
            onBackPressed()

        }
        click()

        et_Dob.setOnClickListener {
            showDatePickerDialog()
        }

//        settext()

    }

    private fun showDatePickerDialog() {
        val cal = Calendar.getInstance()
        val d = cal.get(Calendar.DAY_OF_MONTH)
        val m = cal.get(Calendar.MONTH)
        val y = cal.get(Calendar.YEAR)

        datePicker.showDialog(d, m, y, object : datePickerHelper.Callback {
            override fun onDateSelected(dayofMonth: Int, month: Int, year: Int) {


                val dayStr = if (dayofMonth < 10) "0${dayofMonth}" else "${dayofMonth}"
                val mon = month + 1
                val monthStr = if (mon < 10) "0${mon}" else "${mon}"
                var data:String
                data="${year}-${monthStr}-${dayStr}"

                date=(data)

                val date = data
                val input = SimpleDateFormat("yyyy-MM-dd")
                val output = SimpleDateFormat("yyyy-MM-dd")
                oneWayTripDate = input.parse(date) // parse input


                et_Dob.setText(output.format(oneWayTripDate))


            }


        })
    }

    private fun editUserImageApi() {
        //  val request = HashMap<String, String>()
        //request.put("email", emailid)
        val request = HashMap<String, String>()
        request.put("image", image1path)
        request.put("user_id", SharedPreferenceUtils.getInstance(this)?.getStringValue(
            ConstantUtils.ID, "").toString())


        Log.e("imagepath",image1path)
        rlLoader.visibility = View.VISIBLE


        var call: Call<UpdatePhotoResponse> = APIUtils.getServiceAPI()!!.editUserImageApi(request)

        call.enqueue(object : Callback<UpdatePhotoResponse> {
            override fun onResponse(
                call: Call<UpdatePhotoResponse>,
                response: Response<UpdatePhotoResponse>
            ) {
                try {

                    if (response.body()!!.message.equals(true)) {
                        rl_Details.visibility=View.VISIBLE

                        Glide.with(this@ProfileActivity).load(response.body()?.data?.image)

                    } else {
                        Toast.makeText(
                            this@ProfileActivity, response.body()!!.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()

                    }

                    rlLoader.visibility=View.GONE

                } catch (e: Exception) {
                    Log.e("Exception", e.toString())
                    rlLoader.visibility = View.GONE
                    Toast.makeText(this@ProfileActivity, e.toString(), Toast.LENGTH_LONG).show()

                }

            }

            override fun onFailure(call: Call<UpdatePhotoResponse>, t: Throwable) {
                Log.e("Throwable", t.message.toString())
                Toast.makeText(this@ProfileActivity, t.toString(), Toast.LENGTH_LONG).show()
                rlLoader.visibility = View.GONE

            }
        })
    }

//    private fun settext() {
//            tv_Name.setText(
//                SharedPreferenceUtils.getInstance(this)?.getStringValue(
//                    ConstantUtils.NAME, ""
//                )
//            )
//            et_Name.setText(
//                SharedPreferenceUtils.getInstance(this)?.getStringValue(
//                    ConstantUtils.NAME, ""
//                )
//            )
//            et_Gender.setText(
//                SharedPreferenceUtils.getInstance(this)?.getStringValue(
//                    ConstantUtils.GENDER, ""
//                )
//            )
//           /* et_Dob.setText(
//                SharedPreferenceUtils.getInstance(this)?.getStringValue(
//                    ConstantUtils.DATE_OF_BIRTH, ""
//                )
//            )*/
//            et_Email.setText(
//                SharedPreferenceUtils.getInstance(this)?.getStringValue(
//                    ConstantUtils.EMAIL, ""
//                )
//            )
//        }

    private fun click() {

        iv_Profile.setOnClickListener {
            getPermissions()

        }

        iv_Male.setOnClickListener {
            et_Gender.setText("Male")

        }
        iv_Female.setOnClickListener {
            et_Gender.setText("Female")

        }
        et_Dob.setOnClickListener {

        }


        tv_Update.setOnClickListener {
            name=et_Name.text.toString()
            gender=et_Gender.text.toString()
            date_of_birth=et_Dob.text.toString()
            location=et_Location.text.toString()
            email=et_Email.text.toString()

            if (name.isNullOrEmpty()) {
                Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show()

            } else if (gender.isNullOrEmpty()) {
                Toast.makeText(this, "Select Gender", Toast.LENGTH_SHORT).show()

            } else if( date_of_birth.isNullOrEmpty()) {

                Toast.makeText(this, "Please Enter Your Date of Birth", Toast.LENGTH_SHORT).show()
            } else if (email.isNullOrEmpty()) {

                Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show()
            } else if (location.isNullOrEmpty()) {

                Toast.makeText(this, "Please Enter Your Location", Toast.LENGTH_SHORT).show()
            } else {
                callupdateProfile()

            }

        }

    }

    private fun SetUserDetailApi() {

        rlLoader.visibility = View.VISIBLE

        var UserId:String=SharedPreferenceUtils.getInstance(this)?.getStringValue(
                    ConstantUtils.ID, "").toString()

        var call: Call<GetDetailResponse> = APIUtils.getServiceAPI()!!.UserDetailApi(UserId)

        call.enqueue(object : Callback<GetDetailResponse> {
            override fun onResponse(
                call: Call<GetDetailResponse>,
                response: Response<GetDetailResponse>
            ) {
                try {

                    if (response.body()!!.status.equals(true)) {
                        rl_Details.visibility=View.VISIBLE
                        tv_Name.setText(response.body()!!.data.name)
                        tv_Location.setText(response.body()!!.data.location)
                        et_Name.setText(response.body()!!.data.name)
                        et_Gender.setText(response.body()!!.data.gender)
                        et_Dob.setText(response.body()!!.data.date_of_birth)
                        et_Location.setText(response.body()!!.data.location)
                        et_Email.setText(response.body()!!.data.email)

                    } else {
                        Toast.makeText(
                            this@ProfileActivity, response.body()!!.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()

                    }

                    rlLoader.visibility=View.GONE

                } catch (e: Exception) {
                    Log.e("Exception", e.toString())
                    rlLoader.visibility = View.GONE
                    Toast.makeText(this@ProfileActivity, e.toString(), Toast.LENGTH_LONG).show()

                }

            }

            override fun onFailure(call: Call<GetDetailResponse>, t: Throwable) {
                Log.e("Throwable", t.message.toString())
                Toast.makeText(this@ProfileActivity, t.toString(), Toast.LENGTH_LONG).show()
                rlLoader.visibility = View.GONE

            }

        })
    }

    private fun callupdateProfile() {
        rlLoader.visibility = View.VISIBLE

        val request = HashMap<String, String>()
//        request.put("user_id", SharedPreferenceUtils.getInstance(this)?.getStringValue(
//                ConstantUtils.USER_ID, "").toString())
        request.put("name", name)
        request.put("gender", gender)
        request.put("dob", date_of_birth)
        request.put("location", location)
        request.put("email", email)
        request.put("user_id", SharedPreferenceUtils.getInstance(this)?.getStringValue(
            ConstantUtils.ID, "").toString())
        request.put("lat", SharedPreferenceUtils.getInstance(this)?.getStringValue(
            ConstantUtils.LAT, "").toString())
        request.put("lng", SharedPreferenceUtils.getInstance(this)?.getStringValue(
            ConstantUtils.LNG, "").toString())
        request.put("phone", SharedPreferenceUtils.getInstance(this)?.getStringValue(
            ConstantUtils.PHONE, "").toString())
        request.put("work_email", SharedPreferenceUtils.getInstance(this)?.getStringValue(
            ConstantUtils.WORK_EMAIL, "").toString())


        var call: Call<EditProfileResponse> = APIUtils.getServiceAPI()!!.editUserDetailApi(request)

        call.enqueue(object : Callback<EditProfileResponse> { override fun onResponse(
                call: Call<EditProfileResponse>,
                response: Response<EditProfileResponse>
            ) {
                try {
                    if (response.body()!!.status.equals(true)) {

                        /*SharedPreferenceUtils.getInstance(this@ProfileActivity)
                            ?.setStringValue(ConstantUtils.USER_NAME, etName.text.toString())
                        SharedPreferenceUtils.getInstance(this@ProfileActivity)?.setStringValue(
                            ConstantUtils.ADDRESS,
                            etLocation.text.toString()
                        )*/
                        onBackPressed()

                        Toast.makeText(
                            this@ProfileActivity, response.body()!!.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()


                    } else {
                        Toast.makeText(
                            this@ProfileActivity, response.body()!!.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()

                    }
                    rlLoader.visibility = View.GONE
                } catch (e: Exception) {
                    Log.e("nitish", e.toString())

                    Toast.makeText(this@ProfileActivity, e.toString(), Toast.LENGTH_LONG).show()
                    rlLoader.visibility = View.GONE
                }

            }
            override fun onFailure(call: Call<EditProfileResponse>, t: Throwable) {
                Log.e("nitish", t.message.toString())
                rlLoader.visibility = View.GONE
                Toast.makeText(this@ProfileActivity, t.toString(), Toast.LENGTH_LONG).show()

            }
        })

    }

    fun getPermissions() {
        if (PermissionUtils.checkPermission(this as Activity, Manifest.permission.CAMERA, REQUEST_CODE)) {
            if (PermissionUtils.checkPermission(
                    this as Activity?,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    REQUEST_CODE
                )
            ) {
                if (PermissionUtils.checkPermission(
                        this as Activity?,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        REQUEST_CODE
                    )
                ) {
                    selectImage()
                }
            }
        }
    }

    private fun selectImage() {

        val items = arrayOf<CharSequence>("Take photo", "Choose from gallery")
        val builder = AlertDialog.Builder(this as Activity)
        builder.setTitle("Add Photo")
        builder.setCancelable(true)
        builder.setItems(items) { dialog, item ->
            val result: Boolean = Utility.checkPermission(this)
            if (items[item] == "Take photo") {
                userChoosenTask = "Take photo"
                if (result) {
                    dispatchTakePictureIntent()

                }

            } else if (items[item] == "Choose from gallery") {
                userChoosenTask = "Choose from gallery"
                if (result) {
                    openGallery()
                }

            }/* else if (items[item] == "Cancel") {
                dialog.dismiss()
            }*/
        }
        builder.show()
    }

    fun openGallery() {
        if (Build.VERSION.SDK_INT >= 23) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST
            )

        } else {
            if (Build.VERSION.SDK_INT <= 19) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(
                    Intent.createChooser(intent, "Select Picture"),
                    PICK_IMAGE_REQUEST
                )
            } else if (Build.VERSION.SDK_INT > 19) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(
                    Intent.createChooser(intent, "Select Picture"),
                    PICK_IMAGE_REQUEST
                )
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                ex.printStackTrace()
                // Error occurred while creating the File
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                val photoURI: Uri = FileProvider.getUriForFile(
                    this,
                    getApplicationContext()?.getPackageName() + ".fileprovider",
                    photoFile
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, CAMERA_REQUEST)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir: File? =getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image: File = File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath()
        Log.d("photopath", currentPhotoPath)
        return image
    }

    fun onselectfromcamera1() {
        //  try {
        var bitmap: Bitmap? = null
        var imgFile = File(currentPhotoPath)
        Log.d("flow1","dfdf")
        if (imgFile.exists()) {
            bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgFile = mCompressor!!.compressToFile(imgFile)
            bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            image1path=imgFile.path



//            val editor: SharedPreferences.Editor = mCompressor.edit()
//            editor.putString("imagePreferance", encodeTobase64(yourbitmap))
//            editor.commit()







            profileimage = getStringImage(bitmap)

            val shre = PreferenceManager.getDefaultSharedPreferences(this)
            val edit: SharedPreferences.Editor = shre.edit()
            edit.putString("image", image)
            edit.commit()

            //  uploadImage(imgFile.path)
            // upladImage()
        }
        Log.d("flow2","dfdf")
        /*} catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.d("Exception",e.toString())
        }*/
    }

    fun getStringImage(bmp: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageBytes: ByteArray = baos.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }

    fun onselectfromgallery(data: Intent) {
        filepath_presc = data.data
        if (filepath_presc.toString() == null) {

        } else {
            try {
                Log.e("filepath", filepath_presc.toString() + "")
                var bitmap: Bitmap? = null
                /* if (Build.VERSION.SDK_INT >= 29) {
                     val source: ImageDecoder.Source? =
                         filepath_presc?.let {
                             ImageDecoder.createSource(
                                 requireActivity().contentResolver,
                                 it
                             )
                         }
                     try {
                         bitmap = source?.let { ImageDecoder.decodeBitmap(it) }!!
                     } catch (e: IOException) {
                         e.printStackTrace()
                     }
                 } else {
                     bitmap =
                         MediaStore.Images.Media.getBitmap(activity?.contentResolver, filepath_presc)
                 }*/
                var file = File(filepath_presc?.let { getRealPathFromUri(it) })
                try {
                    file = mCompressor!!.compressToFile(file)
                    bitmap = mCompressor!!.compressToBitmap(file)
                    image1path=file.path

                    profileimage = bitmap?.let { getStringImage(it) }.toString()
                    iv_Profile.setImageBitmap(bitmap)
                    //  upladImage()
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                    Log.e("exception1", e.toString())
                }

            } catch (e: java.lang.Exception) {
                Log.e("exception", e.toString())
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("Range")
    fun getRealPathFromUri(uri: Uri): String? {
        var result = ""
        val documentID: String
        documentID = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            val pathParts = uri.path!!.split("/".toRegex()).toTypedArray()
            pathParts[pathParts.size - 1]
        } else {
            val pathSegments = uri.lastPathSegment!!.split(":".toRegex()).toTypedArray()
            pathSegments[pathSegments.size - 1]
        }
        val mediaPath = MediaStore.Images.Media.DATA
        val imageCursor: Cursor? =contentResolver.query(
            uri,
            arrayOf(mediaPath),
            MediaStore.Images.Media._ID + "=" + documentID,
            null,
            null
        )
        if (imageCursor!!.moveToFirst()) {
            result = imageCursor.getString(imageCursor.getColumnIndex(mediaPath))
        }
        return result
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == -1 && data != null && data.data != null) {
            Log.e("filename", data.toString() + "")
            onselectfromgallery(data)
        }else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            onselectfromcamera1()
        }
    }


















//        var data: String? =SharedPreferenceUtils.getInstance(this)
//            ?.getStringValue(ConstantUtils.ID,"")


    }