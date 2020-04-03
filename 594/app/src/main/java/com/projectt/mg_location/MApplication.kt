package com.projectt.mg_location

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class MApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}