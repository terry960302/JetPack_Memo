package com.ritier.my_memo
import com.ritier.my_memo.Model.MemoModel
import io.realm.RealmList
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ModelTest  {

    lateinit var memo : MemoModel

    @Before
    fun setUp(){
        memo = MemoModel()
        memo.id = 1234
        memo.title = "test_title"
        memo.desc = "test_description"
        memo.thumbPathList = null
    }

    @Test
    fun checkId(){
        assertEquals(1234, memo.id)
    }

    @Test
    fun checkTitle(){
        assertEquals("test_title", memo.title)
    }

    @Test
    fun checkImageList(){
        val list = RealmList<String>()
        list.addAll( mutableListOf("image1", "image2"))
        memo.thumbPathList = list
        assertEquals(mutableListOf("image1", "image2"), memo.thumbPathList)
    }

    @After
    fun done(){
        print("Memo 모델 테스트를 수행했습니다.")
    }

}