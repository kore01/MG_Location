package com.projectt.mg_location

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ExpandableListView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.projectt.mg_location.Adapters.ExpandableListAdapter
import com.projectt.mg_location.Adapters.ExpandableListAdapterForClasswork
import com.projectt.mg_location.DataModel.*

import kotlinx.android.synthetic.main.activity_class_work.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ClassWork_Act : NavigationView.OnNavigationItemSelectedListener, Observer, BaseActivity() {


    var eventdata: ArrayList<ClassWork> = ClassWorkModel.getData()!!
    //var classwork: ArrayList<ClassWork> = ClassWorkModel.getData()!!


    internal var expandableListView: ExpandableListView? = null
    internal var adapter: ExpandableListAdapterForClasswork? = null
    internal var titleList: List<String>? = null


    lateinit var data: HashMap<String, List<ClassWork>>

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

        ExtraCurModel
        ExtraCurModel.addObserver(this)
        TeacherModel
        TeacherModel.addObserver(this)
        ClassWorkModel
        ClassWorkModel.addObserver(this)

        val fab: View = findViewById(R.id.fab_btn)
        if (acc.type == "2") fab.visibility = View.VISIBLE
        else fab.visibility = View.GONE
        fab.setOnClickListener {

            AddClassWork()
        }

        updates()
    }


    private fun AddClassWork() {
        myDialog!!.setContentView(R.layout.dial_add_classwork)
        val txtclose: TextView = myDialog!!.findViewById(R.id.txtclose)
        txtclose.text = "X"
        txtclose.setOnClickListener {
            myDialog!!.dismiss()
        }

        val date: TextView = myDialog!!.findViewById(R.id.date)

        val typeView: TextView = myDialog!!.findViewById(R.id.type)

        val classView: TextView = myDialog!!.findViewById(R.id.classs)

        classView.setOnClickListener {
            ChooseClassForClassWork()
            classView.text = classtext
        }
        // val typeView: TextView = myDialog!!.findViewById(R.id.type)

        val descView: TextView = myDialog!!.findViewById(R.id.desc)

        //textView.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())


        var cal = Calendar.getInstance()


        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.UK)
            date.text = sdf.format(cal.time)
        }

        date.setOnClickListener {
            DatePickerDialog(
                this, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        val submit: Button = myDialog!!.findViewById(R.id.submit)

        submit.setOnClickListener {
            if (date.text.length == 0 || classView.text.length == 0 || typeView.text.length == 0) {
                Toast.makeText(this, "Моля, попълнете всички полета.", Toast.LENGTH_LONG).show()
            } else {
                lateinit var database2: DatabaseReference
                database2 = FirebaseDatabase.getInstance().reference
                val key = database2!!.child("ClassWork").push().key
                database2.child("ClassWork").child(key!!).child("Type").setValue(typeView.text.toString())
                database2.child("ClassWork").child(key).child("Class").setValue(classView.text.toString())
                database2.child("ClassWork").child(key).child("Date").setValue(date.text.toString())
                database2.child("ClassWork").child(key).child("Teacher")
                    .setValue(teachers[acc.myclass!!.toInt() - 1].name + " " + teachers[acc.myclass!!.toInt() - 1].familyname)
                database2.child("ClassWork").child(key).child("Subject")
                    .setValue(teachers[acc.myclass!!.toInt() - 1].subject1)
                database2.child("ClassWork").child(key).child("Info").setValue(descView.text.toString())
                myDialog!!.dismiss()
            }
        }
        myDialog!!.show()
    }

    var classtext: String = ""
    fun ChooseClassForClassWork() {
        var myDialog3: Dialog? = Dialog(this)
        myDialog3!!.setContentView(R.layout.dial_choose_class)
        val txtclose: TextView = myDialog3.findViewById(R.id.txtclose)
        txtclose.text = "X"
        txtclose.setOnClickListener {
            myDialog3.dismiss()
        }

        val b5a: Button = myDialog3.findViewById(R.id.b5а)
        b5a.setOnClickListener {

            classtext = "5а"
            myDialog3.dismiss()
        }

        val b5b: Button = myDialog3.findViewById(R.id.b5b)
        b5b.setOnClickListener {
            classtext = "5б"
            myDialog3.dismiss()
        }

        val b5v: Button = myDialog3.findViewById(R.id.b5v)
        b5v.setOnClickListener {
            classtext = "5в"
            myDialog3.dismiss()
        }

        val b6a: Button = myDialog3.findViewById(R.id.b6a)
        b6a.setOnClickListener {
            classtext = "6а"
            myDialog3.dismiss()
        }
        val b6b: Button = myDialog3.findViewById(R.id.b6b)
        b6b.setOnClickListener {
            classtext = "6б"
            myDialog3.dismiss()
        }
        val b6v: Button = myDialog3.findViewById(R.id.b6v)
        b6v.setOnClickListener {
            classtext = "6в"
            myDialog3.dismiss()
        }

        val b7a: Button = myDialog3.findViewById(R.id.b7a)
        b7a.setOnClickListener {
            classtext = "7а"
            myDialog3.dismiss()
        }
        val b7b: Button = myDialog3.findViewById(R.id.b7b)
        b7b.setOnClickListener {
            classtext = "7б"
            myDialog3.dismiss()
        }

        val b8a: Button = myDialog3.findViewById(R.id.b8a)
        b8a.setOnClickListener {
            classtext = "8а"
            myDialog3.dismiss()
        }
        val b8b: Button = myDialog3.findViewById(R.id.b8b)
        b8b.setOnClickListener {
            classtext = "8б"
            myDialog3.dismiss()
        }
        val b8v: Button = myDialog3.findViewById(R.id.b8v)
        b8v.setOnClickListener {
            classtext = "8в"
            myDialog3.dismiss()
        }
        val b8g: Button = myDialog3.findViewById(R.id.b8g)
        b8g.setOnClickListener {
            classtext = "8г"
            myDialog3.dismiss()
        }
        val b8d: Button = myDialog3.findViewById(R.id.b8d)
        b8d.setOnClickListener {
            classtext = "8д"
            myDialog3.dismiss()
        }
        val b8e: Button = myDialog3.findViewById(R.id.b8e)
        b8e.setOnClickListener {
            classtext = "8е"
            myDialog3.dismiss()
        }
        val b8j: Button = myDialog3.findViewById(R.id.b8j)
        b8j.setOnClickListener {
            classtext = "8ж"
            myDialog3.dismiss()
        }

        val b9a: Button = myDialog3.findViewById(R.id.b9a)
        b9a.setOnClickListener {
            classtext = "9а"
            myDialog3.dismiss()
        }
        val b9b: Button = myDialog3.findViewById(R.id.b9b)
        b9b.setOnClickListener {
            classtext = "9б"
            myDialog3.dismiss()
        }
        val b9v: Button = myDialog3.findViewById(R.id.b9v)
        b9v.setOnClickListener {
            classtext = "9в"
            myDialog3.dismiss()
        }
        val b9g: Button = myDialog3.findViewById(R.id.b9g)
        b9g.setOnClickListener {
            classtext = "9г"
            myDialog3.dismiss()
        }
        val b9d: Button = myDialog3.findViewById(R.id.b9d)
        b9d.setOnClickListener {
            classtext = "9д"
            myDialog3.dismiss()
        }
        val b9e: Button = myDialog3.findViewById(R.id.b9e)
        b9e.setOnClickListener {
            classtext = "8е"
            myDialog3.dismiss()
        }
        val b9j: Button = myDialog3.findViewById(R.id.b9j)
        b9j.setOnClickListener {
            classtext = "9ж"
            myDialog3.dismiss()
        }

        val b10a: Button = myDialog3.findViewById(R.id.b10a)
        b10a.setOnClickListener {
            classtext = "10а"
            myDialog3.dismiss()
        }
        val b10b: Button = myDialog3.findViewById(R.id.b10b)
        b10b.setOnClickListener {
            classtext = "10б"
            myDialog3.dismiss()
        }
        val b10v: Button = myDialog3.findViewById(R.id.b10v)
        b10v.setOnClickListener {
            classtext = "10в"
            myDialog3.dismiss()
        }
        val b10g: Button = myDialog3.findViewById(R.id.b10g)
        b10g.setOnClickListener {
            classtext = "10г"
            myDialog3.dismiss()
        }
        val b10d: Button = myDialog3.findViewById(R.id.b10d)
        b10d.setOnClickListener {
            classtext = "10д"
            myDialog3.dismiss()
        }
        val b10e: Button = myDialog3.findViewById(R.id.b10e)
        b10e.setOnClickListener {
            classtext = "10е"
            myDialog3.dismiss()
        }
        val b10j: Button = myDialog3.findViewById(R.id.b10j)
        b10j.setOnClickListener {
            classtext = "10ж"
            myDialog3.dismiss()
        }
        val b10z: Button = myDialog3.findViewById(R.id.b10z)
        b10z.setOnClickListener {
            classtext = "10з"
            myDialog3.dismiss()
        }

        val b11a: Button = myDialog3.findViewById(R.id.b11a)
        b11a.setOnClickListener {
            classtext = "11а"
            myDialog3.dismiss()
        }
        val b11b: Button = myDialog3.findViewById(R.id.b11b)
        b11b.setOnClickListener {
            classtext = "11б"
            myDialog3.dismiss()
        }
        val b11v: Button = myDialog3.findViewById(R.id.b11v)
        b11v.setOnClickListener {
            classtext = "11в"
            myDialog3.dismiss()
        }
        val b11g: Button = myDialog3.findViewById(R.id.b11g)
        b11g.setOnClickListener {
            classtext = "11г"
            myDialog3.dismiss()
        }
        val b11d: Button = myDialog3.findViewById(R.id.b11d)
        b11d.setOnClickListener {
            classtext = "11д"
            myDialog3.dismiss()
        }
        val b11e: Button = myDialog3.findViewById(R.id.b11e)
        b11e.setOnClickListener {
            classtext = "11е"
            myDialog3.dismiss()
        }
        val b11j: Button = myDialog3.findViewById(R.id.b11j)
        b11j.setOnClickListener {
            classtext = "11ж"
            myDialog3.dismiss()
        }
        val b11z: Button = myDialog3.findViewById(R.id.b11z)
        b11z.setOnClickListener {
            classtext = "11з"
            myDialog3.dismiss()
        }

        val b12a: Button = myDialog3.findViewById(R.id.b12a)
        b12a.setOnClickListener {
            classtext = "12а"
            myDialog3.dismiss()
        }
        val b12b: Button = myDialog3.findViewById(R.id.b12b)
        b12b.setOnClickListener {
            classtext = "12б"
            myDialog3.dismiss()
        }
        val b12v: Button = myDialog3.findViewById(R.id.b12v)
        b12v.setOnClickListener {
            classtext = "12в"
            myDialog3.dismiss()
        }
        val b12g: Button = myDialog3.findViewById(R.id.b12g)
        b12g.setOnClickListener {
            classtext = "12г"
            myDialog3.dismiss()
        }
        val b12d: Button = myDialog3.findViewById(R.id.b12d)
        b12d.setOnClickListener {
            classtext = "12д"
            myDialog3.dismiss()
        }
        val b12e: Button = myDialog3.findViewById(R.id.b12e)
        b12e.setOnClickListener {
            classtext = "12е"
            myDialog3.dismiss()
        }
        val b12j: Button = myDialog3.findViewById(R.id.b12j)
        b12j.setOnClickListener {
            classtext = "12ж"
            myDialog3.dismiss()
        }
        val b12z: Button = myDialog3.findViewById(R.id.b12z)
        b12z.setOnClickListener {
            classtext = "12з"
            myDialog3.dismiss()
        }

        myDialog3.show()
    }


    override fun onResume() {
        super.onResume()
        updates()
    }


    override fun update(p0: Observable?, p1: Any?) {

        classes = ClassesModel.getData()!!
        rooms = RoomModel.getData()!!
        eventdata = ClassWorkModel.getData()!!
        teachers = TeacherModel.getData()!!
        users = AccountModel.getDataAccounts()!!


        updates()
    }

    fun updates() {
        val listData = HashMap<String, List<ClassWork>>()

        val arraylist = ArrayList<ArrayList<ClassWork>>()
        var p = ArrayList<ClassWork>()
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

            Log.i("acc.type", acc.type)
            if (acc.type == "2" && a.teacher != teachers[acc.myclass!!.toInt() - 1].name + " " + teachers[acc.myclass!!.toInt() - 1].familyname) continue
            if (a.classs != acc.myclass) continue;
            Log.i("eventdata5", acc.myclass + " " + a.classs)
            if (a.data1 == s) {
                p.add(a)
            } else {
                arraylist.add(p)
                listData.put(s, arraylist[br])
                br++
                s = a.data1!!
                p = ArrayList<ClassWork>()
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
            adapter = ExpandableListAdapterForClasswork(this, titleList as ArrayList<String>, listData)
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