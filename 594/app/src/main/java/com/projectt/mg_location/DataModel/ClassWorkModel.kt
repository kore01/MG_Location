package com.projectt.mg_location.DataModel

import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

object ClassWorkModel : Observable()
{
    private var mValueDataListener: ValueEventListener? = null
    private var mClasssList: ArrayList<ClassWork>? = ArrayList()

    private fun getDatabaseRef(): DatabaseReference?
    {
        return FirebaseDatabase.getInstance().reference.child("ClassWork")
    }

    init
    {
        if (mValueDataListener != null)
        {

            getDatabaseRef()?.removeEventListener(mValueDataListener!!)

        }
        mValueDataListener = null
        Log.i("ClassModel", "data init line 28")
        mValueDataListener = object : ValueEventListener
        {
            override fun onDataChange(datasnapshot: DataSnapshot)
            {
                try
                {
                    Log.i("ClassModel", "data updated line 34")
                    val data: ArrayList<ClassWork> = ArrayList()

                    if (datasnapshot != null)
                    {
                        for (snapshot: DataSnapshot in datasnapshot.children)
                        {
                            try
                            {
                                data.add(ClassWork(snapshot))

                            } catch (e: Exception)
                            {
                                e.printStackTrace()
                            }

                        }
                    }

                    mClasssList = data
                    Log.i("ClasssModel", "data updated, there are " + mClasssList?.size + " in the list")

                    setChanged()
                    notifyObservers()
                }

                catch (e: Exception)
                {
                    e.printStackTrace()

                }
            }

            override fun onCancelled(p0: DatabaseError)
            {
                if (p0 != null)
                {
                    Log.i("RoomssModel", "line 70 Data update cancelled, err=${p0.message}")
                }
            }

        }
        getDatabaseRef()?.addValueEventListener(mValueDataListener as ValueEventListener)
    }

    fun getData(): ArrayList<ClassWork>?
    {
        Log.i("RoomsModel", "data updated, there are " + mClasssList?.size + " in the list")
        return mClasssList
    }
}