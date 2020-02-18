package com.ritier.my_memo.View.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import com.ritier.my_memo.R

class ImageDialog(
    context: Context,
    private val galleryClickListener: View.OnClickListener,
    private val cameraClickListener: View.OnClickListener,
    private val linkClickListener: View.OnClickListener
) : Dialog(context) {

    lateinit var lt_gallery: ConstraintLayout
    lateinit var lt_camera: ConstraintLayout
    lateinit var lt_link: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.apply {
            this.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
            this.dimAmount = 0.8f
        }
        window?.attributes = layoutParams
        setContentView(R.layout.activity_add_dialog)

        lt_gallery = findViewById(R.id.lt_gallery)
        lt_camera = findViewById(R.id.lt_camera)
        lt_link = findViewById(R.id.lt_link)

        lt_gallery.setOnClickListener(galleryClickListener)
        lt_camera.setOnClickListener(cameraClickListener)
        lt_link.setOnClickListener(linkClickListener)
    }




}