package com.projectt.mg_location.DataModel

import android.util.Log
import com.google.firebase.database.DataSnapshot
import java.lang.Exception

class Class (snapshot: DataSnapshot)
{
    var id: String? = ""
    var classs: String? = ""
    var classteacher: String? = ""
    var name: String? = ""

    init
    {
        try
        {
            val data: HashMap<String, Any> = snapshot.value as HashMap<String, Any>
            id = snapshot.key ?: ""
            classs = data["Class"] as String
            classteacher = data["ClassTeacher"] as String
            name = data["Name"] as String
            Log.i("info", "INFO+ " +id+classs+classteacher+name)
        } catch (e: Exception)
        {
            e.printStackTrace()
        }
    }
}