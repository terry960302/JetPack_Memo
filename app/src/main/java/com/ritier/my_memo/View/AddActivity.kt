package com.ritier.my_memo.View

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import com.ritier.my_memo.Util.getRealmLastId
import com.ritier.my_memo.View.Adapter.ImageAdapter
import com.ritier.my_memo.View.Dialog.ImageDialog
import com.ritier.my_memo.View.Interface.OnListItemClickListener
import com.ritier.my_memo.ViewModel.MemoViewModel
import io.realm.Realm
import io.realm.RealmList
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

class AddActivity : AppCompatActivity() {

    lateinit var memoViewModel: MemoViewModel
    lateinit var lt_image: ConstraintLayout
    lateinit var ev_title: EditText
    lateinit var ev_desc: EditText
    lateinit var btn_submit: Button
    lateinit var rv_imageList: RecyclerView
    lateinit var imageAdapter: ImageAdapter
    lateinit var imageSelectDialog: ImageDialog
    lateinit var galleryClickListener: View.OnClickListener
    lateinit var cameraClickListener: View.OnClickListener
    lateinit var linkClickListener: View.OnClickListener
    lateinit var cameraImageUri: Uri

    val TAG = "AddActivity"
    val RC_GALLERY = 1001
    val RC_CAMERA = 1002
    val RC_LINK = 1003

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
            imageSelectDialog.show()
        }

        imageAdapter.setOnImageClickListener(object :
            OnListItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                showDeleteDialog(position)
            }
        })
    }

    private fun submitMemo() {
        if (!TextUtils.isEmpty(ev_desc.text.toString()) && !TextUtils.isEmpty(ev_title.text.toString())) {
            val realm = Realm.getDefaultInstance()
            val realmImageList = RealmList<String>()
            realmImageList.addAll(imageAdapter.getImages())
            val memo = MemoModel(
                getRealmLastId(realm),
                realmImageList,
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

    private fun showDeleteDialog(position: Int) {
        val dialogBuilder = AlertDialog.Builder(this@AddActivity)
        dialogBuilder.setTitle("이미지 삭제").setMessage("정말로 삭제하시겠습니까?")
        dialogBuilder.setPositiveButton("네") { dialog, i ->
            imageAdapter.deleteImage(position)
            dialog.cancel()
        }.setNegativeButton("아니오") { dialog, i ->
            dialog.cancel()
        }
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun setImageDialog() {
        galleryClickListener = View.OnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            //TODO : 구글 포토에서 가져온 이미지의 경우는 보안문제 때문에 경로로 가져오는것에서 에러 터짐.
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(Intent.createChooser(intent, "Select File"), RC_GALLERY)
            }else{
                Toast.makeText(this, "구글 포토 보안문제 발생", Toast.LENGTH_SHORT).show()
            }
            imageSelectDialog.cancel()
        }

        cameraClickListener = View.OnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val imageContent = ContentValues()
            imageContent.put(MediaStore.Images.Media.TITLE, UUID.randomUUID().toString())
            imageContent.put(MediaStore.Images.Media.DESCRIPTION, "LINE 메모앱 테스트용 이미지입니다.")
            cameraImageUri =
                contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageContent)!!
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri)
            startActivityForResult(intent, RC_CAMERA)
            imageSelectDialog.cancel()
        }

        linkClickListener = View.OnClickListener {
            //TODO : 링크 받아서 GLIDE로 띄우기
            imageSelectDialog.cancel()
        }

        imageSelectDialog =
            ImageDialog(
                this,
                galleryClickListener,
                cameraClickListener,
                linkClickListener
            )
        imageSelectDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        imageSelectDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun initRecyclerView() {
        rv_imageList.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        rv_imageList.setHasFixedSize(true)
        rv_imageList.setItemViewCacheSize(10)
        rv_imageList.adapter = imageAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                //갤러리는 이미 저장된 이미지이기 때문에 uri가 있음(uri는 파일의 경로)
                val imageUri = data?.data
                imageAdapter.addImage(imageUri.toString())
            } else {
                Log.d(TAG, "갤러리에서 이미지를 가져오는 것이 중지되었습니다.")
                Toast.makeText(this, "갤러리에서 사진 가져오기가 중지되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        if (requestCode == RC_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                //카메라의 경우에는 따로 사진첩에 저장하고 그 uri를 받아오는 식으로 구현
                //내장디비라서 바이너리 자체를 Realm에 저장할 경우 16MB 용량 제한이 있어서 한계가 생김.
                imageAdapter.addImage(cameraImageUri.toString())
            } else {
                Log.d(TAG, "카메라에서 이미지 가져오는 것이 중지되었습니다.")
                Toast.makeText(this, "카메라에서 이미지 가져오는 것이 중지되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        memoViewModel.dispose()
    }
}
