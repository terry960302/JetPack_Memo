package com.ritier.my_memo.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ritier.my_memo.Model.MemoModel
import com.ritier.my_memo.Util.RealmLiveData
import com.ritier.my_memo.Util.asLiveData
import io.realm.Realm
import io.realm.Sort

class MemoRepository {

    val TAG = "MemoRepository"
    val realm: Realm by lazy { Realm.getDefaultInstance() }

    fun getAllMemo(): RealmLiveData<MemoModel> =
        realm.where(MemoModel::class.java).sort("id", Sort.DESCENDING).findAll().asLiveData()

    fun getOneMemo(id: Int): MutableLiveData<MemoModel>{
        val data = MutableLiveData<MemoModel>()
        realm.executeTransaction {
            val memo = it.where(MemoModel::class.java).equalTo("id", id).findFirst()
            data.postValue(memo)
        }
        return data
    }

    fun addMemo(memo: MemoModel) {
        realm.executeTransactionAsync {
            it.copyToRealm(memo)
        }
        Log.d(TAG, "${memo.id}번째 메모를 추가했습니다.")
    }

    fun deleteMemo(id: Int) {
        realm.executeTransactionAsync {
            val result : MemoModel? = it.where(MemoModel::class.java).equalTo("id", id).findFirst()
            result?.deleteFromRealm()
        }
        Log.d(TAG, "$id 번째 메모가 삭제되었습니다.")
    }

    fun updateMemo(memo: MemoModel) {
        realm.executeTransactionAsync {
            it.copyToRealmOrUpdate(memo)
        }
        Log.d(TAG, "${memo.id} 번째 메모가 업데이트되었습니다.")
    }

    fun dispose() {
        realm.close()
    }
}