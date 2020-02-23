package com.ritier.my_memo.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ViewModelFactory(val context : Context) : ViewModelProvider.Factory {
    //원래 ViewModel에서 파라미터로 받으면 안되는데 context를 받기 위해서 Factory를 만들어줌.
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageViewModel::class.java)) {
            return ImageViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}