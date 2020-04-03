package com.projectt.mg_location

import android.content.Intent
import android.app.PendingIntent
import android.R
import android.app.Notification
import android.os.IBinder
import android.app.Service.START_NOT_STICKY
import android.content.Context.NOTIFICATION_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.app.NotificationManager
import android.app.Service
import android.os.Binder
import android.util.Log


open class NotifyService : Service() {
    // The system notification manager
    private var mNM: NotificationManager? = null

    // This is the object that receives interactions from clients
    private val mBinder = ServiceBinder()

    /**
     * Class for clients to access
     */
    inner class ServiceBinder : Binder() {
        internal val service: NotifyService
            get() = this@NotifyService
    }

    override fun onCreate() {
        Log.i("NotifyService", "onCreate()")
        mNM = getSystemService(NOTIFICATION_SERVICE) as NotificationManager?
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.i("LocalService", "Received start id $startId: $intent")

        // If this service was started by out AlarmTask intent then we want to show our notification
        if (intent.getBooleanExtra(INTENT_NOTIFY, false))
            showNotification()

        // We don't care if this service is stopped as we have already delivered our notification
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    /**
     * Creates a notification and shows it in the OS drag-down status bar
     */
    private fun showNotification() {
        // This is the 'title' of the notification
        val title = "Alarm!!"
        // This is the icon to use on the notification
        val icon = R.drawable.ic_dialog_alert
        // This is the scrolling text of the notification
        val text = "Your notification time is upon us."
        // What time to show on the notification
        val time = System.currentTimeMillis()

        var notification = Notification(icon, text, time)

        // The PendingIntent to launch our activity if the user selects this notification
        val contentIntent = PendingIntent.getActivity(this, 0, Intent(this, MainActivity::class.java), 0)

        // Set the info for the views that show in the notification panel.
        val builder = Notification.Builder(this)
            .setContentIntent(contentIntent)
            .setSmallIcon(R.drawable.ic_menu_help)
            .setContentTitle(title)
        notification = builder.build();

        // Clear the notification when it is pressed

        // Send the notification to the system.
        mNM!!.notify(NOTIFICATION, notification)

        // Stop the service when we are finished
        stopSelf()
    }

    companion object {

        // Unique id to identify the notification.
        val NOTIFICATION = 123
        // Name of an intent extra we can use to identify if this service was started to create a notification
        val INTENT_NOTIFY = "com.blundell.tut.service.INTENT_NOTIFY"
    }
}