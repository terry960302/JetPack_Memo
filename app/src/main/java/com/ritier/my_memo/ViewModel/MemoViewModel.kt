package com.ritier.my_memo.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ritier.my_memo.Model.MemoModel
import com.ritier.my_memo.Repository.MemoRepository

class MemoViewModel : ViewModel() {

    private val memoRepository : MemoRepository by lazy { MemoRepository() }
    private val TAG = "MemoViewModel"

    fun getAllMemo() : MutableLiveData<MutableList<MemoModel>> =  memoRepository.getAllMemo()

    fun addMemo(memo : MemoModel) = memoRepository.addMemo(memo)

    fun deleteMemo(){

    }

    fun updateMemo(){

    }

    fun dispose() = memoRepository.dispose()
}