package com.projectt.mg_location.DataModel

import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

object SuggExtra : Observable()
{
    private var mValueDataListener: ValueEventListener? = null
    private var mExtraCurList: ArrayList<ExtraCur>? = ArrayList()

    private fun getDatabaseRef(): DatabaseReference?
    {
        return FirebaseDatabase.getInstance().reference.child("SuggExtra")
    }

    init
    {
        if (mValueDataListener != null)
        {

            getDatabaseRef()?.removeEventListener(mValueDataListener!!)

        }
        mValueDataListener = null
        Log.i("RoomModel", "data init line 28")
        mValueDataListener = object : ValueEventListener
        {
            override fun onDataChange(datasnapshot: DataSnapshot)
            {
                try
                {
                    Log.i("RoomModel", "data updated line 34")
                    val data: ArrayList<ExtraCur> = ArrayList()

                    if (datasnapshot != null)
                    {
                        for (snapshot: DataSnapshot in datasnapshot.children)
                        {
                            try
                            {
                                data.add(ExtraCur(snapshot))

                            } catch (e: Exception)
                            {
                                e.printStackTrace()
                            }

                        }
                    }

                    mExtraCurList = data
                    Log.i("ExtraCurModel", "data updated, there are " + mExtraCurList?.size + " in the list")
                    for(a in mExtraCurList!!)
                    {
                        Log.i("ExtraCurModel", "ExtraCur: " + a.teacher + " "+a.name)
                    }
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
                    Log.i("ExtraCursModel", "line 70 Data update cancelled, err=${p0.message}")
                }
            }

        }
        getDatabaseRef()?.addValueEventListener(mValueDataListener as ValueEventListener)
    }

    fun getDataExtra(): ArrayList<ExtraCur>?
    {
        Log.i("ExtraCurModel", "data updated, there are " + mExtraCurList?.size + " in the list")
        return mExtraCurList
    }
}