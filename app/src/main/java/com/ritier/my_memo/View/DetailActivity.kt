package com.ritier.my_memo.View

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ritier.my_memo.R
import com.ritier.my_memo.View.Adapter.ImageAdapter
import com.ritier.my_memo.View.Interface.OnListItemClickListener
import com.ritier.my_memo.ViewModel.MemoViewModel

class DetailActivity : AppCompatActivity() {

    val TAG = "DetailActivity"
    lateinit var tv_desc: TextView
    lateinit var tv_title: TextView
    lateinit var tv_app_bar: TextView
    lateinit var tv_noImage : TextView
    lateinit var lt_delete: ConstraintLayout
    lateinit var rv_images: RecyclerView
    lateinit var imageAdapter: ImageAdapter
    lateinit var memoViewModel: MemoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        tv_desc = findViewById(R.id.tv_memoDesc)
        tv_title = findViewById(R.id.tv_title)
        tv_app_bar = findViewById(R.id.tv_app_bar)
        tv_noImage = findViewById(R.id.tv_noImage)
        lt_delete = findViewById(R.id.lt_delete)
        rv_images = findViewById(R.id.rv_images)
        imageAdapter = ImageAdapter(this@DetailActivity)
        memoViewModel = ViewModelProviders.of(this@DetailActivity).get(MemoViewModel::class.java)

        initRecyclerView()
        getOneMemo()

        //메모 삭제
        lt_delete.setOnClickListener {
            showDeleteDialog()
        }

        imageAdapter.setOnImageClickListener(object : OnListItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                return
            }
        })
    }

    private fun initRecyclerView() {
        rv_images.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        rv_images.setHasFixedSize(true)
        rv_images.setItemViewCacheSize(10)
        rv_images.adapter = imageAdapter
    }

    @SuppressLint("SetTextI18n")
    private fun getOneMemo() {
        memoViewModel.getOneMemo(getMemoId()).observe(this, Observer {
            tv_desc.text = it.desc
            tv_title.text = it.title
            imageAdapter.setAllImageData(it.thumbPathList!!.toMutableList())
            tv_app_bar.text = it.id.toString() + "번째 MEMO"

            if(it.thumbPathList!!.isNotEmpty()){
                tv_noImage.visibility = View.GONE
            }else{
                tv_noImage.visibility = View.VISIBLE
            }
        })
    }

    private fun getMemoId(): Int = intent.getIntExtra("memoId", 0)

    private fun showDeleteDialog() {
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
}
