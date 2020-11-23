package com.gohar_amin.whishly.network

import com.gohar_amin.whishly.callback.UploadCallback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream
import java.security.PrivateKey

class UploadRequestBody<T>(private  val file:File,
                        private val contentType:String,
                        private val callback: UploadCallback<T>
) : RequestBody() {
    override fun contentType()= "$contentType/*".toMediaTypeOrNull()

    override fun writeTo(sink: BufferedSink) {
        val total=file.length()
        val buffer=ByteArray(DEFAULT_BUFFER_SIZE)
        val fileInputStream=FileInputStream(file)
        var uploaded=0L
        fileInputStream.use {
            var read:Int
            while(fileInputStream.read(buffer).also { read=it }!=-1){
                uploaded+=read
                sink!!.write(buffer,0,read)
                callback.onProgress((100*(uploaded/total)))
            }
        }
    }

    override fun contentLength(): Long {
        //max file length
        return file.length()
    }
    companion object{
        const val DEFAULT_BUFFER_SIZE:Int=1048
    }
}