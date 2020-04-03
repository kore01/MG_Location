package com.projectt.mg_location

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Context.ALARM_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import java.util.*


class AlarmTask(// Your context to retrieve the alarm manager from
    private val context: Context, // The date selected for the alarm
    private val date: Calendar
) : Runnable {
    // The android system alarm manager
    private val am: AlarmManager

    init {
        this.am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    override fun run() {
        // Request to start are service when the alarm date is upon us
        // We don't start an activity as we just want to pop up a notification into the system bar not a full activity
        val intent = Intent(context, NotifyService::class.java)
        intent.putExtra(NotifyService.INTENT_NOTIFY, true)
        val pendingIntent = PendingIntent.getService(context, 0, intent, 0)

        // Sets an alarm - note this alarm will be lost if the phone is turned off and on again
        am.set(AlarmManager.RTC, date.getTimeInMillis(), pendingIntent)
    }
}