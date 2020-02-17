package com.ritier.my_memo.UI

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ritier.my_memo.Model.MemoModel
import com.ritier.my_memo.R
import com.ritier.my_memo.UI.Adapter.ImageAdapter
import com.ritier.my_memo.Util.getRealPathFromUri
import com.ritier.my_memo.Util.getRealmLastId
import com.ritier.my_memo.ViewModel.MemoViewModel
import io.realm.Realm
import io.realm.RealmList

class AddActivity : AppCompatActivity() {

    lateinit var memoViewModel: MemoViewModel
    lateinit var lt_image: ConstraintLayout
    lateinit var ev_title: EditText
    lateinit var ev_desc: EditText
    lateinit var btn_submit: Button
    lateinit var rv_imageList: RecyclerView
    lateinit var imageAdapter: ImageAdapter
    lateinit var dialog: ImageDialog
    lateinit var galleryClickListener: View.OnClickListener
    lateinit var cameraClickListener: View.OnClickListener
    lateinit var linkClickListener: View.OnClickListener

    val TAG = "AddActivity"
    val RC_GALLERY = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        lt_image = findViewById(R.id.lt_image)
        ev_title = findViewById(R.id.ev_title)
        ev_desc = findViewById(R.id.ev_desc)
        btn_submit = findViewById(R.id.btn_submit)
        rv_imageList = findViewById(R.id.rv_imageList)
        imageAdapter = ImageAdapter(this@AddActivity)
        memoViewModel = ViewModelProviders.of(this@AddActivity).get(MemoViewModel::class.java)

        initRecyclerView()
        setImageDialog()


        btn_submit.setOnClickListener {
            submitMemo()
        }

        lt_image.setOnClickListener {
            dialog.show()
        }
    }

    private fun submitMemo() {
        if (!TextUtils.isEmpty(ev_desc.text.toString()) && !TextUtils.isEmpty(ev_title.text.toString())) {
            val realm = Realm.getDefaultInstance()
            val realmList = RealmList<String>()
            imageAdapter.getImages().forEach {
                realmList.add(it)
            }
            val memo = MemoModel(
                getRealmLastId(realm),
                realmList,
                ev_title.text.toString(),
                ev_desc.text.toString()
            )
            memoViewModel.addMemo(memo)
            ev_desc.text.clear()
            ev_title.text.clear()
            Toast.makeText(applicationContext, "메모가 추가되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(applicationContext, "메모를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setImageDialog() {
        galleryClickListener = View.OnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, RC_GALLERY)
            dialog.cancel()
        }

        cameraClickListener = View.OnClickListener {
            //TODO : 카메라에서 찍은 사진 갤러리에 저장하고 경로 가져오기
            dialog.cancel()
        }

        linkClickListener = View.OnClickListener {
            //TODO : 링크 받아서 GLIDE로 띄우기
            dialog.cancel()
        }

        dialog = ImageDialog(this, galleryClickListener, cameraClickListener, linkClickListener)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun initRecyclerView() {
        rv_imageList.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        rv_imageList.adapter = imageAdapter
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                val imageUri = data?.data
                val imagePath = getRealPathFromUri(applicationContext, imageUri!!)

                imageAdapter.setImageData(imagePath)
            } else {
                Log.d(TAG, "갤러리에서 이미지를 가져오는 것이 중지되었습니다.")
                Toast.makeText(this, "갤러리에서 사진 가져오기가 중지되었습니다.", Toast.LENGTH_SHORT).show()
            }

        }

    }


    override fun onDestroy() {
        super.onDestroy()
        memoViewModel.dispose()
    }
}
