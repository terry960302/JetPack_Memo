package com.ritier.my_memo.View

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ritier.my_memo.Model.MemoModel
import com.ritier.my_memo.R
import com.ritier.my_memo.View.Adapter.MemoAdapter
import com.ritier.my_memo.View.Interface.OnListItemClickListener
import com.ritier.my_memo.ViewModel.MemoViewModel
import io.realm.Realm


class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    lateinit var memoViewModel: MemoViewModel
    lateinit var memoAdapter: MemoAdapter
    lateinit var rv_memoList: RecyclerView
    lateinit var lt_add: ConstraintLayout
    lateinit var tv_noMemo : TextView
    val RC_PERMISSION = 1234
    val PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lt_add = findViewById(R.id.lt_add)
        rv_memoList = findViewById(R.id.rv_memoList)
        tv_noMemo = findViewById(R.id.tv_noMemo)
        memoAdapter = MemoAdapter(this)
        memoViewModel = ViewModelProviders.of(this).get(MemoViewModel::class.java)

        initRecyclerView()
        getAllMemo()

        lt_add.setOnClickListener {
            moveToAddActivity()
        }

        memoAdapter.setOnMemoClickListener(object :
            OnListItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val id = memoAdapter.getData(position).id
                val intent = Intent(applicationContext, DetailActivity::class.java)
                intent.putExtra("memoId", id)
                startActivity(intent)
            }
        })
    }

    private fun initRecyclerView() {
        rv_memoList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_memoList.setHasFixedSize(true)
        rv_memoList.setItemViewCacheSize(10)
        rv_memoList.adapter = memoAdapter
    }

    private fun getAllMemo() {
        memoViewModel.getAllMemo().observe(this, Observer {
            memoAdapter.setData(it!!.toMutableList())

            if(it.toMutableList().isNotEmpty()){
                tv_noMemo.visibility = View.GONE
            }else{
                tv_noMemo.visibility = View.VISIBLE
            }
        })
    }

    private fun moveToAddActivity() {
        val intent = Intent(this@MainActivity, AddActivity::class.java)
        startActivity(intent)
    }

    private fun moveToErrorActivity() {
        val intent = Intent(this@MainActivity, ErrorActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun hasPermissions(context: Context, vararg permissions: String): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun setPermission() {
        if (!hasPermissions(this, *PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, RC_PERMISSION);
        } else {
            Log.d(TAG, "퍼미션이 모두 허용된 사용자입니다.")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == RC_PERMISSION) {
            if (grantResults.isNotEmpty()) {
                //모두 거부
                if (PackageManager.PERMISSION_GRANTED !in grantResults) {
                    Toast.makeText(this, "모든 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
                    moveToErrorActivity()

                }
                //일부만 거부
                else if (PackageManager.PERMISSION_DENIED in grantResults) {
                    Toast.makeText(this, "모든 권한을 허용해주십시오.", Toast.LENGTH_SHORT).show()
                    moveToErrorActivity()
                }
            }
        }
    }

    override fun onStart() {
        setPermission()
        super.onStart()
    }

    override fun onDestroy() {
        rv_memoList.adapter = null
        super.onDestroy()
    }

}
