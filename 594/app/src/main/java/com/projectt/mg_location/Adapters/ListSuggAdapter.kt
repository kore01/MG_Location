package com.projectt.mg_location.Adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.projectt.mg_location.DataModel.SuggEvent
import com.projectt.mg_location.R

class ListSuggAdapter(private var activity: Activity, private var items: ArrayList<SuggEvent>) : BaseAdapter() {

    private class ViewHolder(row: View?) {
        var data: TextView? = null
        var time: TextView? = null
        var place: TextView? = null
        var name: TextView? = null
        var info: TextView? = null

        init {
            this.data = row?.findViewById<TextView>(R.id.Date)
            this.time = row?.findViewById<TextView>(R.id.Time)
            this.place = row?.findViewById<TextView>(R.id.Place)
            this.name = row?.findViewById<TextView>(R.id.Name)
            this.info = row?.findViewById<TextView>(R.id.Info)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            view = inflater.inflate(R.layout.suggevents_card_items, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var tea = items[position]
        viewHolder.data?.text = tea.date
        viewHolder.time?.text = tea.time
        viewHolder.place?.text = tea.place
        viewHolder.name?.text = tea.name
        viewHolder.info?.text = tea.desc

        return view
    }

    override fun getItem(i: Int): SuggEvent {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}