package com.projectt.mg_location

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase
import com.projectt.mg_location.Adapters.ListSuggAdapter
import com.projectt.mg_location.DataModel.*
import java.util.*

class SuggEvents : NavigationView.OnNavigationItemSelectedListener, Observer, BaseActivity() {

    var problems: ArrayList<SuggEvent> = ArrayList<SuggEvent>()
    lateinit var list: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sugg_events)
        oncreate()
        SuggEventsModel
        SuggEventsModel.addObserver(this)
        problems = SuggEventsModel.getData()!!
        list = findViewById(R.id.list)
        val adapter = ListSuggAdapter(
            this,
            problems
        )
        list.adapter = adapter
        
        
        list.setOnItemClickListener { parent, view, position, id ->
            ShowAreUSure(position)

        }
    }

    override fun onStart() {
        super.onStart()
        updates()
    }

    private fun ShowAreUSure(pos:Int) {
        myDialog!!.setContentView(R.layout.dial_areusure)

        var current:SuggEvent = problems[pos]
       // Toast.makeText(this,current.id, Toast.LENGTH_LONG).show()

        val txtclose: TextView = myDialog!!.findViewById(R.id.txtclose)
        txtclose.text = "X"
        txtclose.setOnClickListener {
            myDialog!!.dismiss()
        }
        var database2 = FirebaseDatabase.getInstance().reference
        val da: TextView = myDialog!!.findViewById(R.id.da)
        da.setOnClickListener {


            database2
                .child("SuggEvents")
                .child(current.id!!)
                .setValue(null)

            val key = database2!!.child("Events").push().key
            database2.child("Events").child(key!!).child("Name").setValue(current.name)
            database2.child("Events").child(key!!).child("Place").setValue(current.place)
            database2.child("Events").child(key!!).child("Time").setValue(current.time)
            database2.child("Events").child(key!!).child("Data").setValue(current.date)
            database2.child("Events").child(key!!).child("Info").setValue(current.desc)
            database2.child("Events").child(key!!).child("Type").setValue("1")
            database2.child("Events").child(key!!).child("Access").setValue("All")

            myDialog!!.dismiss()
        }

        val ne: TextView = myDialog!!.findViewById(R.id.ne)

        ne.setOnClickListener {

            database2
                .child("SuggEvents")
                .child(current.id!!)
                .setValue(null)
            myDialog!!.dismiss()
        }

        myDialog!!.show()
    }


    override fun update(p0: Observable?, p1: Any?) {
        teachers = TeacherModel.getData()!!
        problems = SuggEventsModel.getData()!!

        updates()
    }

    fun updates() {
        //Log.i("problems size", problems.size.toString())

        val adapter = ListSuggAdapter(
            this,
            problems
        )
        list.adapter = adapter
    }
}
