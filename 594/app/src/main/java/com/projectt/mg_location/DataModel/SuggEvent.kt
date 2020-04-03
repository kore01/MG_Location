package com.projectt.mg_location.DataModel

import android.util.Log
import com.google.firebase.database.DataSnapshot
import java.lang.Exception

class SuggEvent(snapshot: DataSnapshot) {
    var date: String? = ""
    var id: String? = ""
    var desc: String? = ""
    var name: String? = ""
    var place: String? = ""
    var time: String? = ""

    init {
        try {
            val data: HashMap<String, Any> = snapshot.value as HashMap<String, Any>
            id = snapshot.key ?: ""
            date = data["date"] as String
            desc = data["desc"] as String
            name = data["name"] as String
            place = data["place"] as String
            time = data["time"] as String

            Log.i("suggevent", "INFO+ " + id + name)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}