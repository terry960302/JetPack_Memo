package com.ritier.my_memo.Model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MemoModel(
    @PrimaryKey
    var id: Int = 0,
    var thumbPathList: RealmList<String>? = RealmList(),
    var time: String = "",
    var desc: String = ""
) : RealmObject()