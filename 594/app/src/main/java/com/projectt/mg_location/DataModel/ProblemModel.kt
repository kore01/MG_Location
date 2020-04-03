package com.projectt.mg_location.DataModel

import android.util.Log
import com.google.firebase.database.*
import java.util.*

object ProblemModel : Observable()
{
    private var mValueDataListener: ValueEventListener? = null
    private var mProblemList: ArrayList<Problem>? = ArrayList()

    private fun getDatabaseRef(): DatabaseReference?
    {
        return FirebaseDatabase.getInstance().reference.child("Problems")
    }

    init
    {
        if (mValueDataListener != null)
        {

            getDatabaseRef()?.removeEventListener(mValueDataListener!!)

        }
        mValueDataListener = null
        Log.i("ProblemModel", "data init line 28")
        mValueDataListener = object : ValueEventListener
        {
            override fun onDataChange(datasnapshot: DataSnapshot)
            {
                try
                {
                    Log.i("ProblemModel", "data updated line 34")
                    val data: ArrayList<Problem> = ArrayList()

                    if (datasnapshot != null)
                    {
                        for (snapshot: DataSnapshot in datasnapshot.children)
                        {
                            try
                            {
                                data.add(Problem(snapshot))

                            } catch (e: Exception)
                            {
                                e.printStackTrace()
                            }

                        }
                    }

                    mProblemList = data
                    Log.i("ProblemModel", "data updated, there are " + mProblemList?.size + " in the list");

                    ProblemModel.setChanged()
                    ProblemModel.notifyObservers()
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

    fun getData(): ArrayList<Problem>?
    {
        Log.i("ProblemModel", "data updated, there are " + mProblemList?.size + " in the list")
        return mProblemList
    }

    fun getSize() : Int
    {
        return mProblemList!!.size
    }

}