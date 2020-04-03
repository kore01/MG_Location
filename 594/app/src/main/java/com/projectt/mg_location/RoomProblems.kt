package com.projectt.mg_location


import android.os.Bundle
import android.support.design.widget.NavigationView
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.projectt.mg_location.Adapters.ProblemListAdapter
import com.projectt.mg_location.DataModel.RoomProblem
import com.projectt.mg_location.DataModel.RoomProblemModel
import com.projectt.mg_location.DataModel.TeacherModel
import java.util.*

class RoomProblems : NavigationView.OnNavigationItemSelectedListener, Observer, BaseActivity() {

    var problems: ArrayList<RoomProblem> = ArrayList<RoomProblem>()

    lateinit var list: ListView// = findViewById(R.id.list)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_problem)
        oncreate()

        RoomProblemModel
        RoomProblemModel.addObserver(this)
        problems= RoomProblemModel.getData()!!
        list = findViewById(R.id.list)
        val adapter = ProblemListAdapter(
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
        problems = RoomProblemModel.getData()!!

        updates()
    }
    fun updates ()
    {
        Log.i("problems size", problems.size.toString())

        val adapter = ProblemListAdapter(
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

        var current: RoomProblem = problems[pos]
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
                .child("RoomProblems")
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
