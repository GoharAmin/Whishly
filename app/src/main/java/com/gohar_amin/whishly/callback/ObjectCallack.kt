package com.gohar_amin.whishly.callback

interface ObjectCallack<T>:BaseCallback {
    fun onData(obj:T)
}