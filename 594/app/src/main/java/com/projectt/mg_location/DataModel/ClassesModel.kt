package com.projectt.mg_location.DataModel

import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

object ClassesModel : Observable()
{
    private var mValueDataListener: ValueEventListener? = null
    private var mClassesList: ArrayList<Classes>? = ArrayList()

    private fun getDatabaseRef(): DatabaseReference?
    {
        return FirebaseDatabase.getInstance().reference.child("Classes")
    }

    init
    {
        if (mValueDataListener != null)
        {

            getDatabaseRef()?.removeEventListener(mValueDataListener!!)

        }
        mValueDataListener = null
        Log.i("ClassesModel", "data init line 28")
        mValueDataListener = object : ValueEventListener
        {
            override fun onDataChange(datasnapshot: DataSnapshot)
            {
                try
                {
                    Log.i("ClassesModel", "data updated line 34")
                    val data: ArrayList<Classes> = ArrayList()

                    if (datasnapshot != null)
                    {
                        for (snapshot: DataSnapshot in datasnapshot.children)
                        {
                            try
                            {
                                data.add(Classes(snapshot))

                            } catch (e: Exception)
                            {
                                e.printStackTrace()
                            }

                        }
                    }

                    mClassesList = data
                    Log.i("ClassessModel", "data updated, there are " + mClassesList?.size + " in the list")

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

    fun getData(): ArrayList<Classes>?
    {
        Log.i("RoomsModel", "data updated, there are " + mClassesList?.size + " in the list")
        return mClassesList
    }

    fun getDataForClass(classss:String): List<Classes>
    {
        Log.i("RoomsModel", "data updated, there are " + mClassesList?.size + " in the list")
        var CL: List<Classes> = mClassesList!!.filter { s -> s.classs == classss }
        return CL
    }

    fun getDataForTeachers(teacher:String): List<Classes>
    {
        Log.i("RoomsModel", "data updated, there are " + mClassesList?.size + " in the list")
        var CL: List<Classes> = mClassesList!!.filter { s -> s.teachernumber == teacher }
        return CL
    }

    fun getDataForRoom(room:String): List<Classes>
    {
        Log.i("RoomsModel", "data updated, there are " + mClassesList?.size + " in the list")
        var CL: List<Classes> = mClassesList!!.filter { s -> s.room == room }
        return CL
    }


}