package com.projectt.mg_location

import android.app.Dialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TextInputEditText
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.projectt.mg_location.Adapters.ListAdapter
import com.projectt.mg_location.DataModel.*
import com.projectt.mg_location.DataModel.TeacherModel.getData
import java.util.*

class ClassSchedule : BaseActivity(), Observer, NavigationView.OnNavigationItemSelectedListener {

   // var classes: ArrayList<Classes> = ArrayList()

    var arra: ArrayList<Array<TextView>>? = null

    var pon1: TextView? = null
    var pon2: TextView? = null
    var pon3: TextView? = null
    var pon4: TextView? = null
    var pon5: TextView? = null
    var pon6: TextView? = null
    var pon7: TextView? = null

    var vt1: TextView? = null
    var vt2: TextView? = null
    var vt3: TextView? = null
    var vt4: TextView? = null
    var vt5: TextView? = null
    var vt6: TextView? = null
    var vt7: TextView? = null

    var sr1: TextView? = null
    var sr2: TextView? = null
    var sr3: TextView? = null
    var sr4: TextView? = null
    var sr5: TextView? = null
    var sr6: TextView? = null
    var sr7: TextView? = null

    var cht1: TextView? = null
    var cht2: TextView? = null
    var cht3: TextView? = null
    var cht4: TextView? = null
    var cht5: TextView? = null
    var cht6: TextView? = null
    var cht7: TextView? = null

    var pt1: TextView? = null
    var pt2: TextView? = null
    var pt3: TextView? = null
    var pt4: TextView? = null
    var pt5: TextView? = null
    var pt6: TextView? = null
    var pt7: TextView? = null

    var RoomName: String? = null

    var classs = ClassModel.getData()!!
    var data = RoomModel.getData()!!

