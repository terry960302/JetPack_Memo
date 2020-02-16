package com.ritier.my_memo.UI

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ritier.my_memo.MemoAdapter
import com.ritier.my_memo.R
import com.ritier.my_memo.ViewModel.MemoViewModel
import io.realm.Realm

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    lateinit var memoViewModel: MemoViewModel
    lateinit var memoAdapter: MemoAdapter
    lateinit var rv_memoList: RecyclerView
    lateinit var lt_add: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(applicationContext)

        lt_add = findViewById(R.id.lt_add)
        rv_memoList = findViewById(R.id.rv_memoList)
        memoAdapter = MemoAdapter(this)
        memoViewModel = ViewModelProviders.of(this).get(MemoViewModel::class.java)

        setRecyclerView()
        getAllMemo()

        lt_add.setOnClickListener {
            navigateToAdd()
        }
    }

    private fun navigateToAdd() {
        val intent = Intent(this@MainActivity, AddActivity::class.java)
        startActivity(intent)
    }

    private fun setRecyclerView() {
        rv_memoList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_memoList.adapter = memoAdapter
    }

    private fun getAllMemo() {
        memoViewModel.getAllMemo().observe(this, Observer {
            memoAdapter.setData(it!!.toMutableList())
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        memoViewModel.dispose()
    }

}
