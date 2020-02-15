package com.ritier.my_memo.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ritier.my_memo.Model.MemoModel
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import io.realm.Sort

class MemoRepository {

    val TAG = "MemoRepository"
    val realm: Realm by lazy { Realm.getDefaultInstance() }

    init {
        val config = RealmConfiguration.Builder().name("memo.realm").build()
        Realm.setDefaultConfiguration(config)
    }

    fun getMemo()  : LiveData<List<MemoModel>>{
        val data = MutableLiveData<List<MemoModel>>()

        realm.beginTransaction()
        val results: RealmResults<MemoModel> = realm.where(MemoModel::class.java).sort("id", Sort.DESCENDING).findAll()
        realm.commitTransaction()

        Log.d(TAG, "가져온 메모들 : $results")
        data.value = results
        return data
    }

    fun addMemo(memo: MemoModel) {
        realm.beginTransaction()
        realm.copyToRealm(memo)
        realm.commitTransaction()

        Log.d(TAG, "${memo.id}번째 메모를 추가했습니다.")
    }

    fun deleteMemo(id: Long) {

    }

    fun updateMemo(memo: MemoModel) {

    }

    fun dispose(){
        realm.close()
    }
}