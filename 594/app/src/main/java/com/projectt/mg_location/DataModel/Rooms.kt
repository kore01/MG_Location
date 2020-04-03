package com.projectt.mg_location.DataModel

import android.util.Log
import com.google.firebase.database.DataSnapshot
import java.lang.Exception

class Rooms (snapshot: DataSnapshot)
{
    var id: String? = ""
    var type: String? = ""
    var info: String? = ""
    var number: String? = ""
    var location: String? = ""

    init
    {
        try
        {
            val data: HashMap<String, Any> = snapshot.value as HashMap<String, Any>
            id = snapshot.key ?: ""
            info = data["Info"] as String
            location = data["Location"] as String
            type = data["Type"] as String
            number = data["Number"] as String
            Log.i("info", id+info+location+number+type)
        } catch (e: Exception)
        {
            e.printStackTrace()
        }
    }
}