package com.projectt.mg_location.DataModel

import android.util.Log
import com.google.firebase.database.DataSnapshot

class ClassWork(snapshot: DataSnapshot) {
    var id: String? = ""
    var info: String? = ""
    var data1: String? = ""
    var type: String? = ""
    var teacher: String? = ""
    var classs: String? = ""
    var subject: String? = ""

    init {
        try {
            val data: HashMap<String, Any> = snapshot.value as HashMap<String, Any>
            id = snapshot.key ?: ""
            info = data["Info"] as String
            data1 = data["Date"] as String
            type = data["Type"] as String
            teacher = data["Teacher"] as String
            classs = data["Class"] as String
            subject = data["Subject"] as String

            //Log.i("info", "INFO+ " + id + " " + name)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
