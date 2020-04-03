package com.projectt.mg_location.DataModel

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    val PREFS_FILENAME = "com.projectt.mg_location.DataModel.prefs"
    val AccID = "accid"
    var prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var Acc_ID: String
        get() = prefs.getString(AccID, "")
        set(value) = prefs.edit().putString(AccID, value).apply()
    var myclass: String
        get() = prefs.getString("myclass", "")
        set(value) = prefs.edit().putString("myclass", value).apply()
    var isadmin: String
        get() = prefs.getString("isadmin", "")
        set(value) = prefs.edit().putString("isadmin", value).apply()
    var type: String
        get() = prefs.getString("type", "")
        set(value) = prefs.edit().putString("type", value).apply()
    var notifications: Boolean
        get() = prefs.getBoolean("notifications", false)
        set(value) = prefs.edit().putBoolean("notifications", value).apply()
    var startdial: Boolean
        get() = prefs.getBoolean("startdial", false)
        set(value) = prefs.edit().putBoolean("startdial", value).apply()
}