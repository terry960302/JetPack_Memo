package com.ritier.my_memo.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ritier.my_memo.R

class AddActivity : AppCompatActivity() {

    lateinit var lt_image : ConstraintLayout
    lateinit var ev_memoInput : EditText
    lateinit var btn_submit : Button
    lateinit var rv_imageList : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
    }
}
