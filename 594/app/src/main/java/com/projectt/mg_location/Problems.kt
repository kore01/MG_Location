package com.projectt.mg_location

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.projectt.mg_location.Adapters.ListAdapterClass
import com.projectt.mg_location.DataModel.Problem
import com.projectt.mg_location.DataModel.ProblemModel
import com.projectt.mg_location.DataModel.ProblemModel.getData
import com.projectt.mg_location.DataModel.TeacherModel
import java.util.*

class Problems : NavigationView.OnNavigationItemSelectedListener, Observer, BaseActivity() {

    var problems:ArrayList<Problem> = ArrayList<Problem>()

    lateinit var list: ListView// = findViewById(R.id.list)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problems)
        oncreate()

        ProblemModel
        ProblemModel.addObserver(this)
        problems=getData()!!
        list = findViewById(R.id.list)
        val adapter = ListAdapterClass(
            this,
            problems
        )
        list.adapter = adapter
     //   Log.i("problems" ,problems.size.toString())
    }

    override fun onStart() {
        super.onStart()
        updates()
    }

    override fun update(p0: Observable?, p1: Any?) {
        teachers = TeacherModel.getData()!!
        problems = getData()!!

        updates()
    }
    fun updates ()
    {
        Log.i("problems size", problems.size.toString())

        val adapter = ListAdapterClass(
            this,
            problems
        )
        list.adapter = adapter

        list.setOnItemClickListener { parent, view, position, id ->
            ShowAreUSure(position)

        }
    }
    private fun ShowAreUSure(pos:Int) {
        myDialog!!.setContentView(R.layout.dial_areusure2)

        var current: Problem = problems[pos]
        Toast.makeText(this,current.id, Toast.LENGTH_LONG).show()

        val txtclose: TextView = myDialog!!.findViewById(R.id.txtclose)
        txtclose.text = "X"
        txtclose.setOnClickListener {
            myDialog!!.dismiss()
        }
        var database2 = FirebaseDatabase.getInstance().reference
        val da: TextView = myDialog!!.findViewById(R.id.da)
        da.setOnClickListener {
            database2
                .child("Problems")
                .child(current.id!!)
                .setValue(null)
            myDialog!!.dismiss()
        }

        val ne: TextView = myDialog!!.findViewById(R.id.ne)

        ne.setOnClickListener {
            myDialog!!.dismiss()
        }

        myDialog!!.show()
    }

}
