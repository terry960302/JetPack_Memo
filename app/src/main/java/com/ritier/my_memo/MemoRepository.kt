package com.ritier.my_memo

import android.content.Context
import android.util.Log
import com.ritier.my_memo.Model.MemoModel
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults

class MemoRepository(context: Context) {

    val TAG = "MemoRepository"
    val realm: Realm by lazy { Realm.getDefaultInstance() }

    init {
        //Init Realm
        Realm.init(context)
        val config = RealmConfiguration.Builder().name("memo.realm").build()
        Realm.setDefaultConfiguration(config)
    }

    fun getMemo() {
        realm.beginTransaction()
        val results: RealmResults<MemoModel> = realm.where(MemoModel::class.java).findAll()
        realm.commitTransaction()
        Log.d(TAG, "가져온 결과물 : ${results.toString()}")
    }

    fun addMemo(memo: MemoModel) {

    }

    fun deleteMemo(id: Long) {

    }

    fun updateMemo(memo: MemoModel) {

    }
}