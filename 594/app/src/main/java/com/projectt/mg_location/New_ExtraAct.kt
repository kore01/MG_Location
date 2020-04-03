package com.projectt.mg_location

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.projectt.mg_location.Adapters.Extra_ListAdapter
import com.projectt.mg_location.DataModel.ExtraCur
import com.projectt.mg_location.DataModel.SuggExtra
import com.projectt.mg_location.DataModel.TeacherModel

import java.util.*

class New_ExtraAct : NavigationView.OnNavigationItemSelectedListener, Observer, BaseActivity() {

    var extra_cur:ArrayList<ExtraCur> = ArrayList<ExtraCur>()

    lateinit var list: ListView// = findViewById(R.id.list)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new__extra)
        oncreate()

        SuggExtra
        SuggExtra.addObserver(this)

        extra_cur= SuggExtra.getDataExtra()!!
        list = findViewById(R.id.list)
        val adapter = Extra_ListAdapter(
            this,
            extra_cur
        )
        list.adapter = adapter
        //   Log.i("extra_cur" ,extra_cur.size.toString())
    }

    override fun onStart() {
        super.onStart()
        updates()
    }

    override fun update(p0: Observable?, p1: Any?) {
        teachers = TeacherModel.getData()!!
        extra_cur = SuggExtra.getDataExtra()!!

        updates()
    }
    fun updates ()
    {
        Log.i("extra_cur size", extra_cur.size.toString())

        val adapter = Extra_ListAdapter(
            this,
            extra_cur
        )
        list.adapter = adapter

        list.setOnItemClickListener { parent, view, position, id ->
            ShowAreUSure(position)

        }
    }
    private fun ShowAreUSure(pos:Int) {
        myDialog!!.setContentView(R.layout.dial_areusure3)

        var current: ExtraCur = extra_cur[pos]
        Toast.makeText(this,current.id, Toast.LENGTH_LONG).show()

        val txtclose: TextView = myDialog!!.findViewById(R.id.txtclose)
        txtclose.text = "X"
        txtclose.setOnClickListener {
            myDialog!!.dismiss()
        }
        var database2 = FirebaseDatabase.getInstance().reference
        val da: TextView = myDialog!!.findViewById(R.id.da)

        val key = database2!!.child("ExtraCur").push().key

        da.setOnClickListener {
            database2.child("ExtraCur").child(key!!).child("Name").setValue(current.name)
            database2.child("ExtraCur").child(key!!).child("Place").setValue(current.place)
            database2.child("ExtraCur").child(key!!).child("Day").setValue(current.day)
            database2.child("ExtraCur").child(key!!).child("From").setValue(current.from)
            database2.child("ExtraCur").child(key!!).child("Info").setValue(current.info)
            database2.child("ExtraCur").child(key!!).child("Teacher").setValue(current.teacher)
            database2.child("ExtraCur").child(key!!).child("To").setValue(current.to)

            database2
                .child("SuggExtra")
                .child(current.id!!)
                .setValue(null)
            myDialog!!.dismiss()
        }

        val ne: TextView = myDialog!!.findViewById(R.id.ne)

        ne.setOnClickListener {

            database2
                .child("SuggExtra")
                .child(current.id!!)
                .setValue(null)
            myDialog!!.dismiss()
        }

        myDialog!!.show()
    }

}
