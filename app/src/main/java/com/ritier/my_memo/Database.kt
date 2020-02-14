package com.ritier.my_memo

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Dog(
    var name: String = "",
    var age: Int = 0
) : RealmObject()

open class Person(
    @PrimaryKey
    var id: Long = 0,
    var name: String = "",
    var dogs: RealmList<Dog> = RealmList()
) : RealmObject()