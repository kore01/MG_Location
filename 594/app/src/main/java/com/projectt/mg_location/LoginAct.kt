package com.projectt.mg_location

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.projectt.mg_location.DataModel.*
import java.util.*

open class LoginAct : AppCompatActivity(), Observer {

    override fun update(o: Observable?, arg: Any?) {
        teachers = TeacherModel.getData()!!
        users = AccountModel.getDataAccounts()!!
    }

    private var etFirstName: EditText? = null
    private var etLastName: EditText? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnCreateAccount: Button? = null
    private var mProgressBar: ProgressDialog? = null


    lateinit var teachers: List<Teachers>
    lateinit var teachers2: List<Teachers>
    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    private lateinit var firebaseAuth: FirebaseAuth

    //info for the acc
    var acc: Account1 = Account1()
    lateinit var database2: DatabaseReference

    private val TAG = "CreateAccountActivity"
    private var email: String? = null
    private var password: String? = null

    //information of previous enters
    var prefs: Prefs? = null

    var myLogDialog: Dialog? = null
    fun ShowLogins() {
        firebaseAuth = FirebaseAuth.getInstance()
        val txtclose: TextView
        myLogDialog!!.setContentView(R.layout.dial_logintab)
        txtclose = myLogDialog!!.findViewById(R.id.txtclose)
        txtclose.text = "X"
        txtclose.setOnClickListener {

            myLogDialog!!.dismiss()
        }
        etEmail = myLogDialog!!.findViewById(R.id.et_email)
        etPassword = myLogDialog!!.findViewById(R.id.et_password)
        btnCreateAccount = myLogDialog!!.findViewById(R.id.btn_register)
        mProgressBar = ProgressDialog(this)
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()


        val btn: Button = myLogDialog!!.findViewById(R.id.google_sign_in_button)
        btn.setOnClickListener {
            myLogDialog!!.dismiss()
            signIn()
        }

        val btn2: Button = myLogDialog!!.findViewById(R.id.btn_register)
        btn2.setOnClickListener {
            myLogDialog!!.dismiss()
            createNewAccount()
        }
        configureGoogleSignIn()
        firebaseAuth = FirebaseAuth.getInstance()
        myLogDialog!!.show()
    }

    fun makeacc2() {
        prefs!!.myclass = acc.myclass!!
        prefs!!.notifications = true
        database2 = FirebaseDatabase.getInstance().reference
        database2.child("Users").child(acc.id!!).child("myclass").setValue(acc.myclass)
    }

    val RC_SIGN_IN: Int = 1
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions

