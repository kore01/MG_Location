package com.projectt.mg_location.Adapters

//package com.project.mg_location.Adapters

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import android.widget.Toast
import com.projectt.mg_location.DataModel.Event
import com.projectt.mg_location.R

import java.time.ZoneId
import java.time.ZonedDateTime

import java.util.HashMap

class ExpandableListAdapter internal constructor(
    private val context: Context,
    private val titleList: List<String>,
    private val dataList: HashMap<String, List<Event>>
) : BaseExpandableListAdapter() {

    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        Log.i("this is", this.dataList[this.titleList[listPosition]]!![expandedListPosition].name)
        return this.dataList[this.titleList[listPosition]]!![expandedListPosition]
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(
        listPosition: Int,
        expandedListPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val expandedListText = getChild(listPosition, expandedListPosition) as Event
        if (convertView == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_item, null)
        }

        val name = convertView!!.findViewById<TextView>(R.id.Name)
        name.text = expandedListText.name
        val place = convertView.findViewById<TextView>(R.id.Place)
        place.text = expandedListText.place
        val time = convertView.findViewById<TextView>(R.id.Time)
        time.text = expandedListText.time
        val info = convertView.findViewById<TextView>(R.id.Info)
        info.text = expandedListText.info
        Log.i("child", expandedListText.name)
        return convertView
    }

    override fun getChildrenCount(listPosition: Int): Int {
        Log.i("this is the children",this.dataList[titleList[listPosition]]!!.size.toString())
        return this.dataList[this.titleList[listPosition]]!!.size
    }

    override fun getGroup(listPosition: Int): Any {
        return this.titleList[listPosition]
    }

    override fun getGroupCount(): Int {
        //Toast.makeText(this, this.titleList.size.toString(),Toast.LENGTH_LONG).show();
        Log.i("this",this.titleList.size.toString())
        return this.titleList.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(listPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val listTitle = getGroup(listPosition) as String
        Log.i("title", listTitle)
        if (convertView == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_group, null)
        }
        val listTitleTextView = convertView!!.findViewById<TextView>(R.id.listTitle)
        listTitleTextView.setTypeface(null, Typeface.BOLD)
        listTitleTextView.text = listTitle

        return convertView
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return false
    }
}


