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
class GetOneMemoTest {

    lateinit var realm: Realm

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
            this.id = 1
            this.title = "test_title1"
            this.desc = "test_desc1"
            this.thumbPathList = RealmList<String>("image1")
        }

        realm.beginTransaction()
        realm.copyToRealm(memo1)
        realm.commitTransaction()
    }

    @Test
    fun getOneMemo() {
        val id: Int = 1
        val memo = realm.where(MemoModel::class.java).equalTo("id", id).findFirst()
        Assert.assertEquals("image1", memo?.thumbPathList?.get(0))
    }

    @After
    fun dispose(){
        realm.close()
        print("Realm 데이터 하나 가져오기 테스트를 수행하였습니다.")
    }

}