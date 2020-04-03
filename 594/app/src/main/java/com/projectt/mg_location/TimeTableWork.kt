package com.projectt.mg_location

import android.app.TimePickerDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.projectt.mg_location.Adapters.ExtraAdapter
import com.projectt.mg_location.DataModel.AccountModel
import com.projectt.mg_location.DataModel.ExtraCur
import com.projectt.mg_location.DataModel.ExtraCurModel
import com.projectt.mg_location.DataModel.TeacherModel
import kotlinx.android.synthetic.main.activity_extra_.*

import kotlinx.android.synthetic.main.activity_time_table_work.*
import java.util.*

class TimeTableWork : BaseActivity(), Observer, AdapterView.OnItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_table_work)
        oncreate()
    }
    override fun onStart() {
        super.onStart()
        oncreate()
    }

}