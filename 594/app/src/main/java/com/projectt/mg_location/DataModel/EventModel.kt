package com.projectt.mg_location.DataModel

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import java.util.*

object EventModel : Observable(){
    private var mValueDataListener: ValueEventListener? = null
    private var mEventsList: ArrayList<Event>? = ArrayList()

    private fun getDatabaseRef(): DatabaseReference?
    {

        return FirebaseDatabase.getInstance().reference.child("Events")
    }

    init
    {
       // FirebaseApp.initializeApp(this);
        if (mValueDataListener != null)
        {

            getDatabaseRef()?.removeEventListener(mValueDataListener!!)

        }
        mValueDataListener = null
        // Log.i("EventModel", "data init line 28");
        mValueDataListener = object : ValueEventListener
        {
            override fun onDataChange(datasnapshot: DataSnapshot)
            {
                try
                {
                    Log.i("EventModel", "data updated line 34")
                    val data: ArrayList<Event> = ArrayList()
                    //  Log.i("there is an update ", mEventsList!!.size.toString())
                    if (datasnapshot != null)
                    {
                        for (snapshot: DataSnapshot in datasnapshot.children)
                        {
                            try
                            {
                                data.add(Event(snapshot))

                            } catch (e: Exception)
                            {
                                e.printStackTrace()
                            }

                        }
                    }
                    mEventsList = data
                    Log.i("EventsModel", "data updated, there are " + mEventsList?.size + " in the list")
                    //Log.i("there is an update ", mEventsList!!.size.toString())
                    for(a in mEventsList!!)
                    {
                        //Log.i("EventsModel", "Subjects: " + a.subject1 + " "+a.number);
                    }
                    //Log.i("there is an update ", mEventsList!!.size.toString())
                    EventModel.setChanged()
                    Log.i("there is an update ", mEventsList!!.size.toString())
                    EventModel.notifyObservers()
                } catch (e: Exception)
                {
                    e.printStackTrace()

                }
            }

            override fun onCancelled(p0: DatabaseError)
            {
                if (p0 != null)
                {
                    Log.i("EventsModel", "line 70 Data update cancelled, err=${p0.message}")
                }
            }

        }
        getDatabaseRef()?.addValueEventListener(mValueDataListener as ValueEventListener)
    }

    fun getDataEvents(): ArrayList<Event>?
    {


        return mEventsList
    }
}

