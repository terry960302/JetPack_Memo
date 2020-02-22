package com.ritier.my_memo.RealmTest

import com.ritier.my_memo.Model.MemoModel
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DeleteMemoTest {

    lateinit var realm : Realm

    @Before
    fun setRealm(){
        RealmInstanceTest().setUp()
        val testConfig = RealmConfiguration.Builder().inMemory().name("test.realm").build()
        realm = Realm.getInstance(testConfig)
    }

    @Before
    fun addMemo(){
        realm.executeTransaction {
            val memo = MemoModel()
            memo.id = 1
            memo.title = "test_title"
            memo.desc = "test_description"
            memo.thumbPathList = RealmList("image_sample...")

            it.copyToRealm(memo)
        }
    }

    @Test
    fun deleteMemo(){
        val id = 1
        realm.executeTransaction {
            val result : MemoModel? = it.where(MemoModel::class.java).equalTo("id", id).findFirst()
            result?.deleteFromRealm()
        }

        val memo =  realm.where(MemoModel::class.java).equalTo("id", id).findFirst()
        Assert.assertEquals(null, memo?.title)
    }

}