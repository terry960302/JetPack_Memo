package com.ritier.my_memo.UI

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.ritier.my_memo.Model.MemoModel
import com.ritier.my_memo.R
import com.ritier.my_memo.Util.getRealmLastId
import com.ritier.my_memo.Util.getTime
import com.ritier.my_memo.ViewModel.MemoViewModel
import io.realm.Realm

class AddActivity : AppCompatActivity() {

    lateinit var memoViewModel : MemoViewModel
    lateinit var lt_image : ConstraintLayout
    lateinit var ev_memoInput : EditText
    lateinit var btn_submit : Button
    lateinit var rv_imageList : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        lt_image = findViewById(R.id.lt_image)
        ev_memoInput  = findViewById(R.id.ev_memoInput)
        btn_submit = findViewById(R.id.btn_submit)
        rv_imageList = findViewById(R.id.rv_imageList)
        memoViewModel = ViewModelProviders.of(this@AddActivity).get(MemoViewModel::class.java)

        btn_submit.setOnClickListener{
            submitMemo()
        }

        lt_image.setOnClickListener {
            val dialog = ImageDialog(this, null, null, null)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }
    }

    private fun submitMemo(){
        if(!TextUtils.isEmpty(ev_memoInput.text.toString())){
            val realm = Realm.getDefaultInstance()
            val memo = MemoModel(getRealmLastId(realm), null, getTime(), ev_memoInput.text.toString())
            memoViewModel.addMemo(memo)
            ev_memoInput.text.clear()
            Toast.makeText(applicationContext, "메모가 추가되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }else{
            Toast.makeText(applicationContext, "메모를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private  fun addImages() {

    }

    override fun onDestroy() {
        super.onDestroy()
        memoViewModel.dispose()
    }
}
