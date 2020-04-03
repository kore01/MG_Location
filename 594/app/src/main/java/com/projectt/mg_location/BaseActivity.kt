package com.projectt.mg_location

import android.support.v4.widget.DrawerLayout
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.support.design.widget.NavigationView
import android.support.design.widget.TextInputEditText
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.MenuItem
import android.widget.*
import com.firebase.ui.auth.AuthUI
import com.google.firebase.FirebaseApp
import android.view.View
import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.core.view.View
import com.projectt.mg_location.Adapters.ListAdapter
import com.projectt.mg_location.Adapters.RoomAdapter
import com.projectt.mg_location.DataModel.*
import com.projectt.mg_location.DataModel.TeacherModel.getData
import java.util.*
import kotlin.collections.ArrayList


open class BaseActivity : Observer, NavigationView.OnNavigationItemSelectedListener, LoginAct(),
    AdapterView.OnItemSelectedListener {
    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        textView1.text = list_of_items[position]
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //base activity with the integrated menu. It is inherited by all other activities


    private var drawer: DrawerLayout? = null
    var myDialog: Dialog? = null
    var myDialog2: Dialog? = null
    var myDialog3: Dialog? = null

    var myStartDial: Dialog? = null

    var DialogShowTeachers: Dialog? = null
    lateinit var toggle: ActionBarDrawerToggle

    var classes: java.util.ArrayList<Classes> = ClassesModel.getData()!!

    var rooms: java.util.ArrayList<Rooms> = RoomModel.getData()!!

    fun oncreate() {
        FirebaseApp.initializeApp(this)
        TeacherModel
        TeacherModel.addObserver(this)
        AccountModel
        AccountModel.addObserver(this)
        ClassesModel
        ClassesModel.addObserver(this)

        //take previous enters
        prefs = Prefs(this)
        acc.id = prefs!!.Acc_ID
        acc.type = prefs!!.type
        acc.myclass = prefs!!.myclass
        acc.isadmin = prefs!!.isadmin


        //set menu
        val toolbar: android.support.v7.widget.Toolbar = this.findViewById(R.id.toolbar)
        teachers = getData()!!
        setSupportActionBar(toolbar)
        val actionBar = this.supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
        }

        drawer = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        drawer!!.addDrawerListener(toggle)
        toggle.syncState()

        val nav_Menu = navigationView.menu
        Log.i("admin", acc.isadmin)
        nav_Menu.findItem(R.id.admin).isVisible = acc.isadmin == "1"

        //declare dialogs for the menu
        myDialog = Dialog(this)
        myDialog2 = Dialog(this)
        myDialog3 = Dialog(this)

        myStartDial = Dialog(this)

        DialogShowTeachers = Dialog(this)


    }


    override fun onBackPressed() {
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //for opening the menu with the hamburger icon
        return when (item.itemId) {
            android.R.id.home -> {
                drawer!!.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        //actions on every menu item selected
        when (item.itemId) {
            android.R.id.home -> {
                drawer!!.openDrawer(GravityCompat.START)
                return true
            }
            R.id.nav_begining -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                drawer!!.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.nav_profile -> {
                drawer!!.closeDrawer(GravityCompat.START)

                if (acc.myclass!!.length < 2) ShowLogins()
                else ShowProfile()
                return true
            }
            R.id.suggevents -> {
                val intent = Intent(this, SuggEvents::class.java)
                startActivity(intent)
                drawer!!.closeDrawer(GravityCompat.START)
                return true
            }

            R.id.roomproblems -> {
                val intent = Intent(this, RoomProblems::class.java)
                startActivity(intent)
                drawer!!.closeDrawer(GravityCompat.START)
                return true
            }

            R.id.suggextra -> {
                val intent = Intent(this, New_ExtraAct::class.java)
                startActivity(intent)
                drawer!!.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.nav_extra -> {
                val intent = Intent(this, Extra_Act::class.java)
                //intent.putExtra("pos", acc.myclass)
                startActivity(intent)
                return true
            }
            R.id.nav_classwork -> {
                val intent = Intent(this, ClassWork_Act::class.java)
                //intent.putExtra("pos", acc.myclass)
                startActivity(intent)
                return true
            }
            R.id.nav_timetable -> {
                val intent = Intent(this, TimeTableWork::class.java)
                //intent.putExtra("pos", acc.myclass)
                startActivity(intent)
                return true
            }
            R.id.nav_myschedule -> {
                drawer!!.closeDrawer(GravityCompat.START)

                acc.id = prefs!!.Acc_ID
                acc.type = prefs!!.type
                acc.myclass = prefs!!.myclass
                acc.isadmin = prefs!!.isadmin

                if (acc.type == "1" || acc.type == "4") {
                    if (acc.myclass == "5а" || acc.myclass == "5б" || acc.myclass == "5в" || acc.myclass == "12а" || acc.myclass == "12б" || acc.myclass == "12в" || acc.myclass == "12г" || acc.myclass == "12д" || acc.myclass == "12е" || acc.myclass == "12ж" || acc.myclass == "12з") {
                        val intent = Intent(this, ClassSchedule2::class.java)
                        intent.putExtra("pos", acc.myclass)
                        startActivity(intent)
                    } else {
                        val intent = Intent(this, ClassSchedule::class.java)
                        intent.putExtra("pos", acc.myclass)
                        startActivity(intent)
                    }
                } else if (acc.type == "2") {
                    val intent = Intent(this, TeacherSchedule::class.java)
                    intent.putExtra("pos", acc.myclass)
                    startActivity(intent)
                } else {
                    ShowLogins()
                }
                return true
            }
            R.id.problems -> {
                val intent = Intent(this, Problems::class.java)
                //intent.putExtra("pos", acc.myclass)
                startActivity(intent)
                return true
            }
            R.id.nav_fullschedule -> {
                drawer!!.closeDrawer(GravityCompat.START)
                SearchingFullSchedule()
                return true
            }
            R.id.nav_problem -> {
                drawer!!.closeDrawer(GravityCompat.START)
                DoklProblem()
            }
            R.id.nav_info -> {
                drawer!!.closeDrawer(GravityCompat.START)
                ShowInfo()
            }
        }
        drawer!!.closeDrawer(GravityCompat.START)
        return true
    }

    private fun ShowProfile() {
        myDialog!!.setContentView(R.layout.dial_profile)
        val txtclose: TextView = myDialog!!.findViewById(R.id.txtclose)
        txtclose.text = "X"
        val txttype: TextView = myDialog!!.findViewById(R.id.txttype)
        val txtclass: TextView = myDialog!!.findViewById(R.id.txtclass)
        if (acc.type == "2") {
            txttype.text = "Име: "
            txtclass.text = teachers[acc.myclass?.toInt()!!-1].name+" "+teachers[acc.myclass?.toInt()!!-1].familyname
        } else {
            txttype.text = "Клас: "
            txtclass.text = acc.myclass
        }

        val changeclass: Button = myDialog!!.findViewById(R.id.changeclass)
        changeclass.setOnClickListener {
            ChooseTeachOrStud()
            myDialog!!.dismiss()
        }
        val btnlogout: Button = myDialog!!.findViewById(R.id.logoutBtn)
        btnlogout.setOnClickListener {
            AuthUI.getInstance().signOut(this).addOnCompleteListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            prefs = Prefs(this)
            acc.id = ""
            acc.type = ""
            acc.myclass = ""
            acc.isadmin = ""
            prefs!!.type = ""
            prefs!!.Acc_ID = ""
            prefs!!.myclass = ""
            prefs!!.isadmin = ""
            prefs!!.notifications = false

        }
        txtclose.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            myDialog!!.dismiss()
        }

        val switch: Switch = myDialog!!.findViewById(R.id.switchh)
        if (prefs!!.notifications == true) switch.isChecked = true
        else switch.isChecked = false


        switch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            prefs!!.notifications = isChecked
        })
        myDialog!!.show()
    }

    private fun SearchingFullSchedule() {
        //do the search in the full schedule
        myDialog!!.setContentView(R.layout.dial_search_for)
        val txtclose: TextView = myDialog!!.findViewById(R.id.txtclose)
        val teacherchoice: Button = myDialog!!.findViewById(R.id.search_teacher)
        val roomchoice: Button = myDialog!!.findViewById(R.id.search_room)
        val classchoice: Button = myDialog!!.findViewById(R.id.search_class)
        val extraroom: Button = myDialog!!.findViewById(R.id.extra_act)


        txtclose.text = "X"
        txtclose.setOnClickListener {
            myDialog!!.dismiss()
        }
        teacherchoice.setOnClickListener {
            ChooseTeacher("All")
            myDialog!!.dismiss()
        }
        roomchoice.setOnClickListener {
            ChooseRoom()
            myDialog!!.dismiss()
        }
        classchoice.setOnClickListener {
            ChooseClasses()
            myDialog!!.dismiss()
        }
        extraroom.setOnClickListener {

            ChooseRoomInterval()

            myDialog!!.dismiss()

        }

        myDialog!!.show()
    }

    fun startStart() {
        //do the search in the full schedule
        myStartDial!!.setContentView(R.layout.dial_startstart)
        val txtclose: TextView = myStartDial!!.findViewById(R.id.txtclose)

        txtclose.text = "X"
        txtclose.setOnClickListener {
            myStartDial!!.dismiss()
        }

        val btn: Button = myStartDial!!.findViewById(R.id.btn)
        btn.setOnClickListener {
            myStartDial!!.dismiss()
            ShowLogins()
        }
        myStartDial!!.show()
    }

    private fun ChooseTeacher(subject: String) {
        DialogShowTeachers!!.setContentView(R.layout.dial_teacher_choose)
        val txtclose: TextView = DialogShowTeachers!!.findViewById(R.id.txtclose)
        txtclose.text = "X"

        txtclose.setOnClickListener {
            DialogShowTeachers!!.dismiss()
        }
        teachers = TeacherModel.getData()!!
        teachers2 = teachers


        var bel: Button = DialogShowTeachers!!.findViewById(R.id.bel)
        var bel1: Button = DialogShowTeachers!!.findViewById(R.id.bel1)

        cleanbuttons()
        val list: ListView = DialogShowTeachers!!.findViewById(R.id.list)

        bel.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            teachers2 = teachers2.filter { t -> t.subject1!!.contains("Български език и литература") }
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
            bel1.visibility = View.VISIBLE
            bel.visibility = View.GONE
        }
        bel1.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
        }

        var mat: Button = DialogShowTeachers!!.findViewById(R.id.mat)
        var mat1: Button = DialogShowTeachers!!.findViewById(R.id.mat1)
        mat.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            teachers2 = teachers2.filter { t -> t.subject1!!.contains("Математика") }
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
            mat1.visibility = View.VISIBLE
            mat.visibility = View.GONE
        }
        mat1.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
        }

        var ae: Button = DialogShowTeachers!!.findViewById(R.id.ae)
        var ae1: Button = DialogShowTeachers!!.findViewById(R.id.ae1)
        ae.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            teachers2 = teachers2.filter { t -> t.subject1!!.contains("Английски език") }
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
            ae1.visibility = View.VISIBLE
            ae.visibility = View.GONE
        }
        ae1.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
        }

        var ne: Button = DialogShowTeachers!!.findViewById(R.id.ne)
        var ne1: Button = DialogShowTeachers!!.findViewById(R.id.ne1)
        ne.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            teachers2 = teachers2.filter { t -> t.subject1!!.contains("Немски език") }
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
            ne1.visibility = View.VISIBLE
            ne.visibility = View.GONE
        }
        ne1.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
        }

        var re: Button = DialogShowTeachers!!.findViewById(R.id.re)
        var re1: Button = DialogShowTeachers!!.findViewById(R.id.re1)
        re.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            teachers2 = teachers2.filter { t -> t.subject1!!.contains("Руски език") }
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
            re1.visibility = View.VISIBLE
            re.visibility = View.GONE
        }
        re1.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
        }


        var fe: Button = DialogShowTeachers!!.findViewById(R.id.fe)
        var fe1: Button = DialogShowTeachers!!.findViewById(R.id.fe1)
        fe.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            teachers2 = teachers2.filter { t -> t.subject1!!.contains("Френски език") }
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
            fe1.visibility = View.VISIBLE
            fe.visibility = View.GONE
        }
        fe1.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
        }

        var it: Button = DialogShowTeachers!!.findViewById(R.id.it)
        var it1: Button = DialogShowTeachers!!.findViewById(R.id.it1)
        it.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            teachers2 = teachers2.filter { t -> t.subject1 == "Информатика/ИТ" }
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
            it1.visibility = View.VISIBLE
            it.visibility = View.GONE
        }
        it1.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
        }


        var ist: Button = DialogShowTeachers!!.findViewById(R.id.ist)
        var ist1: Button = DialogShowTeachers!!.findViewById(R.id.ist1)
        ist.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            teachers2 = teachers2.filter { t -> t.subject1 == "История" }
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
            ist1.visibility = View.VISIBLE
            ist.visibility = View.GONE
        }
        ist1.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
        }

        var fil: Button = DialogShowTeachers!!.findViewById(R.id.fil)
        var fil1: Button = DialogShowTeachers!!.findViewById(R.id.fil1)
        fil.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            teachers2 = teachers2.filter { t -> t.subject1 == "Философия" }
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
            fil1.visibility = View.VISIBLE
            fil.visibility = View.GONE
        }
        fil1.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
        }


        var bio: Button = DialogShowTeachers!!.findViewById(R.id.bio)
        var bio1: Button = DialogShowTeachers!!.findViewById(R.id.bio1)
        bio.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            teachers2 = teachers2.filter { t -> t.subject1 == "Биология" }
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
            bio1.visibility = View.VISIBLE
            bio.visibility = View.GONE
        }
        bio1.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
        }

        var geo: Button = DialogShowTeachers!!.findViewById(R.id.geo)
        var geo1: Button = DialogShowTeachers!!.findViewById(R.id.geo1)
        geo.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            teachers2 = teachers2.filter { t -> t.subject1 == "География" }
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
            geo1.visibility = View.VISIBLE
            geo.visibility = View.GONE
        }
        geo1.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
        }

        var him: Button = DialogShowTeachers!!.findViewById(R.id.him)
        var him1: Button = DialogShowTeachers!!.findViewById(R.id.him1)
        him.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            teachers2 = teachers2.filter { t -> t.subject1 == "Химия" }
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
            him1.visibility = View.VISIBLE
            him.visibility = View.GONE
        }
        him1.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
        }

        var fiz: Button = DialogShowTeachers!!.findViewById(R.id.fiz)
        var fiz1: Button = DialogShowTeachers!!.findViewById(R.id.fiz1)
        fiz.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            teachers2 = teachers2.filter { t -> t.subject1 == "Физика" }
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
            fiz1.visibility = View.VISIBLE
            fiz.visibility = View.GONE
        }
        fiz1.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
        }

        var fvs: Button = DialogShowTeachers!!.findViewById(R.id.fvs)
        var fvs1: Button = DialogShowTeachers!!.findViewById(R.id.fvs1)
        fvs.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            teachers2 = teachers2.filter { t -> t.subject1!!.contains("Физ. възпитание и спорт") }
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
            fvs1.visibility = View.VISIBLE
            fvs.visibility = View.GONE
        }
        fvs1.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
        }

        var mus: Button = DialogShowTeachers!!.findViewById(R.id.mus)
        var mus1: Button = DialogShowTeachers!!.findViewById(R.id.mus1)
        mus.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            teachers2 = teachers2.filter { t -> t.subject1!!.contains("Музика") }
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
            mus1.visibility = View.VISIBLE
            mus.visibility = View.GONE
        }
        mus1.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
        }

        var ii: Button = DialogShowTeachers!!.findViewById(R.id.ii)
        var ii1: Button = DialogShowTeachers!!.findViewById(R.id.ii1)
        ii.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            teachers2 = teachers2.filter { t -> t.subject1!!.contains("Изобразително изкуство") }
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
            ii1.visibility = View.VISIBLE
            ii.visibility = View.GONE
        }
        ii1.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
        }

        var tip: Button = DialogShowTeachers!!.findViewById(R.id.tip)
        var tip1: Button = DialogShowTeachers!!.findViewById(R.id.tip1)
        tip.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            teachers2 = teachers2.filter { t -> t.subject1!!.contains("Технологии и предприемачество") }
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
            tip1.visibility = View.VISIBLE
            tip.visibility = View.GONE
        }
        tip1.setOnClickListener {

            cleanbuttons()
            teachers2 = teachers
            val adapter = ListAdapter(
                this,
                teachers2 as ArrayList<Teachers>
            )
            list.adapter = adapter
        }

        val adapter = ListAdapter(
            this,
            teachers2 as ArrayList<Teachers>
        )
        list.adapter = adapter

        list.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, TeacherSchedule::class.java)
            intent.putExtra("pos", teachers2[position].number)
            this.startActivity(intent)
        }

        DialogShowTeachers!!.show()
    }


    private fun cleanbuttons() {
        var bel: Button = DialogShowTeachers!!.findViewById(R.id.bel)
        var bel1: Button = DialogShowTeachers!!.findViewById(R.id.bel1)
        bel1.visibility = View.GONE
        bel.visibility = View.VISIBLE

        var mat: Button = DialogShowTeachers!!.findViewById(R.id.mat)
        var mat1: Button = DialogShowTeachers!!.findViewById(R.id.mat1)
        mat1.visibility = View.GONE
        mat.visibility = View.VISIBLE

        var ae: Button = DialogShowTeachers!!.findViewById(R.id.ae)
        var ae1: Button = DialogShowTeachers!!.findViewById(R.id.ae1)
        ae1.visibility = View.GONE
        ae.visibility = View.VISIBLE

        var him: Button = DialogShowTeachers!!.findViewById(R.id.him)
        var him1: Button = DialogShowTeachers!!.findViewById(R.id.him1)
        him1.visibility = View.GONE
        him.visibility = View.VISIBLE

        var ne: Button = DialogShowTeachers!!.findViewById(R.id.ne)
        var ne1: Button = DialogShowTeachers!!.findViewById(R.id.ne1)
        ne1.visibility = View.GONE
        ne.visibility = View.VISIBLE

        var fe: Button = DialogShowTeachers!!.findViewById(R.id.fe)
        var fe1: Button = DialogShowTeachers!!.findViewById(R.id.fe1)
        fe1.visibility = View.GONE
        fe.visibility = View.VISIBLE

        var it: Button = DialogShowTeachers!!.findViewById(R.id.it)
        var it1: Button = DialogShowTeachers!!.findViewById(R.id.it1)
        it1.visibility = View.GONE
        it.visibility = View.VISIBLE

        var bio: Button = DialogShowTeachers!!.findViewById(R.id.bio)
        var bio1: Button = DialogShowTeachers!!.findViewById(R.id.bio1)
        bio1.visibility = View.GONE
        bio.visibility = View.VISIBLE

        var fvs: Button = DialogShowTeachers!!.findViewById(R.id.fvs)
        var fvs1: Button = DialogShowTeachers!!.findViewById(R.id.fvs1)
        fvs1.visibility = View.GONE
        fvs.visibility = View.VISIBLE

        var ii: Button = DialogShowTeachers!!.findViewById(R.id.ii)
        var ii1: Button = DialogShowTeachers!!.findViewById(R.id.ii1)
        ii1.visibility = View.GONE
        ii.visibility = View.VISIBLE

        var mus: Button = DialogShowTeachers!!.findViewById(R.id.mus)
        var mus1: Button = DialogShowTeachers!!.findViewById(R.id.mus1)
        mus1.visibility = View.GONE
        mus.visibility = View.VISIBLE

        var ist: Button = DialogShowTeachers!!.findViewById(R.id.ist)
        var ist1: Button = DialogShowTeachers!!.findViewById(R.id.ist1)
        ist1.visibility = View.GONE
        ist.visibility = View.VISIBLE

        var fil: Button = DialogShowTeachers!!.findViewById(R.id.fil)
        var fil1: Button = DialogShowTeachers!!.findViewById(R.id.fil1)
        fil1.visibility = View.GONE
        fil.visibility = View.VISIBLE

        var geo: Button = DialogShowTeachers!!.findViewById(R.id.geo)
        var geo1: Button = DialogShowTeachers!!.findViewById(R.id.geo1)
        geo1.visibility = View.GONE
        geo.visibility = View.VISIBLE

        var fiz: Button = DialogShowTeachers!!.findViewById(R.id.fiz)
        var fiz1: Button = DialogShowTeachers!!.findViewById(R.id.fiz1)
        fiz1.visibility = View.GONE
        fiz.visibility = View.VISIBLE

        var tip: Button = DialogShowTeachers!!.findViewById(R.id.tip)
        var tip1: Button = DialogShowTeachers!!.findViewById(R.id.tip1)
        tip1.visibility = View.GONE
        tip.visibility = View.VISIBLE

    }

    private fun ChooseRoom() {
        //choice of room - from fullschedule
        myDialog2!!.setContentView(R.layout.dial_room_search)
        val txtclose: TextView = myDialog2!!.findViewById(R.id.txtclose)
        txtclose.text = "X"
        txtclose.setOnClickListener {
            myDialog2!!.dismiss()
        }
        //first floor
        val b106: Button = myDialog2!!.findViewById(R.id.b106)
        b106.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "106")
            this.startActivity(intent)
        }
        val b107: Button = myDialog2!!.findViewById(R.id.b107)
        b107.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "107")
            this.startActivity(intent)
        }
        val b2000: Button = myDialog2!!.findViewById(R.id.b2000)
        b2000.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "2000")
            this.startActivity(intent)
        }
        val bshannon: Button = myDialog2!!.findViewById(R.id.bshan)
        bshannon.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "Shannon")
            this.startActivity(intent)
        }
        val baz: Button = myDialog2!!.findViewById(R.id.baz)
        baz.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "Актова зала")
            this.startActivity(intent)
        }
        val bfvs: Button = myDialog2!!.findViewById(R.id.bfvs)
        bfvs.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "Физ. салон")
            this.startActivity(intent)
        }

        //second floor
        val b206: Button = myDialog2!!.findViewById(R.id.b206)
        b206.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "206")
            this.startActivity(intent)
        }
        val b205: Button = myDialog2!!.findViewById(R.id.b205)
        b205.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "205")
            this.startActivity(intent)
        }
        val b204: Button = myDialog2!!.findViewById(R.id.b204)
        b204.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "204")
            this.startActivity(intent)
        }
        val b203: Button = myDialog2!!.findViewById(R.id.b203)
        b203.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "203")
            this.startActivity(intent)
        }
        val b202: Button = myDialog2!!.findViewById(R.id.b202)
        b202.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "202")
            this.startActivity(intent)
        }
        val b201: Button = myDialog2!!.findViewById(R.id.b201)
        b201.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "201")
            this.startActivity(intent)
        }
        val b213: Button = myDialog2!!.findViewById(R.id.b213)
        b213.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "213")
            this.startActivity(intent)
        }
        val b214: Button = myDialog2!!.findViewById(R.id.b214)
        b214.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "214")
            this.startActivity(intent)
        }
        val b215: Button = myDialog2!!.findViewById(R.id.b215)
        b215.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "215")
            this.startActivity(intent)
        }
        val b216: Button = myDialog2!!.findViewById(R.id.b216)
        b216.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "216")
            this.startActivity(intent)
        }
        val b213a: Button = myDialog2!!.findViewById(R.id.b213a)
        b213a.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "213а")
            this.startActivity(intent)
        }

        //third floor
        val b306: Button = myDialog2!!.findViewById(R.id.b306)
        b306.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "306")
            this.startActivity(intent)
        }
        val b307: Button = myDialog2!!.findViewById(R.id.b307)
        b307.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "307")
            this.startActivity(intent)
        }
        val b315: Button = myDialog2!!.findViewById(R.id.b315)
        b315.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "315")
            this.startActivity(intent)
        }
        val b300: Button = myDialog2!!.findViewById(R.id.b300)
        b300.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "300")
            this.startActivity(intent)
        }
        val b301: Button = myDialog2!!.findViewById(R.id.b301)
        b301.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "301")
            this.startActivity(intent)
        }
        val b302: Button = myDialog2!!.findViewById(R.id.b302)
        b302.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "302")
            this.startActivity(intent)
        }
        val b303: Button = myDialog2!!.findViewById(R.id.b303)
        b303.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "303")
            this.startActivity(intent)
        }
        val b304: Button = myDialog2!!.findViewById(R.id.b304)
        b304.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "304")
            this.startActivity(intent)
        }
        val b305: Button = myDialog2!!.findViewById(R.id.b305)
        b305.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "305")
            this.startActivity(intent)
        }
        val b308: Button = myDialog2!!.findViewById(R.id.b308)
        b308.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "308")
            this.startActivity(intent)
        }
        val b310: Button = myDialog2!!.findViewById(R.id.b310)
        b310.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "310")
            this.startActivity(intent)
        }

        //fourth floor
        val b410: Button = myDialog2!!.findViewById(R.id.b410)
        b410.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "410")
            this.startActivity(intent)
        }
        val b400: Button = myDialog2!!.findViewById(R.id.b400)
        b400.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "400")
            this.startActivity(intent)
        }
        val b401: Button = myDialog2!!.findViewById(R.id.b401)
        b401.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "401")
            this.startActivity(intent)
        }
        val b402: Button = myDialog2!!.findViewById(R.id.b402)
        b402.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "402")
            this.startActivity(intent)
        }
        val b403: Button = myDialog2!!.findViewById(R.id.b403)
        b403.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "403")
            this.startActivity(intent)
        }
        val b404: Button = myDialog2!!.findViewById(R.id.b404)
        b404.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "404")
            this.startActivity(intent)
        }
        val b405: Button = myDialog2!!.findViewById(R.id.b405)
        b405.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "405")
            this.startActivity(intent)
        }
        val b406: Button = myDialog2!!.findViewById(R.id.b406)
        b406.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "406")
            this.startActivity(intent)
        }
        val b407: Button = myDialog2!!.findViewById(R.id.b407)
        b407.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "407")
            this.startActivity(intent)
        }
        val b408: Button = myDialog2!!.findViewById(R.id.b408)
        b408.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "408")
            this.startActivity(intent)
        }
        val b409: Button = myDialog2!!.findViewById(R.id.b409)
        b409.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "409")
            this.startActivity(intent)
        }
        val b411: Button = myDialog2!!.findViewById(R.id.b411)
        b411.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "411")
            this.startActivity(intent)
        }

        //undreground
        val bkz1: Button = myDialog2!!.findViewById(R.id.bkz1)
        bkz1.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "КЗ-1")
            this.startActivity(intent)
        }
        val bkz2: Button = myDialog2!!.findViewById(R.id.bkz2)
        bkz2.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "КЗ-2")
            this.startActivity(intent)
        }
        val bmega: Button = myDialog2!!.findViewById(R.id.bmega)
        bmega.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "МЕГА")
            this.startActivity(intent)
        }
        val bkz3: Button = myDialog2!!.findViewById(R.id.bkz3)
        bkz3.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "КЗ-3")
            this.startActivity(intent)
        }

        //house
        val boz: Button = myDialog2!!.findViewById(R.id.boz)
        boz.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "ОЗ")
            this.startActivity(intent)
        }
        val b1330: Button = myDialog2!!.findViewById(R.id.b1330)
        b1330.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "1330")
            this.startActivity(intent)
        }
        val bmk1: Button = myDialog2!!.findViewById(R.id.bmk1)
        bmk1.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "МК-1")
            this.startActivity(intent)
        }
        val bmk2: Button = myDialog2!!.findViewById(R.id.bmk2)
        bmk2.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "МК-2")
            this.startActivity(intent)
        }
        val bmk3: Button = myDialog2!!.findViewById(R.id.bmk3)
        bmk3.setOnClickListener {
            val intent = Intent(this, RoomSchedule::class.java)
            intent.putExtra("room", "МК-3")
            this.startActivity(intent)
        }

        myDialog2!!.show()
    }

    lateinit var textView1: TextView

    private fun ChooseRoomInterval() {
        //choice of room - from fullschedule
        myDialog2!!.setContentView(R.layout.dial_search_room_time)
        val txtclose: TextView = myDialog2!!.findViewById(R.id.txtclose)
        txtclose.text = "X"
        txtclose.setOnClickListener {
            myDialog2!!.dismiss()
        }

        textView1 = myDialog2!!.findViewById(R.id.date)

        val timeView: TextView = myDialog2!!.findViewById(R.id.time)

        val timeView2: TextView = myDialog2!!.findViewById(R.id.time2)

        timeView.setOnClickListener {

            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR)
            val minute = c.get(Calendar.MINUTE)

            val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener(function = { view, h, m ->

                timeView.text=""
                if (h < 10) timeView.text = "0"
                timeView.text = timeView.text.toString() + h.toString() + ":"
                if (m < 10) timeView.text = timeView.text.toString() + "0"
                timeView.text = timeView.text.toString() + m.toString()
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

                timeView2.text=""
                if (h < 10) timeView2.text = "0"
                timeView2.text = timeView2.text.toString() + h.toString() + ":"
                if (m < 10) timeView2.text = timeView2.text.toString() + "0"
                timeView2.text = timeView2.text.toString() + m.toString()
                //timeView.setText(h.toString() + ":" + m.toString())
                //Toast.makeText(this, h.toString() + " : " + m + " : ", Toast.LENGTH_LONG).show()
            }), hour, minute, true)
            tpd.show()
        }

        //textView.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())

        var cal = Calendar.getInstance()

        textView1.setOnClickListener {
            chooseDay()
        }


        val submit: Button = myDialog2!!.findViewById(R.id.submit)

        submit.setOnClickListener {

            if (textView1.text.length == 0 || timeView.text.length == 0 || timeView2.text.length == 0) {
                Toast.makeText(this, "Моля, попълнете всички полета.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(
                    this,
                    textView1.text.toString() + " " + timeView.text.toString() + " " + timeView.text.toString(),
                    Toast.LENGTH_LONG
                )
                    .show()

                ShowEmptyRoomsFor(textView1.text.toString(), timeView.text.toString(), timeView2.toString())

            }

            myDialog2!!.dismiss()
        }
        myDialog2!!.show()
    }


    private fun timetomin(time: String) :Int
    {
        Log.i("this is the time", time)
        if(time.length>=5)return time[0].toInt()*10*60+time[1].toInt()*60+time[3].toInt()*10+time[4].toInt()
        else return time[0].toInt()*10*60+time[1].toInt()*60+time[4].toInt()
    }
    private fun ShowEmptyRoomsFor(day: String, from: String, to: String) {
        myDialog3!!.setContentView(R.layout.dial_show_empty_rooms)
        val txtclose: TextView = myDialog3!!.findViewById(R.id.txtclose)
        txtclose.text = "X"

        txtclose.setOnClickListener {
            myDialog3!!.dismiss()
        }

        var time1:Int = timetomin(from)
       var time2 : Int = timetomin(to)

        var daynum: Int = 0
        if (day == "Понделник") {
            daynum = 1

        }
        if (day == "Вторник") {
            daynum = 2

        }
        if (day == "Сряда") {
            daynum = 3

        }
        if (day == "Четвъртък") {
            daynum = 4
        }
        if (day == "Петък") {
            daynum = 5

        }
        if (day == "Събота") {
            daynum = 6

        }
        if (day == "Неделя") {
            daynum = 7

        }

        var sectime1 : Int
        var sectime2 : Int
        var emptyrooms: ArrayList<Rooms> = ArrayList()
        emptyrooms = rooms

        for (i in classes) {
            if (i.day == "Monday" && daynum !=1)continue
            if (i.day == "Tuesday" && daynum !=2)continue
            if (i.day == "Wednesday" && daynum !=3)continue
            if (i.day == "Thursday" && daynum !=4)continue
            if (i.day == "Friday" && daynum !=5)continue
            if (i.day == "Saturday" && daynum !=6)continue
            if (i.day == "Sunday" && daynum !=7)continue

            sectime1=timetomin(i.from.toString())
            sectime2=timetomin(i.too.toString())

            if(sectime1>time1&&sectime1<time2) {
                for(j in emptyrooms)
                {
                    if(i.room == j.number)
                    {
                        emptyrooms.remove(j)
                        break;
                    }

                }

            }
            if(sectime2>time1&&sectime2<time2){
                for(j in emptyrooms)
                {
                    if(i.room == j.number)
                    {
                        emptyrooms.remove(j)
                        break;
                    }

                }

            }

            if(sectime1<time1&&sectime2>time2){
                for(j in emptyrooms)
                {
                    if(i.room == j.number)
                    {
                        emptyrooms.remove(j)
                        break;
                    }

                }

            }




        }
        var list: ListView = myDialog3!!.findViewById(R.id.list)
        val adapter = RoomAdapter(
            this,
            emptyrooms
        )
        list.adapter = adapter









        myDialog3!!.show()
    }


    open var list_of_items = arrayOf("Понеделник", "Вторник", "Сряда", "Четвъртък", "Петък", "Събота", "Неделя")
    private fun chooseDay() {
        myDialog3!!.setContentView(R.layout.dial_choose_weekday)
        val txtclose: TextView = myDialog3!!.findViewById(R.id.txtclose)
        txtclose.text = "X"

        txtclose.setOnClickListener {
            myDialog3!!.dismiss()
        }

        val spinner: Spinner = myDialog3!!.findViewById(R.id.spinner)
        spinner.onItemSelectedListener = this

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, R.layout.my_spinner, list_of_items)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner.adapter = aa

        val btn: Button = myDialog3!!.findViewById(R.id.button2)
        btn.setOnClickListener {
            myDialog3!!.dismiss()
        }
        myDialog3!!.show()
    }


    fun ChooseClasses() {
        var myDialog3: Dialog? = Dialog(this)
        myDialog3!!.setContentView(R.layout.dial_choose_class)
        val txtclose: TextView = myDialog3.findViewById(R.id.txtclose)
        txtclose.text = "X"
        txtclose.setOnClickListener {
            myDialog3.dismiss()
        }

        val b5a: Button = myDialog3.findViewById(R.id.b5а)
        b5a.setOnClickListener {
            val intent = Intent(this, ClassSchedule2::class.java)
            intent.putExtra("pos", "5а")
            this.startActivity(intent)
        }

        val b5b: Button = myDialog3.findViewById(R.id.b5b)
        b5b.setOnClickListener {
            val intent = Intent(this, ClassSchedule2::class.java)
            intent.putExtra("pos", "5б")
            this.startActivity(intent)
        }

        val b5v: Button = myDialog3.findViewById(R.id.b5v)
        b5v.setOnClickListener {
            val intent = Intent(this, ClassSchedule2::class.java)
            intent.putExtra("pos", "5в")
            this.startActivity(intent)
        }

        val b6a: Button = myDialog3.findViewById(R.id.b6a)
        b6a.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "6а")
            this.startActivity(intent)
        }
        val b6b: Button = myDialog3.findViewById(R.id.b6b)
        b6b.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "6б")
            this.startActivity(intent)
        }
        val b6v: Button = myDialog3.findViewById(R.id.b6v)
        b6v.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "6в")
            this.startActivity(intent)
        }

        val b7a: Button = myDialog3.findViewById(R.id.b7a)
        b7a.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "7а")
            this.startActivity(intent)
        }
        val b7b: Button = myDialog3.findViewById(R.id.b7b)
        b7b.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "7б")
            this.startActivity(intent)
        }

        val b8a: Button = myDialog3.findViewById(R.id.b8a)
        b8a.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "8а")
            this.startActivity(intent)
        }
        val b8b: Button = myDialog3.findViewById(R.id.b8b)
        b8b.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "8б")
            this.startActivity(intent)
        }
        val b8v: Button = myDialog3.findViewById(R.id.b8v)
        b8v.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "8в")
            this.startActivity(intent)
        }
        val b8g: Button = myDialog3.findViewById(R.id.b8g)
        b8g.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "8г")
            this.startActivity(intent)
        }
        val b8d: Button = myDialog3.findViewById(R.id.b8d)
        b8d.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "8д")
            this.startActivity(intent)
        }
        val b8e: Button = myDialog3.findViewById(R.id.b8e)
        b8e.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "8е")
            this.startActivity(intent)
        }
        val b8j: Button = myDialog3.findViewById(R.id.b8j)
        b8j.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "8ж")
            this.startActivity(intent)
        }

        val b9a: Button = myDialog3.findViewById(R.id.b9a)
        b9a.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "9а")
            this.startActivity(intent)
        }
        val b9b: Button = myDialog3.findViewById(R.id.b9b)
        b9b.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "9б")
            this.startActivity(intent)
        }
        val b9v: Button = myDialog3.findViewById(R.id.b9v)
        b9v.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "9в")
            this.startActivity(intent)
        }
        val b9g: Button = myDialog3.findViewById(R.id.b9g)
        b9g.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "9г")
            this.startActivity(intent)
        }
        val b9d: Button = myDialog3.findViewById(R.id.b9d)
        b9d.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "9д")
            this.startActivity(intent)
        }
        val b9e: Button = myDialog3.findViewById(R.id.b9e)
        b9e.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "9е")
            this.startActivity(intent)
        }
        val b9j: Button = myDialog3.findViewById(R.id.b9j)
        b9j.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "9ж")
            this.startActivity(intent)
        }

        val b10a: Button = myDialog3.findViewById(R.id.b10a)
        b10a.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "10а")
            this.startActivity(intent)
        }
        val b10b: Button = myDialog3.findViewById(R.id.b10b)
        b10b.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "10б")
            this.startActivity(intent)
        }
        val b10v: Button = myDialog3.findViewById(R.id.b10v)
        b10v.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "10в")
            this.startActivity(intent)
        }
        val b10g: Button = myDialog3.findViewById(R.id.b10g)
        b10g.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "10г")
            this.startActivity(intent)
        }
        val b10d: Button = myDialog3.findViewById(R.id.b10d)
        b10d.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "10д")
            this.startActivity(intent)
        }
        val b10e: Button = myDialog3.findViewById(R.id.b10e)
        b10e.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "10е")
            this.startActivity(intent)
        }
        val b10j: Button = myDialog3.findViewById(R.id.b10j)
        b10j.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "10ж")
            this.startActivity(intent)
        }
        val b10z: Button = myDialog3.findViewById(R.id.b10z)
        b10z.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "10з")
            this.startActivity(intent)
        }

        val b11a: Button = myDialog3.findViewById(R.id.b11a)
        b11a.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "11а")
            this.startActivity(intent)
        }
        val b11b: Button = myDialog3.findViewById(R.id.b11b)
        b11b.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "11б")
            this.startActivity(intent)
        }
        val b11v: Button = myDialog3.findViewById(R.id.b11v)
        b11v.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "11в")
            this.startActivity(intent)
        }
        val b11g: Button = myDialog3.findViewById(R.id.b11g)
        b11g.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "11г")
            this.startActivity(intent)
        }
        val b11d: Button = myDialog3.findViewById(R.id.b11d)
        b11d.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "11д")
            this.startActivity(intent)
        }
        val b11e: Button = myDialog3.findViewById(R.id.b11e)
        b11e.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "11е")
            this.startActivity(intent)
        }
        val b11j: Button = myDialog3.findViewById(R.id.b11j)
        b11j.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "11ж")
            this.startActivity(intent)
        }
        val b11z: Button = myDialog3.findViewById(R.id.b11z)
        b11z.setOnClickListener {
            val intent = Intent(this, ClassSchedule::class.java)
            intent.putExtra("pos", "11з")
            this.startActivity(intent)
        }

        val b12a: Button = myDialog3.findViewById(R.id.b12a)
        b12a.setOnClickListener {
            val intent = Intent(this, ClassSchedule2::class.java)
            intent.putExtra("pos", "12а")
            this.startActivity(intent)
        }
        val b12b: Button = myDialog3.findViewById(R.id.b12b)
        b12b.setOnClickListener {
            val intent = Intent(this, ClassSchedule2::class.java)
            intent.putExtra("pos", "12б")
            this.startActivity(intent)
        }
        val b12v: Button = myDialog3.findViewById(R.id.b12v)
        b12v.setOnClickListener {
            val intent = Intent(this, ClassSchedule2::class.java)
            intent.putExtra("pos", "12в")
            this.startActivity(intent)
        }
        val b12g: Button = myDialog3.findViewById(R.id.b12g)
        b12g.setOnClickListener {
            val intent = Intent(this, ClassSchedule2::class.java)
            intent.putExtra("pos", "12г")
            this.startActivity(intent)
        }
        val b12d: Button = myDialog3.findViewById(R.id.b12d)
        b12d.setOnClickListener {
            val intent = Intent(this, ClassSchedule2::class.java)
            intent.putExtra("pos", "12д")
            this.startActivity(intent)
        }
        val b12e: Button = myDialog3.findViewById(R.id.b12e)
        b12e.setOnClickListener {
            val intent = Intent(this, ClassSchedule2::class.java)
            intent.putExtra("pos", "12е")
            this.startActivity(intent)
        }
        val b12j: Button = myDialog3.findViewById(R.id.b12j)
        b12j.setOnClickListener {
            val intent = Intent(this, ClassSchedule2::class.java)
            intent.putExtra("pos", "12ж")
            this.startActivity(intent)
        }
        val b12z: Button = myDialog3.findViewById(R.id.b12z)
        b12z.setOnClickListener {
            val intent = Intent(this, ClassSchedule2::class.java)
            intent.putExtra("pos", "12з")
            this.startActivity(intent)
        }

        myDialog3.show()
    }

    private fun DoklProblem() {
        //dialog for submitting problem
        myDialog!!.setContentView(R.layout.dial_problem_view)
        val txtclose: TextView = myDialog!!.findViewById(R.id.txtclose)
        val btn: Button = myDialog!!.findViewById(R.id.btnproblem)
        txtclose.text = "X"
        val textt: TextInputEditText = myDialog!!.findViewById(R.id.Text)
        txtclose.setOnClickListener {
            myDialog!!.dismiss()
        }
        database2 = FirebaseDatabase.getInstance().reference

        btn.setOnClickListener {

            if (textt.text!!.length == 0) {
                Toast.makeText(this, "Моля, опишете проблема си - обстойно, за да бъде поправен.", Toast.LENGTH_LONG)
                    .show()
            } else {
                val key = database2.child("Problem").push().key
                database2.child("Problems").child(key!!).child("Info").setValue(textt.text.toString())
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

    private fun ShowInfo() {
        myDialog!!.setContentView(R.layout.dial_infotab)
        var brclicks = 0
        val infotitle: TextView = myDialog!!.findViewById(R.id.infotitle)
        infotitle.setOnClickListener {
            brclicks++
            if (brclicks == 10) {
                Toast.makeText(this, "Вече сте админ.", Toast.LENGTH_LONG).show()
                prefs!!.isadmin = "1"
                acc.isadmin = "1"
                database2 = FirebaseDatabase.getInstance().reference
                database2.child("Users").child(acc.id!!).child("isadmin").setValue("1")
            }
        }


        val txtclose: TextView = myDialog!!.findViewById(R.id.txtclose)
        txtclose.text = "X"
        txtclose.setOnClickListener {
            myDialog!!.dismiss()
        }
        myDialog!!.show()
    }

    override fun update(p0: Observable?, p1: Any?) {
        teachers = TeacherModel.getData()!!
        users = AccountModel.getDataAccounts()!!
        classes = ClassesModel.getData()!!
        rooms = RoomModel.getData()!!
    }


}