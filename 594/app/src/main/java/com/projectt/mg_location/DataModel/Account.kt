package com.projectt.mg_location.DataModel

import android.util.Log
import com.google.firebase.database.DataSnapshot
import java.lang.Exception

class Account  (snapshot: DataSnapshot) {
    var type: String? = ""
    var id: String? = ""
    var myclass: String? = ""
    var email:String? = ""
    var isadmin:String? = ""

    init
    {
        try
        {
            val data: HashMap<String, Any> = snapshot.value as HashMap<String, Any>
            id = snapshot.key ?: ""
            type = data["type"] as String
            myclass = data["myclass"] as String
            email = data["email"] as String
            isadmin = data["isadmin"] as String
            //Log.i("info", "INFO+ " +id+classs+classteacher+name)
        } catch (e: Exception)
        {
            e.printStackTrace()
        }
    }
}