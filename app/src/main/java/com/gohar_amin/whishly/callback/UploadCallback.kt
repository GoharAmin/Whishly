package com.gohar_amin.whishly.callback

interface UploadCallback<T>:BaseCallback {
    fun onCompleted(Data:T)
    fun onProgress(progress:Long)
}