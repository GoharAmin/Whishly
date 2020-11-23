package com.gohar_amin.whishly.callback

interface ArrayCallback<T> :BaseCallback{
    fun onData(list:T?)
}