package com.ritier.my_memo.Util

import com.ritier.my_memo.R
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

fun getTime() : String{
    val  currentTime : Date = Calendar.getInstance().time
    return SimpleDateFormat("HH : mm", Locale.KOREA).format(currentTime)
}