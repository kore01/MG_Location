package com.projectt.mg_location.DataModel

import android.util.Log
import com.google.firebase.database.DataSnapshot
import java.lang.Exception

class Teachers(snapshot: DataSnapshot)
{
    var id: String? = ""
    var name: String? = ""
    var familyname: String? = ""
    var number: String? = ""
    var subject1: String? = ""
    var code: String? = ""

    init
    {
        try
        {
            val data: HashMap<String, Any> = snapshot.value as HashMap<String, Any>
            id = snapshot.key ?: ""
            name = data["Name"] as String
            familyname = data["FamilyName"] as String
            subject1 = data["Subject"] as String
            number = data["Number"] as String
            code = data["Code"] as String


           // Log.i("info", id+name+familyname+number+subject1)
        } catch (e: Exception)
        {
            e.printStackTrace()
        }
    }

}