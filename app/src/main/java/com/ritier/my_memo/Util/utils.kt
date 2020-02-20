package com.ritier.my_memo.Util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.ritier.my_memo.Model.MemoModel
import com.ritier.my_memo.R
import io.realm.Realm
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*


val TAG: String by lazy { "Utils" }

fun getRandColor(): Int {

    val randomColors = listOf<Int>(
        R.color.randColor1,
        R.color.randColor2,
        R.color.randColor3,
        R.color.randColor4,
        R.color.randColor5,
        R.color.randColor6,
        R.color.randColor7,
        R.color.randColor8,
        R.color.randColor9,
        R.color.randColor10,
        R.color.randColor11,
        R.color.randColor12,
        R.color.randColor13,
        R.color.randColor14
    )
    val random = Random()
    val randInt = random.nextInt(randomColors.size)
    return randomColors[randInt]
}

fun getRealmLastId(realm: Realm): Int {
    val primaryKey: Int = (realm.where(MemoModel::class.java).max("id") ?: -1).toInt() + 1
    return primaryKey
}

fun getBinaryFromBitmap(imageBitmap: Bitmap): String {
    return try {
        val byteArrayOutputStream = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream)
        val imageBytes = byteArrayOutputStream.toByteArray()
        val bitmapBinary = Base64.encodeToString(imageBytes, Base64.DEFAULT)
        bitmapBinary
    } catch (e: IOException) {
        e.printStackTrace()
        ""
    }
}

fun getBitmapFromBinary(imageBinary: String): Bitmap? {
    return try {
        val imageBytes = Base64.decode(imageBinary, Base64.DEFAULT)
        val imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        imageBitmap
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

fun getBitmapFromUri(context: Context, imageUri: Uri): Bitmap? {
    return try {
        MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
    } catch (e: FileNotFoundException) {
        Log.e(TAG, e.message!!)
        e.printStackTrace()
        null
    } catch (e: IOException) {
        Log.e(TAG, e.message!!)
        e.printStackTrace()
        null
    }
}

object GlidePlaceHolder {
    fun circularPlaceHolder(context: Context?): CircularProgressDrawable {
        val circularProgressDrawable = CircularProgressDrawable(context!!)
        circularProgressDrawable.centerRadius = 20f
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.start()
        return circularProgressDrawable
    }
}