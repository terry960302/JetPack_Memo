package com.ritier.my_memo.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ritier.my_memo.MemoAdapter
import com.ritier.my_memo.Model.MemoModel
import com.ritier.my_memo.R
import com.ritier.my_memo.ViewModel.MemoViewModel
import io.realm.Realm

class MainActivity : AppCompatActivity() {

    lateinit var memoViewModel: MemoViewModel
    lateinit var  memoAdapter : MemoAdapter
    lateinit var rv_memoList : RecyclerView
    lateinit var lt_add : ConstraintLayout
    lateinit var dataList : MutableList<MemoModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lt_add = findViewById(R.id.lt_add)
        dataList = mutableListOf()
        rv_memoList = findViewById(R.id.rv_memoList)
        memoAdapter = MemoAdapter(this, dataList)
        memoViewModel = ViewModelProviders.of(this).get(MemoViewModel::class.java)

        Realm.init(applicationContext)

        getAllMemo()
        setRecyclerView()

        lt_add.setOnClickListener {
            navigateToAdd()
        }
    }

    private fun navigateToAdd(){
        val intent = Intent(this@MainActivity, AddActivity::class.java)
        startActivity(intent)

    }

    private fun setRecyclerView(){
        rv_memoList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_memoList.adapter = memoAdapter
    }

   private fun getAllMemo(){
       memoViewModel.getMemo().observe(this, Observer { memoList ->
           memoAdapter.setData(memoList)
       })
   }
}
