package com.projectt.mg_location.DataModel

import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

object RoomModel : Observable()
{
    private var mValueDataListener: ValueEventListener? = null
    private var mRoomsList: ArrayList<Rooms>? = ArrayList()

    private fun getDatabaseRef(): DatabaseReference?
    {
        return FirebaseDatabase.getInstance().reference.child("Rooms")
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
                    val data: ArrayList<Rooms> = ArrayList()

                    if (datasnapshot != null)
                    {
                        for (snapshot: DataSnapshot in datasnapshot.children)
                        {
                            try
                            {
                                data.add(Rooms(snapshot))

                            } catch (e: Exception)
                            {
                                e.printStackTrace()
                            }

                        }
                    }

                    mRoomsList = data
                    Log.i("RoomsModel", "data updated, there are " + mRoomsList?.size + " in the list")
                    for(a in mRoomsList!!)
                    {
                        Log.i("RoomsModel", "Rooms: " + a.number + " "+a.type)
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
                    Log.i("RoomssModel", "line 70 Data update cancelled, err=${p0.message}")
                }
            }

        }
        getDatabaseRef()?.addValueEventListener(mValueDataListener as ValueEventListener)
    }

    fun getData(): ArrayList<Rooms>?
    {
        Log.i("RoomsModel", "data updated, there are " + mRoomsList?.size + " in the list")
        return mRoomsList
    }
}