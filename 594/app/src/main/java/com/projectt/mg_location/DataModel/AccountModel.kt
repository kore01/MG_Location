package com.projectt.mg_location.DataModel

import android.util.Log
import com.google.firebase.database.*
import java.util.*

object AccountModel : Observable(){
    private var mValueDataListener: ValueEventListener? = null
    private var mAccountsList: ArrayList<Account>? = ArrayList()

    private fun getDatabaseRef(): DatabaseReference?
    {

        return FirebaseDatabase.getInstance().reference.child("Users")
    }

    init
    {
        // FirebaseApp.initializeApp(this);
        if (mValueDataListener != null)
        {

            getDatabaseRef()?.removeEventListener(mValueDataListener!!)

        }
        mValueDataListener = null
        // Log.i("AccountModel", "data init line 28");
        mValueDataListener = object : ValueEventListener
        {
            override fun onDataChange(datasnapshot: DataSnapshot)
            {
                try
                {
                    Log.i("AccountModel", "data updated line 34")
                    val data: ArrayList<Account> = ArrayList()
                    //  Log.i("there is an update ", mAccountsList!!.size.toString())
                    for (snapshot: DataSnapshot in datasnapshot.children)
                    {
                        try
                        {
                            data.add(Account(snapshot))

                        } catch (e: Exception)
                        {
                            e.printStackTrace()
                        }

                    }
                    mAccountsList = data
                    Log.i("AccountsModel", "data updated, there are " + mAccountsList?.size + " in the list")
                    //Log.i("there is an update ", mAccountsList!!.size.toString())
                    for(a in mAccountsList!!)
                    {
                        //Log.i("AccountsModel", "Subjects: " + a.subject1 + " "+a.number);
                    }
                    //Log.i("there is an update ", mAccountsList!!.size.toString())
                    AccountModel.setChanged()
                    Log.i("there is an update ", mAccountsList!!.size.toString())
                    AccountModel.notifyObservers()
                } catch (e: Exception)
                {
                    e.printStackTrace()

                }
            }

            override fun onCancelled(p0: DatabaseError)
            {

            }

        }
        getDatabaseRef()?.addValueEventListener(mValueDataListener as ValueEventListener)
    }

    fun getDataAccounts(): ArrayList<Account>?
    {
        return mAccountsList
    }
}

