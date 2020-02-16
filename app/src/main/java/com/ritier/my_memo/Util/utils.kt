package com.ritier.my_memo.Util

import com.ritier.my_memo.Model.MemoModel
import com.ritier.my_memo.R
import io.realm.Realm
import java.text.SimpleDateFormat
import java.util.*

fun getRandIcon(): Int {
    val randomIconList: MutableList<Int> = mutableListOf(
        R.drawable.ic_dessert,
        R.drawable.ic_house,
        R.drawable.ic_tv,
        R.drawable.ic_winner
    )
    val random = Random()
    val randInt = random.nextInt(randomIconList.size)

    return randomIconList[randInt]
}

fun getTime(): String {
    val currentTime: Calendar = Calendar.getInstance()
    currentTime.add(Calendar.HOUR, 9)
    return SimpleDateFormat("HH : mm", Locale.KOREA).format(currentTime.time)
}

fun getRealmLastId(realm: Realm): Int {
    val primaryKey: Int = (realm.where(MemoModel::class.java).max("id") ?: -1).toInt() + 1
    return primaryKey
}

//fun setIdString(id: Int): String {
//    val lastChar = id.toString()[id.toString().length-1].toString()
//    val lastBeforeChar = id.toString()[id.toString().length-2].toString()
//    return if (id.toString().length == 2 && lastBeforeChar == "1") {
//        id.toString() + "th"
//    } else {
//        when (lastChar) {
//            "1" -> id.toString() + "st"
//            "2" -> id.toString() + "nd"
//            "3" -> id.toString() + "rd"
//            else -> id.toString() + "th"
//        }
//    }
//}