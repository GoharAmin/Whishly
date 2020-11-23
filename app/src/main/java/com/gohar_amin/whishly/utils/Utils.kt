package com.gohar_amin.whishly.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import com.bumptech.glide.Glide
import com.gohar_amin.whishly.R
import com.gohar_amin.whishly.callback.ArrayCallback
import com.gohar_amin.whishly.callback.ConfirmationCallback
import com.gohar_amin.whishly.callback.ObjectCallack
import com.gohar_amin.whishly.callback.UploadCallback
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

object Utils {
    var dialog: ProgressDialog?=null
    fun <T> debugResponse(
        response: Response<T>
    ) {
        Log.e(
            "retro_request ",
            response.raw().request.url.toString() + " (" + response.code() +" )"
        )
        Log.e("retro_request_data", "( " + response.raw().request.method + ") reqObj"+response.raw().request.body)
        if (response.body() != null) {
            Log.e("retro_response => ", JsonParser.toJson(response.body()))
        }
        if (response.errorBody() != null) Log.e(
            "retro_error",
            "" + response.errorBody().toString()
        ) else Log.e("retro_message", "" + response.message())
    }

    fun <T> setResponse(
        response: Response<T>,
        callback: ObjectCallack<T>
    ) {
        debugResponse(response)
        CoroutineScope(Dispatchers.Main).launch {
            if (response.isSuccessful) {

                callback.onData(response.body() as T)
            }
            else {
                try {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    callback.onError(jObjError.getString("msg"))
                } catch (e: Exception) {
                    callback.onError(response.message())
                    e.printStackTrace()
                }
                /*if(response.errorBody()!=null)
                    callback.onError(response.errorBody().toString());*/
            }
        }
    }
    fun <T> setMultipartResponse(
        response: Response<T>,
        callback: UploadCallback<T>
    ) {
        debugResponse(response)
        CoroutineScope(Dispatchers.Main).launch {
            if (response.isSuccessful) {

                callback.onCompleted(response.body() as T)
            }
            else {
                try {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    callback.onError(jObjError.getString("msg"))
                } catch (e: Exception) {
                    callback.onError(response.message())
                    e.printStackTrace()
                }
            }
        }
    }
    fun<T> confirmationDialog(context: Context, isCancelAble:Boolean, msg:String, obj:T, callback: ConfirmationCallback<T>){
        AlertDialog.Builder(context)
            .setTitle("Are you sure")
            .setMessage(msg)
            .setPositiveButton("yes",object: DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    callback.onConfirm(obj)
                    dialog!!.dismiss()
                }
            }).setNegativeButton("No",object:DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    callback.onCancel()
                    dialog!!.dismiss()
                }
            }).setCancelable(isCancelAble)
            .create()
            .show()
    }
    fun <T> setArrayResponse(
        response: Response<List<T>?>,
        callback: ArrayCallback<T>
    ) {
        debugResponse(response)
        CoroutineScope(Dispatchers.Main).launch {
            if (response.isSuccessful) {
                callback.onData(response.body() as T)
            } else {
                try {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    callback.onError(jObjError.getString("msg"))
                } catch (e: Exception) {
                    callback.onError(response.message())
                    e.printStackTrace()
                }
            }
        }
    }
    fun loadImage(context: Context, imageUrl: String?, iv: ImageView) {
        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_baseline_person_24)
            .into(iv)
    }
    fun showSelectedImages(context: Activity) {
        Dexter.withContext(context)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        Log.e("click","permission")
                        val intent = Intent(Intent.ACTION_PICK)
                        intent.type = "image/*"
                        startActivityForResult(context,intent, 201,null)
                    } else {
                        Toast.makeText(context, "Allow permissions", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).check()
    }
}