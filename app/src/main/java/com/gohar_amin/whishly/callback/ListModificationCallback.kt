package com.gohar_amin.whishly.callback

interface ListModificationCallback<T> {
    fun onDelete(data:T)
    fun onEdit(data:T)
}