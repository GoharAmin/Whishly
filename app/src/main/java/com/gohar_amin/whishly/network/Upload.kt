package com.gohar_amin.whishly.network

import android.content.Context
import android.net.Uri
import android.util.Log
import com.gohar_amin.whishly.callback.UploadCallback
import com.gohar_amin.whishly.dao.UploadDao
import com.gohar_amin.whishly.model.ResponseModel
import com.gohar_amin.whishly.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class Upload(private val context: Context) {
    lateinit var uploadDao: UploadDao

    init {
        uploadDao = ApiClient.getClient(context)!!.create(UploadDao::class.java)
    }

    fun <T> prepareFilePart(
        partName: String,
        contentType: String,
        fileUri: Uri,
        callback: UploadCallback<T>
    ): MultipartBody.Part {

        val file: File = File("" + fileUri);
        val requestFile: RequestBody = UploadRequestBody(file, contentType, callback)
        return MultipartBody.Part.createFormData(partName, file.name, requestFile);
    }

    fun uploadMultiple(
        url: String,
        partNameList: List<String>,
        contentType: String,
        fileUriList: List<Uri>,
        obj:HashMap<String,Any?>,
        isEdit: Boolean,
        callback: UploadCallback<ResponseModel>
    ) {
        val list=ArrayList<MultipartBody.Part>()
        for (i in 0 until fileUriList.size) {
            Log.e("upload", partNameList.get(i) + "  -> " + fileUriList.get(i));
            val multipartBodyPart =
                prepareFilePart(partNameList.get(i), contentType, fileUriList.get(i), callback)
            list.add(multipartBodyPart)
        }
        if(isEdit){
            uploadDao.editMultiple(url, obj, list).enqueue(object : Callback<ResponseModel> {
                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    Log.e("onFailure", "" + t.message)
                }

                override fun onResponse(
                    call: Call<ResponseModel>,
                    response: Response<ResponseModel>
                ) {
                    Utils.setMultipartResponse(response, callback)
                }
            })
        }else {
            uploadDao.uploadMultiple(url, obj, list).enqueue(object : Callback<ResponseModel> {
                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    Log.e("onFailure", "" + t.message)
                }

                override fun onResponse(
                    call: Call<ResponseModel>,
                    response: Response<ResponseModel>
                ) {
                    Utils.setMultipartResponse(response, callback)
                }
            })
        }
    }

    fun upload(
        url: String,
        partName: String,
        hashMap:HashMap<String,Any?>?,
        contentType: String,
        fileUri: Uri,
        callback: UploadCallback<ResponseModel>,
        isEdit:Boolean
    ) {
        Log.e("upload", "upload -> " + fileUri);

        val multipartBodyPart = prepareFilePart(partName, contentType, fileUri, callback)
        if(isEdit) {
            if(hashMap!=null){
                uploadDao.upload(url,hashMap ,multipartBodyPart).enqueue(object : Callback<ResponseModel> {
                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                        Log.e("onFailure", "" + t.message)
                    }

                    override fun onResponse(
                        call: Call<ResponseModel>,
                        response: Response<ResponseModel>
                    ) {
                        Utils.setMultipartResponse(response, callback)
                    }
                })
            }else {
                uploadDao.upload(url, multipartBodyPart).enqueue(object : Callback<ResponseModel> {
                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                        Log.e("onFailure", "" + t.message)
                    }

                    override fun onResponse(
                        call: Call<ResponseModel>,
                        response: Response<ResponseModel>
                    ) {
                        Utils.setMultipartResponse(response, callback)
                    }
                })
            }
        }else{
            if(hashMap!=null) {
                uploadDao.uploadPost(url,hashMap, multipartBodyPart)
                    .enqueue(object : Callback<ResponseModel> {
                        override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                            Log.e("onFailure", "" + t.message)
                        }

                        override fun onResponse(
                            call: Call<ResponseModel>,
                            response: Response<ResponseModel>
                        ) {
                            Utils.setMultipartResponse(response, callback)
                        }
                    })
            }else{
                uploadDao.uploadPost(url, multipartBodyPart)
                    .enqueue(object : Callback<ResponseModel> {
                        override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                            Log.e("onFailure", "" + t.message)
                        }

                        override fun onResponse(
                            call: Call<ResponseModel>,
                            response: Response<ResponseModel>
                        ) {
                            Utils.setMultipartResponse(response, callback)
                        }
                    })
            }
        }
    }

}