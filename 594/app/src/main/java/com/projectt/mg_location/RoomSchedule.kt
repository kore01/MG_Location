package com.projectt.mg_location

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.projectt.mg_location.DataModel.*
import com.projectt.mg_location.DataModel.TeacherModel.getData
import java.util.*

class RoomSchedule : BaseActivity(), Observer {

    //var RoomName : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_schedule)
        oncreate()
        val b = intent.extras
        RoomName = b.getString("room")
        Log.i("roomname", RoomName)

        ClassesModel
        ClassesModel.addObserver(this)

        val fab: View = findViewById(R.id.fab_btn)
        if(acc.id!!.length<3) fab.visibility = View.GONE
        else fab.visibility = View.VISIBLE
        fab.setOnClickListener {
            AddProblem(RoomName!!)
        }

        classes = ClassesModel.getDataForRoom(RoomName!!) as ArrayList<Classes>

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


        // teachers = TeacherModel.getData()!!
        Log.i("it does start", "HERE")
        makeclasses()

    }

    private fun AddProblem(room: String) {
        //dialog for submitting problem
        myDialog!!.setContentView(R.layout.dial_problem_room_view)
        val txtclose: TextView = myDialog!!.findViewById(R.id.txtclose)
        val btn: Button = myDialog!!.findViewById(R.id.btnproblem)
        txtclose.text = "X"
        val textt: TextInputEditText = myDialog!!.findViewById(R.id.Text)
        txtclose.setOnClickListener {
            myDialog!!.dismiss()
        }
        database2 = FirebaseDatabase.getInstance().reference

        val techn: Button = myDialog!!.findViewById(R.id.techn)
        val meb: Button = myDialog!!.findViewById(R.id.mebel)
        val and: Button = myDialog!!.findViewById(R.id.and)



        btn.setOnClickListener {

            Log.i("textdata", textt.text.toString())
            if (textt.text!!.length == 0) {
                Toast.makeText(this, "Моля, опишете проблема си - обстойно, за да бъде поправен.", Toast.LENGTH_LONG)
                    .show()
            } else {
                var problem : String = ""
                if(techn.isActivated)
                {
                    problem = problem + "Технически проблем + \n"
                }
                if(meb.isActivated)
                {
                    problem = problem + "Счупена мебел + \n"
                }
                if(and.isActivated)
                {
                    problem = problem + "Друго + \n"
                }
                problem = problem + textt.text.toString()
                val key = database2.child("RoomProblems").push().key
                database2.child("RoomProblems").child(key!!).child("Info").setValue(textt.text.toString())
                database2.child("RoomProblems").child(key!!).child("Room").setValue(room)
                myDialog!!.dismiss()
                Toast.makeText(
                    this,
                    "Проблемът Ви беше докладван.\nБлагодарим Ви за обратната връзка!",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
        myDialog!!.show()
    }

    //var classes: ArrayList<Classes> = ArrayList()
    //var teachers: ArrayList<Teachers> = ArrayList()

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
    //var teachers = TeacherModel.getData()!!
    var data = RoomModel.getData()!!

    override fun onResume() {
        super.onResume()
        val b = intent.extras
        RoomName = b.getString("room")
        //val roomnametext: TextView = findViewById(R.id.RoomNamee)
        //roomnametext.text = RoomName
        nullpol()

        makeclasses()
    }

    //var myDialog: Dialog? = null
    override fun onStart() {
        super.onStart()

        val b = intent.extras
        RoomName = b.getString("room")
        Log.i("string", RoomName)
        val namet: TextView = findViewById(R.id.namet)
        // val info: TextView = findViewById(R.id.info)
        //rooms = RoomModel.getData()
        //info.text=teachers[RoomName!!.toInt()].subject1
        namet.text = RoomName
        //val roomnametext: TextView = findViewById(R.id.RoomNamee)
        //roomnametext.text = RoomName
        teachers = getData()!!
        nullpol()
        makeclasses()
        val smqna: Button = findViewById(R.id.smqna)
        smqna.setOnClickListener {
            val intent = Intent(this, RoomSchedule2::class.java)
            intent.putExtra("room", RoomName)
            startActivity(intent)
        }
        myDialog = Dialog(this)

//        ChooseTeacher(this)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }


    override fun update(p0: Observable?, p1: Any?) {
        nullpol()
        classes = ClassesModel.getDataForRoom(RoomName!!) as ArrayList<Classes>
        teachers = TeacherModel.getData()!!
        classs = ClassModel.getData()!!
        data = RoomModel.getData()!!
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


    var time1: Int = 0
    var time2: Int = 0
    var str: String = ""
    var c1: Int = 0
    var c2: Int = 0
    var c3: Int = 0
    var c4: Int = 0

    fun strings(st: String, t: Classes): String {
        var res: String? = ""
        Log.i("Here", st)
        Log.i(
            "Here2",
            teachers[t.teachernumber!!.toInt() - 1].name + " " + teachers[t.teachernumber!!.toInt() - 1].familyname + "\n"
        )
        if (st.length == 0) {
            res =
                teachers[t.teachernumber!!.toInt() - 1].name + " " + teachers[t.teachernumber!!.toInt() - 1].familyname + "\n" + t.classs
            return res
        }
        Log.i("result1", st)
        for (a in st) {
            if (a != '\n')
                res += a
            else {
                res =
                    res + ", " + teachers[t.teachernumber!!.toInt() - 1].name + " " + teachers[t.teachernumber!!.toInt() - 1].familyname + "\n"
            }
            Log.i("result", res)
        }
        res = res + ", " + t.classs
        return res
    }

    fun makeclasses() {
        //nullpol()
        Log.i("roomnumber", "The roomnymber right now is ")
        str = ""
        Log.i("mess", "look " + classes.size)
        //   Log.i("thisiswhat", RoomName)
        val b = intent.extras
        RoomName = b.getString("room")
        // Log.i("thisiswhat", RoomName)
        for (a in classes) {
            Log.i("roomnumber", "The roomnymber right now is " + a.room)
            Log.i("teacher", "The teacher right now is " + RoomName)
            if (a.room == RoomName) {
                Log.i("mhm", "It does work")

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
                teachers = getData()!!
                Log.i("teachers number", teachers.size.toString())
                str =
                    a.classs + "\n" + teachers[a.teachernumber!!.toInt() - 1].name + " " + teachers[a.teachernumber!!.toInt() - 1].familyname

                Log.i("found", "Found this string: " + str)
                Log.i("found", a.from + " " + a.too + " " + a.day)
                Log.i("found", time1.toString() + " " + time2.toString())

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

        //color the empty ones
        val colorValue = ContextCompat.getColor(this, R.color.colorFREE)
        if (vt1!!.text.length == 0) {
            Log.i("rhino", "rhino WHY?! " + vt1!!.text)
        }
        Log.i("rhino", "rhino " + sr1!!.text)
        Log.i("rhino", "rhino " + cht1!!.text)
        Log.i("rhino", "rhino " + pt1!!.text)


        if (pon1!!.text.length == 0) {
            pon1!!.text = "Свободна";pon1!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pon2!!.text.length == 0) {
            pon2!!.text = "Свободна";pon2!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pon3!!.text.length == 0) {
            pon3!!.text = "Свободна";pon3!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pon4!!.text.length == 0) {
            pon4!!.text = "Свободна";pon4!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pon5!!.text.length == 0) {
            pon5!!.text = "Свободна";pon5!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pon6!!.text.length == 0) {
            pon6!!.text = "Свободна";pon6!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pon7!!.text.length == 0) {
            pon7!!.text = "Свободна";pon7!!.setBackgroundResource(R.drawable.borderfree)
        }

        if (vt1!!.text.length == 0) {
            Log.i("HRERER", "WHATTHEHELL")
            vt1!!.text = "Свободна"
            vt1!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (vt2!!.text.length == 0) {
            vt2!!.text = "Свободна";vt2!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (vt3!!.text.length == 0) {
            vt3!!.text = "Свободна";vt3!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (vt4!!.text.length == 0) {
            vt4!!.text = "Свободна";vt4!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (vt5!!.text.length == 0) {
            vt5!!.text = "Свободна";vt5!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (vt6!!.text.length == 0) {
            vt6!!.text = "Свободна";vt6!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (vt7!!.text.length == 0) {
            vt7!!.text = "Свободна";vt7!!.setBackgroundResource(R.drawable.borderfree)
        }

        if (sr1!!.text.length == 0) {
            sr1!!.text = "Свободна";sr1!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (sr2!!.text.length == 0) {
            sr2!!.text = "Свободна";sr2!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (sr3!!.text.length == 0) {
            sr3!!.text = "Свободна";sr3!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (sr4!!.text.length == 0) {
            sr4!!.text = "Свободна";sr4!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (sr5!!.text.length == 0) {
            sr5!!.text = "Свободна";sr5!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (sr6!!.text.length == 0) {
            sr6!!.text = "Свободна";sr6!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (sr7!!.text.length == 0) {
            sr7!!.text = "Свободна";sr7!!.setBackgroundResource(R.drawable.borderfree)
        }

        if (cht1!!.text.length == 0) {
            cht1!!.text = "Свободна";cht1!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (cht2!!.text.length == 0) {
            cht2!!.text = "Свободна";cht2!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (cht3!!.text.length == 0) {
            cht3!!.text = "Свободна";cht3!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (cht4!!.text.length == 0) {
            cht4!!.text = "Свободна";cht4!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (cht5!!.text.length == 0) {
            cht5!!.text = "Свободна";cht5!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (cht6!!.text.length == 0) {
            cht6!!.text = "Свободна";cht6!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (cht7!!.text.length == 0) {
            cht7!!.text = "Свободна";cht7!!.setBackgroundResource(R.drawable.borderfree)
        }

        if (pt1!!.text.length == 0) {
            pt1!!.text = "Свободна";pt1!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pt2!!.text.length == 0) {
            pt2!!.text = "Свободна";pt2!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pt3!!.text.length == 0) {
            pt3!!.text = "Свободна";pt3!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pt4!!.text.length == 0) {
            pt4!!.text = "Свободна";pt4!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pt5!!.text.length == 0) {
            pt5!!.text = "Свободна";pt5!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pt6!!.text.length == 0) {
            pt6!!.text = "Свободна";pt6!!.setBackgroundResource(R.drawable.borderfree)
        }
        if (pt7!!.text.length == 0) {
            pt7!!.text = "Свободна";pt7!!.setBackgroundResource(R.drawable.borderfree)
        }
    }
}



