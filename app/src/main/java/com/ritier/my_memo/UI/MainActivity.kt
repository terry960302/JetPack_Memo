package com.ritier.my_memo.UI

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ritier.my_memo.UI.Adapter.MemoAdapter
import com.ritier.my_memo.R
import com.ritier.my_memo.ViewModel.MemoViewModel
import io.realm.Realm

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    lateinit var memoViewModel: MemoViewModel
    lateinit var memoAdapter: MemoAdapter
    lateinit var rv_memoList: RecyclerView
    lateinit var lt_add: ConstraintLayout
    val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 11

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

    private fun setPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
            != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else{
            Log.d(TAG, "이미 퍼미션이 허용되어 있습니다.")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        memoViewModel.dispose()
    }

    override fun onStart() {
        //TODO : 미디어 접근 퍼미션 얻기
        setPermission()
        super.onStart()
    }

}
