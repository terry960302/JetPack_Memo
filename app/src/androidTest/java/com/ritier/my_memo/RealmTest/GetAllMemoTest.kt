package com.ritier.my_memo.RealmTest

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ritier.my_memo.Model.MemoModel
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GetAllMemoTest {
    lateinit var realm: Realm

    fun getRealmId() : Int{
       return (realm.where(MemoModel::class.java).max("id") ?: -1).toInt() + 1
    }

    @Before
    fun setRealm(){
        RealmInstanceTest().setUp()
        val testConfig = RealmConfiguration.Builder().inMemory().name("test.realm").build()
        realm = Realm.getInstance(testConfig)
    }

    @Before
    fun addMemo() {
        val memo1 = MemoModel()
        memo1.apply {
            this.id = getRealmId()
            this.title = "test_title1"
            this.desc = "test_desc1"
            this.thumbPathList = RealmList<String>("image1")
        }

        val memo2 = MemoModel()
        memo2.apply {
            this.id = getRealmId() + 1
            this.title = "test_title2"
            this.desc = "test_desc2"
            this.thumbPathList = RealmList<String>("image2")
        }
        realm.beginTransaction()
        realm.copyToRealm(memo1)
        realm.copyToRealm(memo2)
        realm.commitTransaction()
    }

    @Test
    fun getAllMemo(){
        val memos = realm.where(MemoModel::class.java).findAll()
        Assert.assertEquals(true, memos.size > 1)
    }

    @After
    fun dispose(){
        realm.close()
        print("Realm 데이터 모두 가져오기 테스트를 수행하였습니다.")
    }

}