package com.projectt.mg_location

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.projectt.mg_location.Adapters.ExpandableListAdapter
import com.projectt.mg_location.DataModel.*
import com.projectt.mg_location.DataModel.EventModel.getDataEvents
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : NavigationView.OnNavigationItemSelectedListener, Observer, BaseActivity() {


    var eventdata: ArrayList<Event> = getDataEvents()!!


    internal var expandableListView: ExpandableListView? = null
    internal var adapter: ExpandableListAdapter? = null
    internal var titleList: List<String>? = null

    lateinit var data: HashMap<String, List<Event>>

    private var drawer: DrawerLayout? = null
    //UI elements


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: android.support.v7.widget.Toolbar = this.findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout)
        val actionbar: android.support.v7.app.ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        oncreate()
    }


    override fun onStart() {
        super.onStart()

        FirebaseApp.initializeApp(this)

        EventModel
        EventModel.addObserver(this)
        ExtraCurModel
        ExtraCurModel.addObserver(this)
        TeacherModel
        TeacherModel.addObserver(this)

        myLogDialog = Dialog(this)

        val fab: View = findViewById(R.id.fab_btn)
        if(acc.id!!.length<3) fab.visibility = View.GONE
        else fab.visibility = View.VISIBLE
        fab.setOnClickListener {

            AddEvent()
        }

        val alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val broadcastIntent = Intent(this, AlarmBroadcastReceiver::class.java)
        broadcastIntent.putExtra("class", acc.myclass)

        var pIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, 0)
        alarmMgr.cancel(pIntent);
        pIntent = PendingIntent.getBroadcast(this, 1, broadcastIntent, 0)
        alarmMgr.cancel(pIntent);
        pIntent = PendingIntent.getBroadcast(this, 2, broadcastIntent, 0)
        alarmMgr.cancel(pIntent);
        pIntent = PendingIntent.getBroadcast(this, 3, broadcastIntent, 0)
        alarmMgr.cancel(pIntent);
        pIntent = PendingIntent.getBroadcast(this, 4, broadcastIntent, 0)
        alarmMgr.cancel(pIntent);
        pIntent = PendingIntent.getBroadcast(this, 5, broadcastIntent, 0)
        alarmMgr.cancel(pIntent);
        pIntent = PendingIntent.getBroadcast(this, 6, broadcastIntent, 0)
        alarmMgr.cancel(pIntent);
        pIntent = PendingIntent.getBroadcast(this, 7, broadcastIntent, 0)
        alarmMgr.cancel(pIntent);
        pIntent = PendingIntent.getBroadcast(this, 8, broadcastIntent, 0)
        alarmMgr.cancel(pIntent);


        if (prefs!!.notifications == true) {
            if (acc.myclass == "5а" || acc.myclass == "5б" || acc.myclass == "5в" || acc.myclass == "12а" || acc.myclass == "12б" || acc.myclass == "12в" || acc.myclass == "12г" || acc.myclass == "12д" || acc.myclass == "12е" || acc.myclass == "12ж" || acc.myclass == "12з") {
                setAlarmsfs()
            } else setAlarmsss()
        }



        updates()

        prefs!!.startdial = false
        if(prefs!!.startdial == false )
        {
            prefs!!.startdial = true
            Log.i("email acc", acc.id + "text")
            if(acc.id!!.length<2) startStart()
        }
    }

    private fun setAlarmsfs() {


        val alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Intent to start the Broadcast Receiver
        val broadcastIntent = Intent(this, AlarmBroadcastReceiver::class.java)
        broadcastIntent.putExtra("class", acc.myclass)
        broadcastIntent.putExtra("sub", 1)
        // The Pending Intent to pass in AlarmManager
        var pIntent: PendingIntent


        // Set the alarm to start at 8:30 a.m.
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 7)
            set(Calendar.MINUTE, 25)
        }

        pIntent = PendingIntent.getBroadcast(this, 1, broadcastIntent, 0)


        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            1000 * 60 * 60 * 24,
            pIntent
        )

        broadcastIntent.putExtra("sub", 2)
        pIntent = PendingIntent.getBroadcast(this, 2, broadcastIntent, 0)
        // Set the alarm to start at 8:30 a.m.
        val calendar1: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 5)
        }

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar1.timeInMillis,
            1000 * 60 * 60 * 24,
            pIntent
        )

        broadcastIntent.putExtra("sub", 3)
        pIntent = PendingIntent.getBroadcast(this, 3, broadcastIntent, 0)
        // Set the alarm to start at 8:30 a.m.
        val calendar2: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 55)
        }

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar2.timeInMillis,
            1000 * 60 * 60 * 24,
            pIntent
        )
        broadcastIntent.putExtra("sub", 4)
        pIntent = PendingIntent.getBroadcast(this, 4, broadcastIntent, 0)
        // Set the alarm to start at 8:30 a.m.
        val calendar3: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 45)
        }

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar3.timeInMillis,
            1000 * 60 * 60 * 24,
            pIntent
        )
        broadcastIntent.putExtra("sub", 5)

        pIntent = PendingIntent.getBroadcast(this, 5, broadcastIntent, 0)
        // Set the alarm to start at 8:30 a.m.
        val calendar4: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 10)
            set(Calendar.MINUTE, 45)
        }

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar4.timeInMillis,
            1000 * 60 * 60 * 24,
            pIntent
        )

        broadcastIntent.putExtra("sub", 6)

        pIntent = PendingIntent.getBroadcast(this, 6, broadcastIntent, 0)
        // Set the alarm to start at 8:30 a.m.
        val calendar5: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 11)
            set(Calendar.MINUTE, 35)
        }

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar5.timeInMillis,
            1000 * 60 * 60 * 24,
            pIntent
        )

        broadcastIntent.putExtra("sub", 7)

        pIntent = PendingIntent.getBroadcast(this, 7, broadcastIntent, 0)
        // Set the alarm to start at 8:30 a.m.
        val calendar6: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 12)
            set(Calendar.MINUTE, 25)
        }

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.


        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar6.timeInMillis,
            1000 * 60 * 60 * 24,
            pIntent
        )
        broadcastIntent.putExtra("sub", 8)
        pIntent = PendingIntent.getBroadcast(this, 8, broadcastIntent, 0)
        // Set the alarm to start at 8:30 a.m.
        val calendar7: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 13)
            set(Calendar.MINUTE, 10)
        }

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar7.timeInMillis,
            1000 * 60 * 60 * 24,
            pIntent
        )
    }

    private fun setAlarmsss() {


        val alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Intent to start the Broadcast Receiver
        val broadcastIntent = Intent(this, AlarmBroadcastReceiver::class.java)

        // The Pending Intent to pass in AlarmManager
        var pIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, 0)


        // Set the alarm to start at 8:30 a.m.
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 13)
            set(Calendar.MINUTE, 25)
        }

        pIntent = PendingIntent.getBroadcast(this, 1, broadcastIntent, 0)


        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            1000 * 60 * 60 * 24,
            pIntent
        )

        pIntent = PendingIntent.getBroadcast(this, 2, broadcastIntent, 0)
        // Set the alarm to start at 8:30 a.m.
        val calendar1: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 14)
            set(Calendar.MINUTE, 5)
        }

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar1.timeInMillis,
            1000 * 60 * 60 * 24,
            pIntent
        )

        pIntent = PendingIntent.getBroadcast(this, 3, broadcastIntent, 0)
        // Set the alarm to start at 8:30 a.m.
        val calendar2: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 14)
            set(Calendar.MINUTE, 55)
        }

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar2.timeInMillis,
            1000 * 60 * 60 * 24,
            pIntent
        )
        pIntent = PendingIntent.getBroadcast(this, 4, broadcastIntent, 0)
        // Set the alarm to start at 8:30 a.m.
        val calendar3: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 15)
            set(Calendar.MINUTE, 45)
        }

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar3.timeInMillis,
            1000 * 60 * 60 * 24,
            pIntent
        )

        pIntent = PendingIntent.getBroadcast(this, 5, broadcastIntent, 0)
        // Set the alarm to start at 8:30 a.m.
        val calendar4: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 16)
            set(Calendar.MINUTE, 45)
        }

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar4.timeInMillis,
            1000 * 60 * 60 * 24,
            pIntent
        )

        pIntent = PendingIntent.getBroadcast(this, 6, broadcastIntent, 0)
        // Set the alarm to start at 8:30 a.m.
        val calendar5: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 17)
            set(Calendar.MINUTE, 35)
        }

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar5.timeInMillis,
            1000 * 60 * 60 * 24,
            pIntent
        )

        pIntent = PendingIntent.getBroadcast(this, 7, broadcastIntent, 0)
        // Set the alarm to start at 8:30 a.m.
        val calendar6: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 18)
            set(Calendar.MINUTE, 25)
        }

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.


        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar6.timeInMillis,
            1000 * 60 * 60 * 24,
            pIntent
        )
        pIntent = PendingIntent.getBroadcast(this, 8, broadcastIntent, 0)
        // Set the alarm to start at 8:30 a.m.
        val calendar7: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 19)
            set(Calendar.MINUTE, 10)
        }

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar7.timeInMillis,
            1000 * 60 * 60 * 24,
            pIntent
        )
    }

    private fun AddEvent() {
        myDialog!!.setContentView(R.layout.dial_add_event)
        val txtclose: TextView = myDialog!!.findViewById(R.id.txtclose)
        txtclose.text = "X"
        txtclose.setOnClickListener {
            myDialog!!.dismiss()
        }

        val textView: TextView = myDialog!!.findViewById(R.id.date)

        val timeView: TextView = myDialog!!.findViewById(R.id.time)

        val nameView: TextView = myDialog!!.findViewById(R.id.name)

        val descView: TextView = myDialog!!.findViewById(R.id.desc)

        val placeView: TextView = myDialog!!.findViewById(R.id.place)
        timeView.setOnClickListener {

            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR)
            val minute = c.get(Calendar.MINUTE)

            val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener(function = { view, h, m ->

                if (h < 10) timeView.setText("0")
                timeView.setText(timeView.text.toString() + h.toString() + ":")
                if (m < 10) timeView.setText(timeView.text.toString() + "0")
                timeView.setText(timeView.text.toString() + m.toString())
                //timeView.setText(h.toString() + ":" + m.toString())
                //Toast.makeText(this, h.toString() + " : " + m + " : ", Toast.LENGTH_LONG).show()
            }), hour, minute, true)
            tpd.show()
        }

        //textView.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())

        var cal = Calendar.getInstance()


        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.UK)
            textView.text = sdf.format(cal.time)
        }

        textView.setOnClickListener {
            DatePickerDialog(
                this@MainActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        val submit: Button = myDialog!!.findViewById(R.id.submit)

        submit.setOnClickListener {
            if (textView.text.length == 0 || timeView.text.length == 0 || descView.text.length == 0 || placeView.text.length == 0 || nameView.text.length == 0) {
                Toast.makeText(this, "Моля, попълнете всички полета.", Toast.LENGTH_LONG).show()
            } else {
                lateinit var database2: DatabaseReference
                database2 = FirebaseDatabase.getInstance().reference
                val key = database2!!.child("SuggEvents").push().key
                database2.child("SuggEvents").child(key!!).child("name").setValue(nameView.text.toString())
                database2.child("SuggEvents").child(key!!).child("place").setValue(placeView.text.toString())
                database2.child("SuggEvents").child(key!!).child("time").setValue(timeView.text.toString())
                database2.child("SuggEvents").child(key!!).child("date").setValue(textView.text.toString())
                database2.child("SuggEvents").child(key!!).child("desc").setValue(descView.text.toString())
                myDialog!!.dismiss()
                Toast.makeText(
                    this,
                    "Скоро събитието Ви ще бъде прегледано от администраторите.",
                    Toast.LENGTH_LONG
                )
                    .show()
            }


        }




        myDialog!!.show()
    }


    override fun onResume() {
        super.onResume()
        updates()
    }


    override fun update(p0: Observable?, p1: Any?) {

        classes = ClassesModel.getData()!!
        rooms = RoomModel.getData()!!
        eventdata = EventModel.getDataEvents()!!
        teachers = TeacherModel.getData()!!
        users = AccountModel.getDataAccounts()!!
        updates()
    }

    fun updates() {
        val listData = HashMap<String, List<Event>>()

        val arraylist = ArrayList<ArrayList<Event>>()
        var p = ArrayList<Event>()
        var s: String = ""
        arraylist.clear()
        var br = 0

        (eventdata).sortWith(
            compareBy(
                { it.data1!![8] },
                { it.data1!![9] },
                { it.data1!![3] },
                { it.data1!![4] },
                { it.data1!![0] },
                { it.data1!![1] })
        )
        //eventdata.reverse()
        if (eventdata.size > 0) s = eventdata[0].data1!!
        for (a in eventdata) {
            Log.i("eventdata2", a.data1)
            if (a.data1 == s) {
                p.add(a)
            } else {
                arraylist.add(p)
                listData.put(s, arraylist[br])
                br++
                s = a.data1!!
                p = ArrayList<Event>()
                p.add(a)
            }
        }

        arraylist.add(p)

        listData.put(s, arraylist[br])
        br++
        data = listData
        expandableListView = findViewById(R.id.expandableListView)
        if (expandableListView != null) {

            titleList = ArrayList(listData.keys)
            (titleList as ArrayList<String>).sortWith(
                compareBy(
                    { it[8] },
                    { it[9] },
                    { it[3] },
                    { it[4] },
                    { it[0] },
                    { it[1] })
            )
            adapter = ExpandableListAdapter(this, titleList as ArrayList<String>, listData)
            expandableListView!!.setAdapter(adapter)
            for (i in 0 until titleList!!.size)
                expandableListView!!.expandGroup(i)

            expandableListView!!.setOnGroupCollapseListener { groupPosition ->
                /*  Toast.makeText(
                      applicationContext,
                      (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.",
                      Toast.LENGTH_SHORT
                  ).show()*/
            }

            expandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
                /*Toast.makeText(
                    applicationContext,
                    "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(titleList as ArrayList<String>)[groupPosition]]!!.get(
                        childPosition
                    ),
                    Toast.LENGTH_SHORT
                ).show()*/
                false
            }
        }
        expandableListView!!.setOnGroupExpandListener { groupPosition ->
            /*Toast.makeText(
                applicationContext,
                (titleList as ArrayList<String>)[groupPosition] + " List Expanded.",
                Toast.LENGTH_SHORT
            ).show()*/
        }
        expandableListView!!.setOnGroupCollapseListener { groupPosition ->
            /*Toast.makeText(
                applicationContext,
                (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.",
                Toast.LENGTH_SHORT
            ).show()*/
        }


    }

}