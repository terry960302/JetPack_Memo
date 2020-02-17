package com.ritier.my_memo.Util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.ritier.my_memo.Model.MemoModel
import com.ritier.my_memo.R
import io.realm.Realm
import java.util.*


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
    return randomColors.get(randInt)
}

fun getRealmLastId(realm: Realm): Int {
    val primaryKey: Int = (realm.where(MemoModel::class.java).max("id") ?: -1).toInt() + 1
    return primaryKey
}

fun getRealPathFromUri(context: Context, uri: Uri): String {
    val filePathColumn =
        arrayOf(MediaStore.Images.Media.DATA)
    // Get the cursor
    val cursor: Cursor? =
        context.contentResolver.query(uri, filePathColumn, null, null, null)
    // Move to first row
    cursor?.moveToFirst()
    //Get the column index of MediaStore.Images.Media.DATA
    val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
    //Gets the String value in the column
    val imgDecodableString = cursor?.getString(columnIndex!!)
    cursor?.close()
    return imgDecodableString!!
}
