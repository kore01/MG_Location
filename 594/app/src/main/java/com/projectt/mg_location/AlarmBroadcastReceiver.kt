package com.projectt.mg_location

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.NotificationCompat
import com.projectt.mg_location.DataModel.Classes
import com.projectt.mg_location.DataModel.ClassesModel.getDataForClass
import java.util.*

class AlarmBroadcastReceiver : BroadcastReceiver() {

    var classes: ArrayList<Classes> = ArrayList()


    override fun onReceive(context: Context?, intent: Intent?) {

        var millis: Int = System.currentTimeMillis().toInt()

        var myclass: String = intent!!.getStringExtra("class")

        classes = getDataForClass(myclass) as ArrayList<Classes>

        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_WEEK)

        var room: String
        var subject: String
        var classs = intent!!.getIntExtra("sub",0)
        var time : Int
        if(classs==0)
        {
            return ;
        }




        for(i in classes)
        {
        //    if(i.classs == myclass )


        }

        when (day) {
            Calendar.MONDAY -> {
            }
            Calendar.TUESDAY -> {
            }
            Calendar.WEDNESDAY -> {
            }
            Calendar.THURSDAY -> {
            }
            Calendar.FRIDAY -> {

            }
        }// Current day is Sunday
        // Current day is Monday
        // etc.
        // Create the notification to be shown
        val mBuilder = NotificationCompat.Builder(context!!, "my_app")
            .setSmallIcon(R.mipmap.logo)
            .setContentTitle("Следващ час")
            .setContentText("WAAAT, why?. " + millis / 1000 / 60 / 60 + " "+myclass+" "+day)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        mBuilder.setVibrate(longArrayOf(1000, 1000, 1000))
        mBuilder.setLights(Color.BLUE, 3000, 3000);

        // Get the Notification manager service
        val am = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Generate an Id for each notification
        val id = System.currentTimeMillis() / 1000

        // Show a notification
        am.notify(id.toInt(), mBuilder.build())
    }
}