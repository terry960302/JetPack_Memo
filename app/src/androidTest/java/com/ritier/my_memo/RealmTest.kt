package com.ritier.my_memo

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.realm.Realm
import io.realm.RealmConfiguration
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class RealmTest {

    val context = InstrumentationRegistry.getInstrumentation().targetContext
    lateinit var mockRealm : Realm

    @Before
    fun setUp(){
        Realm.init(context)
        val testConfig = RealmConfiguration.Builder().inMemory().name("test.realm").build()
        Realm.setDefaultConfiguration(testConfig)
        mockRealm = Realm.getDefaultInstance()
    }

    @Test
    @Throws(Exception::class)
    fun checkRealmInstance(){
        Assert.assertThat(Realm.getDefaultInstance(), CoreMatchers.`is`(mockRealm))
    }

    @After
    @Throws(Exception::class)
    fun dispose(){
        mockRealm.close()
        print("Realm 작동 여부 테스트였습니다.")
    }

}