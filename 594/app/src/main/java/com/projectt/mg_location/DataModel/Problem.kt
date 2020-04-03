package com.projectt.mg_location.DataModel

import android.util.Log
import com.google.firebase.database.DataSnapshot
import java.lang.Exception

class Problem(snapshot: DataSnapshot) {

    var id: String? = ""
    var info: String? = ""

    init {
        try {
            val data: HashMap<String, Any> = snapshot.value as HashMap<String, Any>
            id = snapshot.key ?: ""
            info = data["Info"] as String
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}