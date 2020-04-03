package com.projectt.mg_location.Adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.projectt.mg_location.DataModel.ExtraCur
import com.projectt.mg_location.DataModel.TeacherModel
import com.projectt.mg_location.DataModel.TeacherModel.getData
import com.projectt.mg_location.DataModel.Teachers
import com.projectt.mg_location.R
import java.util.*
import kotlin.collections.ArrayList

class ExtraAdapter(private var activity: Activity, private var items: ArrayList<ExtraCur>) : BaseAdapter(), Observer {
    override fun update(o: Observable?, arg: Any?) {

    }

    private class ViewHolder(row: View?) {
        var name: TextView? = null
        var time: TextView? = null
        var day: TextView? = null
        var place: TextView? = null
        var info: TextView? = null
        var teachern: TextView? = null

        init {
            this.name = row?.findViewById(R.id.Name)
            this.time = row?.findViewById(R.id.Time)
            this.day = row?.findViewById(R.id.Day)
            this.place = row?.findViewById(R.id.Place)
            this.info = row?.findViewById(R.id.Info)
            this.teachern = row?.findViewById(R.id.NameT)
        }
    }

    lateinit var teachers: ArrayList<Teachers>
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View

        val viewHolder: ViewHolder

        TeacherModel
        TeacherModel.addObserver(this)
        teachers= getData()!!
        if (convertView == null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            view = inflater.inflate(R.layout.extra_cur_item, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var tea = items[position]
        viewHolder.name?.text = tea.name
        viewHolder.time?.text = tea.from+"-"+tea.to
        viewHolder.day?.text = tea.day
        viewHolder.place?.text = tea.place
        viewHolder.info?.text = tea.info
        viewHolder.teachern?.text = tea.teacher

        return view
    }

    override fun getItem(i: Int): ExtraCur {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}

