package com.gohar_amin.whishly.network

import android.content.Context
import android.icu.util.TimeUnit
import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient{
    const val host="10.0.2.2"
//    const val host="192.168.0.105"
    private val BASE_URL="http://"+host+":7000/";
    private  lateinit var context: Context
    private var retrofit: Retrofit? = null
    fun getClient(context: Context?): Retrofit? {
        if (retrofit == null) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level=HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient
                .Builder()
                .connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(100, java.util.concurrent.TimeUnit.SECONDS)
                .addInterceptor { chain: Interceptor.Chain ->
                    val userToken: String? = null
                    /*UserPrefHelper.getInstance(context!!)!!.getUserToken()*/
                    val builder = chain.request().newBuilder()
                    if (userToken != null) {
                        Log.e("Authorization",""+userToken)
                        builder.header("Authorization", userToken)
                    }
                    chain.proceed(builder.build())
                }.addInterceptor(interceptor)
                .build()
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        return retrofit
    }
}