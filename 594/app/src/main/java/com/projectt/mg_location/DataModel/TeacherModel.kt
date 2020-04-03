package com.projectt.mg_location.DataModel

import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

object TeacherModel : Observable()
{
    private var mValueDataListener: ValueEventListener? = null
    private var mTeachersList: ArrayList<Teachers>? = ArrayList()

    private fun getDatabaseRef(): DatabaseReference?
    {
        return FirebaseDatabase.getInstance().reference.child("Teachers")
    }

    init
    {
        if (mValueDataListener != null)
        {

            getDatabaseRef()?.removeEventListener(mValueDataListener!!)

        }
        mValueDataListener = null
        Log.i("TeacherModel", "data init line 28")
        mValueDataListener = object : ValueEventListener
        {
            override fun onDataChange(datasnapshot: DataSnapshot)
            {
                try
                {
                    Log.i("TeacherModel", "data updated line 34")
                    val data: ArrayList<Teachers> = ArrayList()

                    if (datasnapshot != null)
                    {
                        for (snapshot: DataSnapshot in datasnapshot.children)
                        {
                            try
                            {
                                data.add(Teachers(snapshot))

                            } catch (e: Exception)
                            {
                                e.printStackTrace()
                            }

                        }
                    }
                    mTeachersList = data
                    Log.i("TeachersModel", "data updated, there are " + mTeachersList?.size + " in the list")
                    for(a in mTeachersList!!)
                    {
                        Log.i("TeachersModel", "Subjects: " + a.subject1 + " "+a.number)
                    }
                    setChanged()
                    notifyObservers()
                } catch (e: Exception)
                {
                    e.printStackTrace()

                }
            }

            override fun onCancelled(p0: DatabaseError)
            {
                if (p0 != null)
                {
                    Log.i("TeachersModel", "line 70 Data update cancelled, err=${p0.message}")
                }
            }

        }
        getDatabaseRef()?.addValueEventListener(mValueDataListener as ValueEventListener)
    }

    fun getData(): ArrayList<Teachers>?
    {
        return mTeachersList
    }
}