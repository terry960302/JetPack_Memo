package com.ritier.my_memo.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ritier.my_memo.View.Adapter.ImageAdapter

class ImageViewModel(context: Context) : ViewModel() {

    var imageAdapter : ImageAdapter

    init {
        imageAdapter  = ImageAdapter(context)
    }

}