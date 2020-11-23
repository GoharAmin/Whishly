package com.gohar_amin.whishly.callback

interface ConfirmationCallback<T> {
    fun onConfirm(obj:T)
    fun onCancel()
}