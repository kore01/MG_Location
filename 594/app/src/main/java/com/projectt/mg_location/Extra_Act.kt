package com.projectt.mg_location

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.projectt.mg_location.Adapters.ExtraAdapter
import com.projectt.mg_location.DataModel.AccountModel
import com.projectt.mg_location.DataModel.ExtraCur
import com.projectt.mg_location.DataModel.ExtraCurModel
import com.projectt.mg_location.DataModel.ExtraCurModel.getDataExtra
import com.projectt.mg_location.DataModel.TeacherModel
import kotlinx.android.synthetic.main.activity_extra_.*
import java.util.*

class Extra_Act : BaseActivity(), Observer, AdapterView.OnItemSelectedListener{


    lateinit var extracur: ArrayList<ExtraCur>
    override fun update(o: Observable?, arg: Any?) {
        extracur = getDataExtra()!!
        val adapter = ExtraAdapter(
            this,
            extracur
        )
        list.adapter = adapter
        teachers = TeacherModel.getData()!!
        users = AccountModel.getDataAccounts()!!
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extra_)
        oncreate()
    }
    override fun onStart() {
        super.onStart()
        ExtraCurModel
        ExtraCurModel.addObserver(this)
        extracur = getDataExtra()!!
        val list: ListView = this.findViewById(R.id.list)
        val adapter = ExtraAdapter(
            this,
            extracur
        )
        list.adapter = adapter

        val fab: View = findViewById(R.id.fab_btn)
        if(acc.id!!.length<3) fab.visibility = View.GONE
        else fab.visibility = View.VISIBLE
        fab.setOnClickListener {

            AddExtra()
        }
    }
    lateinit var textView: TextView
    private fun AddExtra() {

        myDialog!!.setContentView(R.layout.dial_add_extra)
        textView = myDialog!!.findViewById(R.id.date)
        val txtclose: TextView = myDialog!!.findViewById(R.id.txtclose)
        txtclose.text = "X"
        txtclose.setOnClickListener {
            myDialog!!.dismiss()
        }

        val timeView: TextView = myDialog!!.findViewById(R.id.time)

        val timeView2: TextView = myDialog!!.findViewById(R.id.time2)

        val nameView: TextView = myDialog!!.findViewById(R.id.name)

        val descView: TextView = myDialog!!.findViewById(R.id.desc)

        val teachView: TextView = myDialog!!.findViewById(R.id.teachers)

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

        timeView2.setOnClickListener {

            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR)
            val minute = c.get(Calendar.MINUTE)

            val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener(function = { view, h, m ->

                if (h < 10) timeView2.setText("0")
                timeView2.setText(timeView2.text.toString() + h.toString() + ":")
                if (m < 10) timeView.setText(timeView2.text.toString() + "0")
                timeView2.setText(timeView2.text.toString() + m.toString())
                //timeView.setText(h.toString() + ":" + m.toString())
                //Toast.makeText(this, h.toString() + " : " + m + " : ", Toast.LENGTH_LONG).show()
            }), hour, minute, true)
            tpd.show()
        }

        //textView.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())

        var cal = Calendar.getInstance()


        var list_of_items = arrayOf("Item 1", "Item 2", "Item 3")

        textView.setOnClickListener {
            chooseDay()
        }


        val submit: Button = myDialog!!.findViewById(R.id.submit)

        submit.setOnClickListener {
            if (textView.text.length == 0 || timeView.text.length == 0 || timeView2.text.length == 0 || descView.text.length == 0 || placeView.text.length == 0 || nameView.text.length == 0 || teachView.text.length == 0) {
                Toast.makeText(this, "Моля, попълнете всички полета.", Toast.LENGTH_LONG).show()
            } else {
                lateinit var database2: DatabaseReference
                database2 = FirebaseDatabase.getInstance().reference
                val key = database2.child("SuggEvents").push().key
                database2.child("SuggExtra").child(key!!).child("Name").setValue(nameView.text.toString())
                database2.child("SuggExtra").child(key).child("Place").setValue(placeView.text.toString())
                database2.child("SuggExtra").child(key).child("From").setValue(timeView.text.toString())
                database2.child("SuggExtra").child(key).child("To").setValue(timeView2.text.toString())
                database2.child("SuggExtra").child(key).child("Day").setValue(textView.text.toString())
                database2.child("SuggExtra").child(key).child("Info").setValue(descView.text.toString())
                database2.child("SuggExtra").child(key).child("Teacher").setValue(teachView.text.toString())
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

    override var list_of_items = arrayOf("Понеделник", "Вторник", "Сряда", "Четвъртък", "Петък", "Събота", "Неделя")
    private fun chooseDay()
    {
        myDialog2!!.setContentView(R.layout.dial_choose_weekday)
        val txtclose: TextView = myDialog2!!.findViewById(R.id.txtclose)
        txtclose.text = "X"

        txtclose.setOnClickListener {
            myDialog2!!.dismiss()
        }

        val spinner: Spinner = myDialog2!!.findViewById(R.id.spinner)
        spinner!!.setOnItemSelectedListener(this)

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, R.layout.my_spinner, list_of_items)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner.setAdapter(aa)

        val btn:Button = myDialog2!!.findViewById(R.id.button2)
        btn.setOnClickListener {
            myDialog2!!.dismiss()
        }
        myDialog2!!.show()
    }
    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        textView1.text =  list_of_items[position]
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}