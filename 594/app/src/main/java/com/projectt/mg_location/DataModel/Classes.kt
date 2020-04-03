package com.projectt.mg_location.DataModel

import android.util.Log
import com.google.firebase.database.DataSnapshot
import java.lang.Exception

class Classes (snapshot: DataSnapshot)
{
    var id: String? = ""
    var classs: String? = ""
    var teachernumber: String? = ""
    var room: String? = ""
    var from: String? = ""
    var too: String? = ""
    var day: String? = ""
    var subject: String?=""
    //var secteachernumber: String? = ""
    //var secroom: String? = ""
    init
    {
        try
        {
            val data: HashMap<String, Any> = snapshot.value as HashMap<String, Any>
            id = snapshot.key ?: ""
            classs = data["Class"] as String
            teachernumber = data["TeacherNumber"] as String
  //          secteachernumber = data["SecondTeacherNumber"] as String
            room = data["Room"] as String
//            secroom=data["SecondRoom"] as String
            from = data["From"] as String
            too = data["To"] as String
            day = data["Day"] as String
            subject= data["Subject"] as String

            //Log.i("info", id+classs+classteacher+name)
        } catch (e: Exception)
        {
            e.printStackTrace()
        }
    }
}