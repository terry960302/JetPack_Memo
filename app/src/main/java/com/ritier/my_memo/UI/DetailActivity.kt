package com.ritier.my_memo.UI

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.ritier.my_memo.R
import com.ritier.my_memo.ViewModel.MemoViewModel

class DetailActivity : AppCompatActivity() {

    lateinit var tv_desc: TextView
    lateinit var tv_time: TextView
    lateinit var lt_delete: ConstraintLayout
    lateinit var tv_title: TextView
    lateinit var rv_images: RecyclerView
    lateinit var memoViewModel: MemoViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        tv_desc = findViewById(R.id.tv_memoDesc)
        tv_time = findViewById(R.id.tv_memoTime)
        lt_delete = findViewById(R.id.lt_delete)
        tv_title = findViewById(R.id.tv_title)
        rv_images = findViewById(R.id.rv_images)
        memoViewModel = ViewModelProviders.of(this).get(MemoViewModel::class.java)

        //메모 상세 불러오기
        memoViewModel.getOneMemo(getMemoId()).observe(this, Observer {
            tv_desc.text = it.desc
            tv_time.text = it.time
            tv_title.text = it.id.toString() + " 번째 MEMO"
        })

        //메모 삭제
        lt_delete.setOnClickListener {
            showDialog()
        }


    }

    private fun getMemoId(): Int {
        val intent = intent
        return intent.getIntExtra("memoId", 0)
    }

    private fun showDialog(){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("메모 삭제").setMessage("정말로 삭제하시겠습니까?")
        dialogBuilder.setPositiveButton("네") { dialog, position ->
            memoViewModel.deleteMemo(getMemoId())
            Toast.makeText(
                applicationContext,
                "${getMemoId()} 번째 메모가 삭제되었습니다.",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }.setNegativeButton("아니오") { dialog, position ->
            dialog.cancel()
        }
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        memoViewModel.dispose()
    }
}
