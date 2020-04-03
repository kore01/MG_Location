package com.projectt.mg_location

import android.app.Service
import android.os.IBinder
import android.content.Intent
import android.os.Binder
import android.util.Log
import java.util.*


class ScheduleService : Service() {

    // This is the object that receives interactions from clients. See
    private val mBinder = ServiceBinder()

    /**
     * Class for clients to access
     */
    inner class ServiceBinder : Binder() {
        internal val service: ScheduleService
            get() = this@ScheduleService
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.i("ScheduleService", "Received start id $startId: $intent")

        // We want this service to continue running until it is explicitly stopped, so return sticky.
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    /**
     * Show an alarm for a certain date when the alarm is called it will pop up a notification
     */
    fun setAlarm(c: Calendar) {
        // This starts a new thread to set the alarm
        // You want to push off your tasks onto a new thread to free up the UI to carry on responding
        AlarmTask(this, c).run()
    }
}