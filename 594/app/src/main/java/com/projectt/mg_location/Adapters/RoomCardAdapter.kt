package com.projectt.mg_location.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.projectt.mg_location.DataModel.Rooms
import com.projectt.mg_location.R


class RoomCardAdapter(context: Context, resource: Int, list: ArrayList<Rooms>) : ArrayAdapter<Rooms>(context, resource, list)
{
    private var mResource: Int = 0
    private var mList: ArrayList<Rooms>
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
        val Rooms: Rooms? = if(count>position)getItem(position) else null
        val RoomNamee: TextView? = view.findViewById(R.id.Rooms_card_info)
        RoomNamee?.text = Rooms?.info ?: ""

        val RoomFamilyName: TextView? = view.findViewById(R.id.Rooms_card_type)
        RoomFamilyName?.text = Rooms?.type ?: ""

        val RoomNumberr: TextView? = view.findViewById(R.id.Rooms_card_number)
        RoomNumberr?.text = Rooms?.number ?: ""

        val RoomSubjectt: TextView? = view.findViewById(R.id.Rooms_card_location)
        RoomSubjectt?.text = Rooms?.location ?: ""
    }
}