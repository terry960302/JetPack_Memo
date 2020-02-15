package com.ritier.my_memo.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ritier.my_memo.Model.MemoModel
import com.ritier.my_memo.Repository.MemoRepository

class MemoViewModel : ViewModel() {

    private val memoRepository : MemoRepository by lazy { MemoRepository() }

    fun getMemo() : LiveData<List<MemoModel>> =  memoRepository.getMemo()

    fun addMemo(memo : MemoModel) = memoRepository.addMemo(memo)

    fun deleteMemo(){

    }

    fun updateMemo(){

    }

    fun dispose() = memoRepository.dispose()
}