    private fun configureGoogleSignIn() {
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Входът не беше успешен. :(", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                findtheclass()
            } else {
                Toast.makeText(this, "Входът не беше успешен. :(", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    private var firebaseData = FirebaseDatabase.getInstance().reference
    fun makeacc() {
        val key = firebaseData.child("Users").push().key
        acc.id = key
        acc.isadmin = "0"
        prefs!!.type = acc.type!!
        prefs!!.Acc_ID = key!!
        prefs!!.myclass = acc.myclass!!
        prefs!!.isadmin = acc.isadmin!!
        prefs!!.notifications = true
        firebaseData.child("Users").child(key).setValue(acc)
        //ChangeAcc(this)
    }

    var users: ArrayList<Account> = AccountModel.getDataAccounts()!!

    fun findtheclass() {
        var email: String = ""
        val user = mAuth!!.currentUser
        if (user != null) {
            email = user.email!!
        } else return
        for (i in users) {
            if (i.email == email) {
                acc.email = i.email
                acc.myclass = i.myclass
                acc.type = i.type
                acc.id = i.id
                prefs!!.type = i.type!!
                prefs!!.Acc_ID = i.id!!
                prefs!!.myclass = i.myclass!!
                prefs!!.isadmin = i.isadmin!!
                prefs!!.notifications = true
                acc.isadmin = i.isadmin
                return
            }
        }

        var string: String = ""
        var clas: Int = 0


        if (email.length > 11) {
            string =
                email[email.length - 11].toString() + email[email.length - 10] + email[email.length - 9] + email[email.length - 8] + email[email.length - 7] + email[email.length - 6] + email[email.length - 5] + email[email.length - 4] + email[email.length - 3] + email[email.length - 2] + email[email.length - 1]

            if (string == "mgberon.com") {
                string =
                    email[email.length - 15].toString() + email[email.length - 14].toString() + email[email.length - 13].toString()
                if (string[0] == '1' || string[0] == '2') {
                    clas = digitt(string[0]) * 10 + digitt(string[1])
                    clas = 12 - (clas - 19)
                    if (string[2] == 'a') string = clas.toString() + 'а'
                    else if (string[2] == 'b') string = clas.toString() + 'б'
                    else if (string[2] == 'v') string = clas.toString() + 'в'
                    else if (string[2] == 'g') string = clas.toString() + 'г'
                    else if (string[2] == 'd') string = clas.toString() + 'д'
                    else if (string[2] == 'e') string = clas.toString() + 'е'
                    else if (string[2] == 'v') string = clas.toString() + 'ж'
                    else if (string[2] == 'z') string = clas.toString() + 'з'

                    val a: Account1 = Account1()
                    a.email = email
                    a.type = "1"
                    a.isadmin = "0"
                    a.myclass = string

                    val key = firebaseData.child("Users").push().key
                    a.id = key
                    prefs!!.Acc_ID = key!!
                    prefs!!.myclass = a.myclass!!
                    prefs!!.isadmin = a.isadmin!!
                    prefs!!.notifications = true
                    firebaseData.child("Users").child(key).setValue(a)
                    acc = a
                    return
                } else {
                    var name: String = ""
                    var famname: String = ""
                    var pos: Int = 0
                    var namepos: Boolean = false
                    email = "1" + email
                    while (pos < email.length) {
                        if (pos == 0) {
                            pos++
                            continue
                        }
                        if (email[pos] == '.') {
                            pos += 2
                            namepos = true
                            continue
                        }
                        if (email[pos] == '@') break

                        if (email[pos] == 'j') {
                            if (email[pos + 1] == 'u') {

                                if (namepos == false) {
                                    name += 'ю'
                                }
                                if (namepos == true) {
                                    famname += 'ю'
                                }
                                pos += 2
                                continue
                            }
                        }
                        if (email[pos] == 'd') {
                            if (email[pos + 1] == 'j') {

                                if (namepos == false) {

                                    name += "дж"
                                }
                                if (namepos) {
                                    famname += "дж"
                                }
                                pos += 2
                                continue
                            }
                        }
                        if (email[pos] == 'i') {
                            if (email[pos + 1] == 'a') {

                                if (namepos == false) {
                                    name += "я"
                                }
                                if (namepos == true) {
                                    famname += "я"
                                }
                                pos += 2
                                continue

                            }
                        }
                        if (email[pos] == 'j') {
                            if (email[pos + 1] == 'u') {

                                if (!namepos) {

                                    name += "ю"

                                }
                                if (namepos) {
                                    famname += "ю"
                                }
                                pos += 2
                                continue

                            }
                        }
                        if (email[pos] == 's') {
                            if (email[pos + 1] == 't') {

                                if (namepos == false) {

                                    name += "щ"
                                }
                                if (namepos == true) {
                                    famname += "щ"
                                }
                                pos += 2
                                continue

                            }
                        }
                        if (email[pos] == 's') {
                            if (email[pos + 1] == 'h') {

                                if (namepos == false) {

                                    name += "ш"

                                }
                                if (namepos == true) {
                                    famname += "ш"
                                }
                                pos += 2
                                continue
                            }
                        }
                        if (email[pos] == 'i') {
                            if (email[pos - 1] == 'o' || email[pos - 1] == 'a' || email[pos - 1] == 'e' || email[pos - 1] == 'u') {

                                if (namepos == false) {

                                    name += "й"

                                }
                                if (namepos == true) {
                                    famname += "й"
                                }
                                pos += 1
                                continue
                            }
                        }

                        if (namepos == false) {

                            name += letter(email[pos])

                        }
                        if (namepos == true) {
                            famname += letter(email[pos])
                        }
                        pos++
                    }
                    for (i in teachers) {
                        if (i.name!!.toLowerCase() == name && i.familyname!!.toLowerCase() == famname) {
                            acc.email = email
                            acc.type = "2"
                            acc.myclass = i.number
                            acc.isadmin = "0"
                            acc.id = i.id
                            prefs!!.type = "2"
                            prefs!!.Acc_ID = i.id!!
                            prefs!!.myclass = acc.myclass!!
                            prefs!!.isadmin = acc.isadmin!!
                            prefs!!.notifications = true


                            // ChangeAcc(this)
                            return
                        }
                    }
                }
            } else {
                acc.type = "4"
                acc.email = email
                ChooseTeachOrStud()
            }
        }

    }


    fun ChooseTeachOrStud() {
        var myDialog1: Dialog? = Dialog(this)
        val txtclose: TextView
        val btnFollow: Button
        Log.i("it should work", "mhm")
        myDialog1!!.setContentView(R.layout.dial_teachorstud)
        txtclose = myDialog1!!.findViewById(R.id.txtclose)
        txtclose.text = "X"
        //btnFollow = myDialog!!.findViewById(R.id.btnfollow)
        txtclose.setOnClickListener {
            myDialog1!!.dismiss()
        }
        val btnteach: Button = myDialog1!!.findViewById(R.id.da)
        btnteach.setOnClickListener {
            myDialog1.dismiss()
            prefs!!.type = "2"
            acc.type = "2"
            addCode()
        }

        val btnstud: Button = myDialog1!!.findViewById(R.id.ne)
        btnstud.setOnClickListener {
            myDialog1.dismiss()
            prefs!!.type = "1"
            acc.type = "1"
            ChooseClass()
        }



        myDialog1!!.show()
    }

    private fun addCode() {
        var myDialog5: Dialog? = Dialog(this)
        val txtclose: TextView
        val btnFollow: Button
        Log.i("it should work", "mhm")
        myDialog5!!.setContentView(R.layout.dial_add_code_teach)
        txtclose = myDialog5!!.findViewById(R.id.txtclose)
        txtclose.text = "X"
        //btnFollow = myDialog!!.findViewById(R.id.btnfollow)
        txtclose.setOnClickListener {
            myDialog5!!.dismiss()
        }

        val code: TextView = myDialog5!!.findViewById(R.id.editText)

        val btnsubmit: Button = myDialog5!!.findViewById(R.id.submit)
        btnsubmit.setOnClickListener {
            if (code.length() == 0) {
                Toast.makeText(this, "Моля, въведете код.", Toast.LENGTH_LONG).show()
            } else {

                for (i in teachers) {
                    Log.i("bullshit", i.code + " " + code.text)
                    if (code.text.toString() == i.code) {
                        Log.i("bullshit", "YEAAAAAAAAAAAAAAAAA")
                        acc.email = email
                        acc.type = "2"
                        acc.myclass = i.number
                        acc.isadmin = "0"
                        acc.id = i.id
                        prefs!!.type = "2"
                        prefs!!.Acc_ID = i.id!!
                        prefs!!.myclass = acc.myclass!!
                        prefs!!.isadmin = acc.isadmin!!
                        prefs!!.notifications = true
                        prefs!!.startdial = true
                        myDialog5.dismiss()
                        break
                    }
                }
            }
        }


        myDialog5!!.show()

    }


    private fun createNewAccount() {
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)
        ) {
            mProgressBar!!.setMessage("Регистриране на потребител...")
            mProgressBar!!.show()
            mAuth!!
                .createUserWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->
                    mProgressBar!!.hide()
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val userId = mAuth!!.currentUser!!.uid
                        //Verify Account
                        verifyEmail()
                        //update user dial_profile information

                        findtheclass()
                    } else {
                        mAuth!!.signInWithEmailAndPassword(email!!, password!!)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success")
                                    findtheclass()
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                    Toast.makeText(
                                        this, "Входът не беше успешен.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                // ...
                            }
                        // If sign in fails, display a message to the user.


                    }
                }
        } else {
            Toast.makeText(this, "Въведете всички детайли.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Беше изпратен верификационен имейл до " + mUser.email,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(
                        this,
                        "Неуспешно изпращане на верификационен имейл.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    fun digitt(a: Char): Int {
        if (a == '0') return 0
        if (a == '1') return 1
        if (a == '2') return 2
        if (a == '3') return 3
        if (a == '4') return 4
        if (a == '5') return 5
        if (a == '6') return 6
        if (a == '7') return 7
        if (a == '8') return 8
        if (a == '9') return 9
        return 0
    }


    fun ChooseClass() {
        var myDialog3: Dialog? = Dialog(this)
        val txtclose: TextView
        val btnFollow: Button
        Log.i("it should work", "mhm")
        myDialog3!!.setContentView(R.layout.dial_choose_class)
        txtclose = myDialog3!!.findViewById(R.id.txtclose)
        txtclose.text = "X"
        //btnFollow = myDialog!!.findViewById(R.id.btnfollow)
        txtclose.setOnClickListener {
            myDialog3!!.dismiss()
        }

        val b5а: Button = myDialog3!!.findViewById(R.id.b5а)
        b5а.setOnClickListener {
            acc.myclass = "5а"
            myDialog3!!.dismiss()
            makeacc()
        }

        val b5b: Button = myDialog3!!.findViewById(R.id.b5b)
        b5b.setOnClickListener {
            acc.myclass = "5б"
            myDialog3!!.dismiss()
            makeacc()
        }

        val b5v: Button = myDialog3!!.findViewById(R.id.b5v)
        b5v.setOnClickListener {
            acc.myclass = "5в"
            myDialog3!!.dismiss()
            makeacc()
        }

        val b6a: Button = myDialog3!!.findViewById(R.id.b6a)
        b6a.setOnClickListener {
            acc.myclass = "6а"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b6b: Button = myDialog3!!.findViewById(R.id.b6b)
        b6b.setOnClickListener {
            acc.myclass = "6б"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b6v: Button = myDialog3!!.findViewById(R.id.b6v)
        b6v.setOnClickListener {
            acc.myclass = "6в"
            myDialog3!!.dismiss()
            makeacc()
        }

        val b7a: Button = myDialog3!!.findViewById(R.id.b7a)
        b7a.setOnClickListener {
            acc.myclass = "7а"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b7b: Button = myDialog3!!.findViewById(R.id.b7b)
        b7b.setOnClickListener {
            acc.myclass = "7б"
            myDialog3!!.dismiss()
            makeacc()
        }

        val b8a: Button = myDialog3!!.findViewById(R.id.b8a)
        b8a.setOnClickListener {
            acc.myclass = "8а"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b8b: Button = myDialog3!!.findViewById(R.id.b8b)
        b8b.setOnClickListener {
            acc.myclass = "8б"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b8v: Button = myDialog3!!.findViewById(R.id.b8v)
        b8v.setOnClickListener {
            acc.myclass = "8в"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b8g: Button = myDialog3!!.findViewById(R.id.b8g)
        b8g.setOnClickListener {
            acc.myclass = "8г"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b8d: Button = myDialog3!!.findViewById(R.id.b8d)
        b8d.setOnClickListener {
            acc.myclass = "8д"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b8e: Button = myDialog3!!.findViewById(R.id.b8e)
        b8e.setOnClickListener {
            acc.myclass = "8е"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b8j: Button = myDialog3!!.findViewById(R.id.b8j)
        b8j.setOnClickListener {
            acc.myclass = "8ж"
            myDialog3!!.dismiss()
            makeacc()
        }


        val b9a: Button = myDialog3!!.findViewById(R.id.b9a)
        b9a.setOnClickListener {
            acc.myclass = "9а"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b9b: Button = myDialog3!!.findViewById(R.id.b9b)
        b9b.setOnClickListener {
            acc.myclass = "9б"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b9v: Button = myDialog3!!.findViewById(R.id.b9v)
        b9v.setOnClickListener {
            acc.myclass = "9в"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b9g: Button = myDialog3!!.findViewById(R.id.b9g)
        b9g.setOnClickListener {
            acc.myclass = "9г"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b9d: Button = myDialog3!!.findViewById(R.id.b9d)
        b9d.setOnClickListener {
            acc.myclass = "9д"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b9e: Button = myDialog3!!.findViewById(R.id.b9e)
        b9e.setOnClickListener {
            acc.myclass = "9е"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b9j: Button = myDialog3!!.findViewById(R.id.b9j)
        b9j.setOnClickListener {
            acc.myclass = "9ж"
            myDialog3!!.dismiss()
            makeacc()
        }


        val b10a: Button = myDialog3!!.findViewById(R.id.b10a)
        b10a.setOnClickListener {
            acc.myclass = "10а"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b10b: Button = myDialog3!!.findViewById(R.id.b10b)
        b10b.setOnClickListener {
            acc.myclass = "10б"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b10v: Button = myDialog3!!.findViewById(R.id.b10v)
        b10v.setOnClickListener {
            acc.myclass = "10в"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b10g: Button = myDialog3!!.findViewById(R.id.b10g)
        b10g.setOnClickListener {
            acc.myclass = "10г"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b10d: Button = myDialog3!!.findViewById(R.id.b10d)
        b10d.setOnClickListener {
            acc.myclass = "10ж"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b10e: Button = myDialog3!!.findViewById(R.id.b10e)
        b10e.setOnClickListener {
            acc.myclass = "10з"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b10j: Button = myDialog3!!.findViewById(R.id.b10j)
        b10j.setOnClickListener {
            acc.myclass = "10ж"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b10z: Button = myDialog3!!.findViewById(R.id.b10z)
        b10z.setOnClickListener {
            acc.myclass = "10з"
            myDialog3!!.dismiss()
            makeacc()
        }

        val b11a: Button = myDialog3!!.findViewById(R.id.b11a)
        b11a.setOnClickListener {
            acc.myclass = "11а"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b11b: Button = myDialog3!!.findViewById(R.id.b11b)
        b11b.setOnClickListener {
            acc.myclass = "11б"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b11v: Button = myDialog3!!.findViewById(R.id.b11v)
        b11v.setOnClickListener {
            acc.myclass = "11в"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b11g: Button = myDialog3!!.findViewById(R.id.b11g)
        b11g.setOnClickListener {
            acc.myclass = "11г"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b11d: Button = myDialog3!!.findViewById(R.id.b11d)
        b11d.setOnClickListener {
            acc.myclass = "11д"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b11e: Button = myDialog3!!.findViewById(R.id.b11e)
        b11e.setOnClickListener {
            acc.myclass = "11е"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b11j: Button = myDialog3!!.findViewById(R.id.b11j)
        b11j.setOnClickListener {
            acc.myclass = "11ж"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b11z: Button = myDialog3!!.findViewById(R.id.b11z)
        b11z.setOnClickListener {
            acc.myclass = "11з"
            myDialog3!!.dismiss()
            makeacc()
        }

        val b12a: Button = myDialog3!!.findViewById(R.id.b12a)
        b12a.setOnClickListener {
            acc.myclass = "12а"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b12b: Button = myDialog3!!.findViewById(R.id.b12b)
        b12b.setOnClickListener {
            acc.myclass = "12б"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b12v: Button = myDialog3!!.findViewById(R.id.b12v)
        b12v.setOnClickListener {
            acc.myclass = "12в"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b12g: Button = myDialog3!!.findViewById(R.id.b12g)
        b12g.setOnClickListener {
            acc.myclass = "12г"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b12d: Button = myDialog3!!.findViewById(R.id.b12d)
        b12d.setOnClickListener {
            acc.myclass = "12д"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b12e: Button = myDialog3!!.findViewById(R.id.b12e)
        b12e.setOnClickListener {
            acc.myclass = "12е"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b12j: Button = myDialog3!!.findViewById(R.id.b12j)
        b12j.setOnClickListener {
            acc.myclass = "12ж"
            myDialog3!!.dismiss()
            makeacc()
        }
        val b12z: Button = myDialog3!!.findViewById(R.id.b12z)
        b12z.setOnClickListener {
            acc.myclass = "12з"
            myDialog3!!.dismiss()
            makeacc()
        }

        myDialog3!!.show()
    }

    fun letter(ch: Char): Char {
        if (ch == 'a') return 'а'
        if (ch == 'b') return 'б'
        if (ch == 'v') return 'в'
        if (ch == 'g') return 'г'
        if (ch == 'd') return 'д'
        if (ch == 'e') return 'е'
        if (ch == 'v') return 'ж'
        if (ch == 'z') return 'з'
        if (ch == 'i') return 'и'
        if (ch == 'k') return 'к'
        if (ch == 'l') return 'л'
        if (ch == 'm') return 'м'
        if (ch == 'n') return 'н'
        if (ch == 'o') return 'о'
        if (ch == 'p') return 'п'
        if (ch == 'r') return 'р'
        if (ch == 's') return 'с'
        if (ch == 't') return 'т'
        if (ch == 'u') return 'у'
        if (ch == 'f') return 'ф'
        if (ch == 'h') return 'х'
        if (ch == 'c') return 'ц'
        return 'я'
    }

}