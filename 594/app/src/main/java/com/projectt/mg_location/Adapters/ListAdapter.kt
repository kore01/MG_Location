package com.projectt.mg_location.Adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.projectt.mg_location.DataModel.Teachers
import com.projectt.mg_location.R

class ListAdapter(private var activity: Activity, private var items: ArrayList<Teachers>) : BaseAdapter() {
    private class ViewHolder(row: View?) {
        var firstname: TextView? = null
        var numb: TextView? = null
        var subject: TextView? = null

        init {
            this.firstname = row?.findViewById<TextView>(R.id.teachers_card_name)
            this.numb = row?.findViewById<TextView>(R.id.teachers_card_numb)
            this.subject = row?.findViewById<TextView>(R.id.teachers_card_subject)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            view = inflater.inflate(R.layout.teachers_card_item, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var tea = items[position]
        viewHolder.firstname?.text = tea.name + " " + tea.familyname
        viewHolder.numb?.text = tea.number + ". "
        viewHolder.subject?.text = tea.subject1
        return view
    }

    override fun getItem(i: Int): Teachers {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}

