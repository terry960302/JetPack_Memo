package com.ritier.my_memo.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ritier.my_memo.Dog
import com.ritier.my_memo.MemoAdapter
import com.ritier.my_memo.Model.MemoModel
import com.ritier.my_memo.Person
import com.ritier.my_memo.R
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.createObject

class MainActivity : AppCompatActivity() {

    lateinit var  adapter : MemoAdapter
    lateinit var rv_memoList : RecyclerView
    lateinit var lt_add : ConstraintLayout
    lateinit var dataList : MutableList<MemoModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lt_add = findViewById(R.id.lt_add)
        lt_add.setOnClickListener {
            val intent = Intent(this@MainActivity, AddActivity::class.java)
            startActivity(intent)
        }

        setDummyData()
        setRecyclerView()

        startRealm(this)
    }

    private fun setDummyData(){
        dataList = mutableListOf()

        dataList.add(MemoModel(0, null, "6 : 00", "Feeding my GoldFish..."))
        dataList.add(MemoModel(1, null, "6 : 00", "Feeding my GoldFish..."))
        dataList.add(MemoModel(2, null, "6 : 00", "Feeding my GoldFish..."))
        dataList.add(MemoModel(3, null, "6 : 00", "Feeding my GoldFish..."))
        dataList.add(MemoModel(4, null, "6 : 00", "Feeding my GoldFish..."))
        dataList.add(MemoModel(5, null, "6 : 00", "Feeding my GoldFish..."))
        dataList.add(MemoModel(6, null, "6 : 00", "Feeding my GoldFish..."))
    }

    private fun setRecyclerView(){
        rv_memoList = findViewById(R.id.rv_memoList)
        adapter = MemoAdapter(this, dataList)

        rv_memoList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_memoList.adapter = adapter
    }

    private fun startRealm(context : Context){

        //시작
        Realm.init(context)
        //인스턴스 세팅
        val realm = Realm.getDefaultInstance()

        //트렌젝션 처리
        realm.beginTransaction()
        val dog = Dog("asdasd", 1)
        val managedDog = realm.copyToRealm(dog)
        val primaryKey  : Int = (realm.where(Person::class.java).max("id") ?: -1).toInt() +1
        val person = realm.createObject<Person>(primaryKey)
        person.dogs.add(managedDog)
        realm.commitTransaction()

        val result  : RealmResults<Person> = realm.where(Person::class.java).findAll()


//
//        //데이터 변경을 감지
//        puppies.addChangeListener { result, changeSet ->
//            //쿼리 결과가 실시간으로 업데아트
//            changeSet.insertions
//        }
//
//        //비동기로 백그라운드 스레드에서 데이터 업데이트
//        realm.executeTransactionAsync(Realm.Transaction { bgRealm ->
//            val dog = bgRealm.where<Dog>().equalTo("age", 1.toInt()).findFirst()!!
//            dog.age = 3
//        },  Realm.Transaction.OnSuccess {
//            puppies.size
//            managedDog.age
//        } )

    }
}
