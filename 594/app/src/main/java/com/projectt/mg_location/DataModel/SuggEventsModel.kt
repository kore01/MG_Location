package com.projectt.mg_location.DataModel

import android.util.Log
import com.google.firebase.database.*
import java.util.*

object SuggEventsModel : Observable()
{
    private var mValueDataListener: ValueEventListener? = null
    private var mSuggEventList: ArrayList<SuggEvent>? = ArrayList()

    private fun getDatabaseRef(): DatabaseReference?
    {
        return FirebaseDatabase.getInstance().reference.child("SuggEvents")
    }

    init
    {
        if (mValueDataListener != null)
        {
            getDatabaseRef()?.removeEventListener(mValueDataListener!!)
        }
        mValueDataListener = null
        Log.i("SuggEventsModel", "data init line 28")
        mValueDataListener = object : ValueEventListener
        {
            override fun onDataChange(datasnapshot: DataSnapshot)
            {
                try
                {
                    Log.i("SuggEventsModel", "data updated line 34")
                    val data: ArrayList<SuggEvent> = ArrayList()

                    if (datasnapshot != null)
                    {
                        for (snapshot: DataSnapshot in datasnapshot.children)
                        {
                            try
                            {
                                data.add(SuggEvent(snapshot))

                            } catch (e: Exception)
                            {
                                e.printStackTrace()
                            }

                        }
                    }

                    mSuggEventList = data
                    //Log.i("RoomsModel", "data updated, there are " + mRoomsList?.size + " in the list")
                    for(a in mSuggEventList!!)
                    {
                       // Log.i("RoomsModel", "Rooms: " + a.number + " "+a.type)
                    }
                    SuggEventsModel.setChanged()
                    SuggEventsModel.notifyObservers()
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
                    Log.i("SuggModel", "line 70 Data update cancelled, err=${p0.message}")
                }
            }

        }
        getDatabaseRef()?.addValueEventListener(mValueDataListener as ValueEventListener)
    }

    fun getData(): ArrayList<SuggEvent>?
    {
        Log.i("SuggModel", "data updated, there are " + mSuggEventList?.size + " in the list")
        return mSuggEventList
    }
}