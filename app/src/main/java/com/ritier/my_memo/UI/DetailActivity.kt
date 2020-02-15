package com.ritier.my_memo.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ritier.my_memo.R

class DetailActivity : AppCompatActivity() {

    lateinit var tv_desc  : TextView
    lateinit var tv_time : TextView
    lateinit var rv_images : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        tv_desc = findViewById(R.id.tv_memoDesc)
        tv_time = findViewById(R.id.tv_memoTime)
        rv_images = findViewById(R.id.rv_images)
    }
}
