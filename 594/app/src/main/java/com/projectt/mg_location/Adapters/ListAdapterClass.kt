package com.projectt.mg_location.Adapters

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.projectt.mg_location.DataModel.Problem
import com.projectt.mg_location.DataModel.Teachers
import com.projectt.mg_location.R

class ListAdapterClass(private var activity: Activity, private var items: ArrayList<Problem>) : BaseAdapter() {

    private class ViewHolder(row: View?) {
        var problem: TextView? = null

        init {

            this.problem = row?.findViewById<TextView>(R.id.problem)
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

        viewHolder.problem?.text = tea.info
        return view
    }

    override fun getItem(i: Int): Problem {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}