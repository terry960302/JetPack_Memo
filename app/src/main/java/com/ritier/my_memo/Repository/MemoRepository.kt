package com.ritier.my_memo.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ritier.my_memo.Model.MemoModel
import com.ritier.my_memo.Util.RealmLiveData
import com.ritier.my_memo.Util.asLiveData
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort

class MemoRepository {

    val TAG = "MemoRepository"
    val realm: Realm by lazy { Realm.getDefaultInstance() }

    init {
        val config = RealmConfiguration.Builder().name("memo.realm").build()
        Realm.setDefaultConfiguration(config)
    }

    fun getAllMemo(): RealmLiveData<MemoModel> =
        realm.where(MemoModel::class.java).sort("id", Sort.DESCENDING).findAll().asLiveData()

    fun getOneMemo(id: Int): MutableLiveData<MemoModel> {
        val data = MutableLiveData<MemoModel>()

        realm.beginTransaction()
        val result: MemoModel? = realm.where(MemoModel::class.java).equalTo("id", id).findFirst()
        realm.commitTransaction()

        data.value = result
        return data
    }

    fun addMemo(memo: MemoModel) {
        realm.beginTransaction()
        realm.copyToRealm(memo)
        realm.commitTransaction()

        Log.d(TAG, "${memo.id}번째 메모를 추가했습니다.")
    }

    fun deleteMemo(id: Int) {
        realm.beginTransaction()
        val result: MemoModel? = realm.where(MemoModel::class.java).equalTo("id", id).findFirst()
        result?.deleteFromRealm()
        realm.commitTransaction()

        Log.d(TAG, "$id 번째 메모가 삭제되었습니다.")
    }

    fun updateMemo(memo: MemoModel) {

    }

    fun dispose() {
        realm.close()
    }
}