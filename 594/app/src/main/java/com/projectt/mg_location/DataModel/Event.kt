package com.projectt.mg_location.DataModel

import android.util.Log
import com.google.firebase.database.DataSnapshot

class Event(snapshot: DataSnapshot) {
    var id: String? = ""
    var info: String? = ""
    var data1: String? = ""
    var time: String? = ""
    var place: String? = ""
    var name: String? = ""
    var type: String? = ""
    var access: String? = ""

    init {
        try {
            val data: HashMap<String, Any> = snapshot.value as HashMap<String, Any>
            id = snapshot.key ?: ""
            info = data["Info"] as String
            time = data["Time"] as String
            name = data["Name"] as String
            place = data["Place"] as String
            data1 = data["Data"] as String
            type = data["Type"] as String
            access = data["Access"] as String

            Log.i("info", "INFO+ " + id + " " + name)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
