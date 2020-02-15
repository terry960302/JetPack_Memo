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