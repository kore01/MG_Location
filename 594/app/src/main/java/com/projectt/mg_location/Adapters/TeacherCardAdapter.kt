package com.projectt.mg_location.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.projectt.mg_location.DataModel.Teachers
import com.projectt.mg_location.R


class TeacherCardAdapter(context: Context, resource: Int, list: ArrayList<Teachers>) : ArrayAdapter<Teachers>(context, resource, list)
{
    private var mResource: Int = 0
    private var mList: ArrayList<Teachers>
    private var mLayoutInflator: LayoutInflater
    private var mContext: Context = context

    init
    {
        this.mResource = resource
        this.mList = list
        this.mLayoutInflator = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View?
    {
        val returnView: View?
        if (convertView == null)
        {
            returnView = try
            {
                mLayoutInflator.inflate(mResource, null)
            } catch (e: Exception)
            {
                e.printStackTrace()
                View(context)
            }
            setUI(returnView, position)
            return returnView
        }
        setUI(convertView, position)
        return convertView
    }
    private fun setUI (view:View, position: Int)
    {
        val teachers: Teachers? = if(count>position)getItem(position) else null
        val teacherNamee: TextView? = view.findViewById(R.id.teachers_card_name)
        teacherNamee?.text = teachers?.name ?: ""

        val teacherNumb: TextView? = view.findViewById(R.id.teachers_card_numb)
        teacherNumb?.text = teachers?.number ?: ""
        teacherNumb?.text = teacherNumb?.text.toString() + '.'

        val teacherSubjectt: TextView? = view.findViewById(R.id.teachers_card_subject)
        teacherSubjectt?.text = teachers?.subject1 ?: ""
    }
}