package com.example.ranierilavastone.Utils

class StringUtil {
    companion object{
    fun capString(str:String):String{
        try{
            return  str[0].uppercaseChar()+str.substring(1).lowercase()
        }catch (e:Exception){
            return  ""
        }


    }
    }
}