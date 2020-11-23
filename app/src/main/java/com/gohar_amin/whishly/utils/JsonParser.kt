package com.gohar_amin.whishly.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class JsonParser {
    companion object{
        fun <T>toJson(obj:T):String{
            return Gson().toJson(obj);
        }
        fun <T>fromJson(str:String,clazz:Class<T>):T{
            return Gson().fromJson(str,clazz)
        }
        fun <T> toList(jsonStr:String, clazz: Class<T>):ArrayList<T>{
            val type = TypeToken.getParameterized(
                ArrayList::class.java,
                clazz
            ).type
            return Gson().fromJson(jsonStr,type) as ArrayList<T>
        }
    }
}