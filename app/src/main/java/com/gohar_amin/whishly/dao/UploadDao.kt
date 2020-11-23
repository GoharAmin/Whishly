package com.gohar_amin.whishly.dao


import com.gohar_amin.whishly.model.ResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
interface UploadDao {
 @Multipart
 @PUT
 fun upload(@Url url:String,@Part image:MultipartBody.Part): Call<ResponseModel>
 @Multipart
 @POST
 fun uploadPost(@Url url:String,@Part image:MultipartBody.Part): Call<ResponseModel>
 @Multipart
 @PUT
 fun upload(@Url url:String,@PartMap params:HashMap<String, Any?>,@Part image:MultipartBody.Part): Call<ResponseModel>
 @Multipart
 @POST
 fun uploadPost(@Url url:String,@PartMap params:HashMap<String, Any?>,@Part image:MultipartBody.Part): Call<ResponseModel>
 @Multipart
 @POST
 fun uploadMultiple(@Url url:String,@PartMap params:HashMap<String, Any?>,@Part list:List<MultipartBody.Part>): Call<ResponseModel>

 @Multipart
 @PUT
 fun editMultiple(@Url url:String,@PartMap params:HashMap<String, Any?>,@Part list:List<MultipartBody.Part>): Call<ResponseModel>
}