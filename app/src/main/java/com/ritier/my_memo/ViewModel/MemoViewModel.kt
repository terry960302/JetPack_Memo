package com.ritier.my_memo.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ritier.my_memo.Model.MemoModel
import com.ritier.my_memo.Util.RealmLiveData
import com.ritier.my_memo.Repository.MemoRepository

class MemoViewModel : ViewModel() {

    private val memoRepository : MemoRepository by lazy { MemoRepository() }
    private val TAG = "MemoViewModel"

    fun getAllMemo() : RealmLiveData<MemoModel> =  memoRepository.getAllMemo()

    fun getOneMemo(id : Int) : MutableLiveData<MemoModel> = memoRepository.getOneMemo(id)

    fun addMemo(memo : MemoModel) = memoRepository.addMemo(memo)

    fun deleteMemo(id : Int)= memoRepository.deleteMemo(id)

    fun updateMemo(memo : MemoModel) = memoRepository.updateMemo(memo)

    override fun onCleared() {
        memoRepository.dispose()
        super.onCleared()
    }
}