package com.projectt.mg_location.DataModel

import android.util.Log
import com.google.firebase.database.DataSnapshot
import java.lang.Exception

class ExtraCur (snapshot: DataSnapshot) {

    var id: String? = ""
    var info: String? = ""
    var day: String? = ""
    var place: String? = ""
    var from: String? = ""
    var to: String? = ""
    var teacher: String? = ""
    var name: String? = ""

    init
    {
        try
        {
            val data: HashMap<String, Any> = snapshot.value as HashMap<String, Any>
            id = snapshot.key ?: ""
            info = data["Info"] as String
            teacher = data["Teacher"] as String
            name = data["Name"] as String
            from = data["From"] as String
            to = data["To"] as String
            day = data["Day"] as String
            place = data["Place"] as String
            Log.i("info", "INFO+ " +id+info+teacher+name)
        } catch (e: Exception)
        {
            e.printStackTrace()
        }
    }
}