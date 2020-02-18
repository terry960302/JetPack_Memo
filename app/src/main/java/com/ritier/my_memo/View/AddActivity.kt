package com.ritier.my_memo.View

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import com.ritier.my_memo.Util.getRealPathFromUri
import com.ritier.my_memo.Util.getRealmLastId
import com.ritier.my_memo.View.Adapter.ImageAdapter
import com.ritier.my_memo.View.Dialog.ImageDialog
import com.ritier.my_memo.View.Interface.OnListItemClickListener
import com.ritier.my_memo.ViewModel.MemoViewModel
import io.realm.Realm
import io.realm.RealmList
import java.io.ByteArrayOutputStream
import java.io.IOException

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
        //TODO : 핸드폰 기종마다 다른데 경로를 받아서 하는게, 되는게 있고 안되는게 있음....
        galleryClickListener = View.OnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, RC_GALLERY)
            imageSelectDialog.cancel()
        }

        cameraClickListener = View.OnClickListener {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(takePictureIntent, RC_CAMERA)
                }
            }
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
                val imageUri = data?.data
                val imagePath = getRealPathFromUri(applicationContext, imageUri!!)

                imageAdapter.addImage(imagePath)
            } else {
                Log.d(TAG, "갤러리에서 이미지를 가져오는 것이 중지되었습니다.")
                Toast.makeText(this, "갤러리에서 사진 가져오기가 중지되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        if (requestCode == RC_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                val imageBitmap = data?.extras?.get("data") as Bitmap
                try {
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                    val bytes = byteArrayOutputStream.toByteArray()
                    val bitmapBinary = android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT)
                    imageAdapter.addImage(bitmapBinary)

//                    val path = Environment.getExternalStorageDirectory().toString()
//                    val outputStream = FileOutputStream(UUID.randomUUID().toString())
//                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
//                    MediaStore.Images.Media.insertImage(contentResolver, )
                } catch (e: IOException) {
                    e.printStackTrace()
                }
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