    var time1: Int = 0
    var time2: Int = 0
    var str: String = ""
    var c1: Int = 0
    var c2: Int = 0
    var c3: Int = 0
    var c4: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_schedule)
        //start the base activity create class
        oncreate()



        pon1 = findViewById(R.id.pon1)
        pon2 = findViewById(R.id.pon2)
        pon3 = findViewById(R.id.pon3)
        pon4 = findViewById(R.id.pon4)
        pon5 = findViewById(R.id.pon5)
        pon6 = findViewById(R.id.pon6)
        pon7 = findViewById(R.id.pon7)

        vt1 = findViewById(R.id.vt1)
        vt2 = findViewById(R.id.vt2)
        vt3 = findViewById(R.id.vt3)
        vt4 = findViewById(R.id.vt4)
        vt5 = findViewById(R.id.vt5)
        vt6 = findViewById(R.id.vt6)
        vt7 = findViewById(R.id.vt7)

        sr1 = findViewById(R.id.sr1)
        sr2 = findViewById(R.id.sr2)
        sr3 = findViewById(R.id.sr3)
        sr4 = findViewById(R.id.sr4)
        sr5 = findViewById(R.id.sr5)
        sr6 = findViewById(R.id.sr6)
        sr7 = findViewById(R.id.sr7)

        cht1 = findViewById(R.id.cht1)
        cht2 = findViewById(R.id.cht2)
        cht3 = findViewById(R.id.cht3)
        cht4 = findViewById(R.id.cht4)
        cht5 = findViewById(R.id.cht5)
        cht6 = findViewById(R.id.cht6)
        cht7 = findViewById(R.id.cht7)

        pt1 = findViewById(R.id.pt1)
        pt2 = findViewById(R.id.pt2)
        pt3 = findViewById(R.id.pt3)
        pt4 = findViewById(R.id.pt4)
        pt5 = findViewById(R.id.pt5)
        pt6 = findViewById(R.id.pt6)
        pt7 = findViewById(R.id.pt7)

        //data teachers
        TeacherModel
        TeacherModel.addObserver(this)
        RoomModel
        RoomModel.addObserver(this)
        ClassModel
        ClassModel.addObserver(this)
        ExtraCurModel
        ExtraCurModel.addObserver(this)
        ClassesModel
        ClassesModel.addObserver(this)
    }

    override fun onResume() {
        super.onResume()
        val b = intent.extras
        RoomName = b.getString("pos")
        nullpol()
        makeclasses()
    }

    override fun onStart() {
        super.onStart()

        val b = intent.extras
        RoomName = b.getString("pos")
        val namet: TextView = findViewById(R.id.namet)
        namet.text = "Клас: " + RoomName
        classes = ClassesModel.getDataForClass(RoomName!!) as ArrayList<Classes>
        nullpol()
        makeclasses()
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

    override fun update(p0: Observable?, p1: Any?) {
        classes = ClassesModel.getDataForClass(RoomName!!) as ArrayList<Classes>
        teachers = TeacherModel.getData()!!
        classs = ClassModel.getData()!!
        data = RoomModel.getData()!!
        nullpol()
        makeclasses()
    }

    fun nullpol() {
        //nullirane
        pon1?.text = ""
        pon2?.text = ""
        pon3?.text = ""
        pon4?.text = ""
        pon5?.text = ""
        pon6?.text = ""
        pon7?.text = ""

        vt1?.text = ""
        vt2?.text = ""
        vt3?.text = ""
        vt4?.text = ""
        vt5?.text = ""
        vt6?.text = ""
        vt7?.text = ""

        sr1?.text = ""
        sr2?.text = ""
        sr3?.text = ""
        sr4?.text = ""
        sr5?.text = ""
        sr6?.text = ""
        sr7?.text = ""

        cht1?.text = ""
        cht2?.text = ""
        cht3?.text = ""
        cht4?.text = ""
        cht5?.text = ""
        cht6?.text = ""
        cht7?.text = ""

        pt1?.text = ""
        pt2?.text = ""
        pt3?.text = ""
        pt4?.text = ""
        pt5?.text = ""
        pt6?.text = ""
        pt7?.text = ""

        pon1?.setBackgroundResource(R.drawable.border)
        pon2?.setBackgroundResource(R.drawable.border)
        pon3?.setBackgroundResource(R.drawable.border)
        pon4?.setBackgroundResource(R.drawable.border)
        pon5?.setBackgroundResource(R.drawable.border)
        pon6?.setBackgroundResource(R.drawable.border)
        pon7?.setBackgroundResource(R.drawable.border)

        vt1?.setBackgroundResource(R.drawable.border)
        vt2?.setBackgroundResource(R.drawable.border)
        vt3?.setBackgroundResource(R.drawable.border)
        vt4?.setBackgroundResource(R.drawable.border)
        vt5?.setBackgroundResource(R.drawable.border)
        vt6?.setBackgroundResource(R.drawable.border)
        vt7?.setBackgroundResource(R.drawable.border)

        sr1?.setBackgroundResource(R.drawable.border)
        sr2?.setBackgroundResource(R.drawable.border)
        sr3?.setBackgroundResource(R.drawable.border)
        sr4?.setBackgroundResource(R.drawable.border)
        sr5?.setBackgroundResource(R.drawable.border)
        sr6?.setBackgroundResource(R.drawable.border)
        sr7?.setBackgroundResource(R.drawable.border)

        cht1?.setBackgroundResource(R.drawable.border)
        cht2?.setBackgroundResource(R.drawable.border)
        cht3?.setBackgroundResource(R.drawable.border)
        cht4?.setBackgroundResource(R.drawable.border)
        cht5?.setBackgroundResource(R.drawable.border)
        cht6?.setBackgroundResource(R.drawable.border)
        cht7?.setBackgroundResource(R.drawable.border)

        pt1?.setBackgroundResource(R.drawable.border)
        pt2?.setBackgroundResource(R.drawable.border)
        pt3?.setBackgroundResource(R.drawable.border)
        pt4?.setBackgroundResource(R.drawable.border)
        pt5?.setBackgroundResource(R.drawable.border)
        pt6?.setBackgroundResource(R.drawable.border)
        pt7?.setBackgroundResource(R.drawable.border)
    }


    fun strings(st: String, t: Classes): String {
        var res: String? = ""
        if (st.length == 0) {
            res =
                t.subject + "\n" +
                        teachers[t.teachernumber!!.toInt() - 1].name + " " + teachers[t.teachernumber!!.toInt() - 1].familyname + "\n" + t.room
            return res
        }
        var br: Boolean = false
        for (a in st) {
            if (a != '\n')
                res += a
            else {
                if (br == false) {
                    res =
                        res + ", " + t.subject + "\n"
                    br = true
                } else if (br == true) {
                    res =
                        res + "," + teachers[t.teachernumber!!.toInt() - 1].name + " " + teachers[t.teachernumber!!.toInt() - 1].familyname + "\n"

                }
            }
        }
        res = res + ", " + t.room
        Log.i("res", res)
        return res
    }

    fun makeclasses() {

        //fill the schedule
        str = ""
        teachers = getData()!!
        val b = intent.extras
        RoomName = b.getString("pos")
        for (a in classes) {
            if (a.classs == RoomName) {
                if (a.from!!.length == 0) continue
                if (a.too!!.length == 0) continue
                c1 = digitt(a.from!![0])
                c2 = digitt(a.from!![1])
                c3 = digitt(a.from!![3])
                c4 = digitt(a.from!![4])
                time1 = (c1 * 10 + c2) * 60 + c3 * 10 + c4 - 6 * 60

                c1 = digitt(a.too!![0])
                c2 = digitt(a.too!![1])
                c3 = digitt(a.too!![3])
                c4 = digitt(a.too!![4])
                time2 = (c1 * 10 + c2) * 60 + c3 * 10 + c4 - 6 * 60

                str = a.subject + "\n" +
                        teachers[a.teachernumber!!.toInt() - 1].name + " " + teachers[a.teachernumber!!.toInt() - 1].familyname + "\n" + a.room

                if (a.day == "Monday") {
                    if (time1 <= 450 && time2 > 470) {
                        pon1!!.text = strings(pon1!!.text.toString(), a)
                    }
                    if (time1 < 520 && time2 > 520) {
                        pon2!!.text = strings(pon2!!.text.toString(), a)
                    }
                    if (time1 < 570 && time2 > 570) {
                        pon3!!.text = strings(pon3!!.text.toString(), a)
                    }
                    if (time1 < 630 && time2 > 630) {
                        pon4!!.text = strings(pon4!!.text.toString(), a)
                    }
                    if (time1 < 680 && time2 > 680) {
                        pon5!!.text = strings(pon5!!.text.toString(), a)
                    }
                    if (time1 < 730 && time2 > 730) {
                        pon6!!.text = strings(pon6!!.text.toString(), a)
                    }
                    if (time1 < 780 && time2 > 780) {
                        pon7!!.text = strings(pon7!!.text.toString(), a)
                    }

                }

                if (a.day == "Tuesday") {
                    if (time1 <= 450 && time2 > 470) {
                        Log.i("Why?", "Whythehell")
                        vt1!!.text = strings(vt1!!.text.toString(), a)
                    }
                    if (time1 < 520 && time2 > 520) {
                        vt2!!.text = strings(vt2!!.text.toString(), a)
                    }
                    if (time1 < 570 && time2 > 570) {
                        vt3!!.text = strings(vt3!!.text.toString(), a)
                    }
                    if (time1 < 630 && time2 > 630) {
                        vt4!!.text = strings(vt4!!.text.toString(), a)
                    }
                    if (time1 < 680 && time2 > 680) {
                        vt5!!.text = strings(vt5!!.text.toString(), a)
                    }
                    if (time1 < 730 && time2 > 730) {
                        vt6!!.text = strings(vt6!!.text.toString(), a)
                    }
                    if (time1 < 780 && time2 > 780) {
                        vt7!!.text = strings(vt7!!.text.toString(), a)
                    }
                }

                if (a.day == "Wednesday") {
                    if (time1 <= 450 && time2 > 470) {
                        sr1!!.text = strings(sr2!!.text.toString(), a)
                    }
                    if (time1 < 520 && time2 > 520) {
                        sr2!!.text = strings(sr2!!.text.toString(), a)
                    }
                    if (time1 < 570 && time2 > 570) {
                        sr3!!.text = strings(sr3!!.text.toString(), a)
                    }
                    if (time1 < 630 && time2 > 630) {
                        sr4!!.text = strings(sr4!!.text.toString(), a)
                    }
                    if (time1 < 680 && time2 > 680) {
                        sr5!!.text = strings(sr5!!.text.toString(), a)
                    }
                    if (time1 < 730 && time2 > 730) {
                        sr6!!.text = strings(sr6!!.text.toString(), a)
                    }
                    if (time1 < 780 && time2 > 780) {
                        sr7!!.text = strings(sr7!!.text.toString(), a)
                    }
                }

                if (a.day == "Thursday") {

                    if (time1 <= 450 && time2 > 470) {
                        cht1!!.text = strings(cht1!!.text.toString(), a)
                    }
                    if (time1 < 520 && time2 > 520) {
                        cht2!!.text = strings(cht2!!.text.toString(), a)
                    }
                    if (time1 < 570 && time2 > 570) {
                        cht3!!.text = strings(cht3!!.text.toString(), a)
                    }
                    if (time1 < 630 && time2 > 630) {
                        cht4!!.text = strings(cht4!!.text.toString(), a)
                    }
                    if (time1 < 680 && time2 > 680) {
                        cht5!!.text = strings(cht5!!.text.toString(), a)
                    }
                    if (time1 < 730 && time2 > 730) {
                        cht6!!.text = strings(cht6!!.text.toString(), a)
                    }
                    if (time1 < 780 && time2 > 780) {
                        cht7!!.text = strings(cht7!!.text.toString(), a)
                    }
                }

                if (a.day == "Friday") {
                    if (time1 <= 450 && time2 > 470) {
                        pt1!!.text = strings(pt1!!.text.toString(), a)
                    }
                    if (time1 < 520 && time2 > 520) {
                        pt2!!.text = strings(pt2!!.text.toString(), a)
                    }
                    if (time1 < 570 && time2 > 570) {
                        pt3!!.text = strings(pt3!!.text.toString(), a)
                    }
                    if (time1 < 630 && time2 > 630) {
                        pt4!!.text = strings(pt4!!.text.toString(), a)
                    }
                    if (time1 < 680 && time2 > 680) {
                        pt5!!.text = strings(pt5!!.text.toString(), a)
                    }
                    if (time1 < 730 && time2 > 730) {
                        pt6!!.text = strings(pt6!!.text.toString(), a)
                    }
                    if (time1 < 780 && time2 > 780) {
                        pt7!!.text = strings(pt7!!.text.toString(), a)
                    }
                }
            }
            str = ""
        }

        if (pon1!!.text.length == 0) {
            pon1!!.text = "Свободен час";pon1!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pon2!!.text.length == 0) {
            pon2!!.text = "Свободен час";pon2!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pon3!!.text.length == 0) {
            pon3!!.text = "Свободен час";pon3!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pon4!!.text.length == 0) {
            pon4!!.text = "Свободен час";pon4!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pon5!!.text.length == 0) {
            pon5!!.text = "Свободен час";pon5!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pon6!!.text.length == 0) {
            pon6!!.text = "Свободен час";pon6!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pon7!!.text.length == 0) {
            pon7!!.text = "Свободен час";pon7!!.setBackgroundResource(R.drawable.borderfree)
        }


        if (vt1!!.text.length == 0) {
            vt1!!.text = "Свободен час"
            vt1!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (vt2!!.text.length == 0) {
            vt2!!.text = "Свободен час";vt2!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (vt3!!.text.length == 0) {
            vt3!!.text = "Свободен час";vt3!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (vt4!!.text.length == 0) {
            vt4!!.text = "Свободен час";vt4!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (vt5!!.text.length == 0) {
            vt5!!.text = "Свободен час";vt5!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (vt6!!.text.length == 0) {
            vt6!!.text = "Свободен час";vt6!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (vt7!!.text.length == 0) {
            vt7!!.text = "Свободен час";vt7!!.setBackgroundResource(R.drawable.borderfree)
        }

        if (sr1!!.text.length == 0) {
            sr1!!.text = "Свободен час";sr1!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (sr2!!.text.length == 0) {
            sr2!!.text = "Свободен час";sr2!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (sr3!!.text.length == 0) {
            sr3!!.text = "Свободен час";sr3!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (sr4!!.text.length == 0) {
            sr4!!.text = "Свободен час";sr4!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (sr5!!.text.length == 0) {
            sr5!!.text = "Свободен час";sr5!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (sr6!!.text.length == 0) {
            sr6!!.text = "Свободен час";sr6!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (sr7!!.text.length == 0) {
            sr7!!.text = "Свободен час";sr7!!.setBackgroundResource(R.drawable.borderfree)
        }

        if (cht1!!.text.length == 0) {
            cht1!!.text = "Свободен час";cht1!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (cht2!!.text.length == 0) {
            cht2!!.text = "Свободен час";cht2!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (cht3!!.text.length == 0) {
            cht3!!.text = "Свободен час";cht3!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (cht4!!.text.length == 0) {
            cht4!!.text = "Свободен час";cht4!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (cht5!!.text.length == 0) {
            cht5!!.text = "Свободен час";cht5!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (cht6!!.text.length == 0) {
            cht6!!.text = "Свободен час";cht6!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (cht7!!.text.length == 0) {
            cht7!!.text = "Свободен час";cht7!!.setBackgroundResource(R.drawable.borderfree)
        }

        if (pt1!!.text.length == 0) {
            pt1!!.text = "Свободен час";pt1!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pt2!!.text.length == 0) {
            pt2!!.text = "Свободен час";pt2!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pt3!!.text.length == 0) {
            pt3!!.text = "Свободен час";pt3!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pt4!!.text.length == 0) {
            pt4!!.text = "Свободен час";pt4!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pt5!!.text.length == 0) {
            pt5!!.text = "Свободен час";pt5!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pt6!!.text.length == 0) {
            pt6!!.text = "Свободен час";pt6!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pt7!!.text.length == 0) {
            pt7!!.text = "Свободен час";pt7!!.setBackgroundResource(R.drawable.borderfree)
        }
    }
}
