package com.projectt.mg_location.Adapters

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.projectt.mg_location.DataModel.RoomProblem
import com.projectt.mg_location.DataModel.Rooms
import com.projectt.mg_location.R

class RoomAdapter (private var activity: Activity, private var items: ArrayList<Rooms>) : BaseAdapter() {

    private class ViewHolder(row: View?) {
        var room: TextView? = null

        init {
            this.room = row?.findViewById<TextView>(R.id.problem)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            view = inflater.inflate(R.layout.problem_card_item, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var tea = items[position]
        Log.i("here", tea.info)

        viewHolder.room?.text = tea.number
        return view
    }

    override fun getItem(i: Int): Rooms {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}