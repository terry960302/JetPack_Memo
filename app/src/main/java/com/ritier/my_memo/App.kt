package com.ritier.my_memo

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder().name("memo.realm").build()
        Realm.setDefaultConfiguration(config)
    }
}