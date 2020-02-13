package com.ritier.my_memo

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ritier.my_memo.Model.MemoModel

class MainActivity : AppCompatActivity() {

    lateinit var  adapter : MemoAdapter
    lateinit var rv_memoList : RecyclerView
    lateinit var lt_add : ConstraintLayout

    lateinit var dataList : MutableList<MemoModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lt_add = findViewById(R.id.lt_add)
        lt_add.setOnClickListener {
            val intent = Intent(this@MainActivity, AddActivity::class.java)
            startActivity(intent)
        }

        setDummyData()
        setRecyclerView()
    }

    fun setDummyData(){
        dataList = mutableListOf()

        dataList.add(MemoModel(null, "6 : 00", "Feeding my GoldFish..."))
        dataList.add(MemoModel(null, "6 : 00", "Feeding my GoldFish..."))
        dataList.add(MemoModel(null, "6 : 00", "Feeding my GoldFish..."))
        dataList.add(MemoModel(null, "6 : 00", "Feeding my GoldFish..."))
        dataList.add(MemoModel(null, "6 : 00", "Feeding my GoldFish..."))
        dataList.add(MemoModel(null, "6 : 00", "Feeding my GoldFish..."))
        dataList.add(MemoModel(null, "6 : 00", "Feeding my GoldFish..."))
    }

    fun setRecyclerView(){
        rv_memoList = findViewById(R.id.rv_memoList)
        adapter = MemoAdapter(this, dataList)

        rv_memoList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_memoList.adapter = adapter
    }
}
