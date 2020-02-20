package com.ritier.my_memo.View.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.webkit.URLUtil
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.ritier.my_memo.R
import com.ritier.my_memo.View.Adapter.ImageAdapter
import java.io.IOException
import java.net.URL


class LinkDialog(context: Context, val imageAdapter: ImageAdapter) : Dialog(context) {

    lateinit var ev_link: EditText
    lateinit var btn_submit: Button
    val TAG = "LinkDialog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.apply {
            this.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
            this.dimAmount = 0.8f
        }
        window?.attributes = layoutParams
        setContentView(R.layout.activity_add_link_dialog)

        ev_link = findViewById(R.id.ev_link)
        btn_submit = findViewById(R.id.btn_submit)

        btn_submit.setOnClickListener {
            if (!TextUtils.isEmpty(ev_link.text.toString())) {
                val url = ev_link.text.toString()
                if(URLUtil.isValidUrl(url)){
                    imageAdapter.addImage(url)
                    ev_link.text.clear()
                    this.cancel()
                }else{
                    Toast.makeText(context, "유효하지 않은 URL입니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "링크 부분을 채워주십시오.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}