package com.tjsimoes.weatherappw

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView


class LocationListAdapter (private val context: Context, var name: ArrayList<String>, var imageID: ArrayList<Int>): BaseAdapter() {

    //The amount of rows in the list
    override fun getCount(): Int {
        return name.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return position
    }

    //Render rows from the list
    override fun getView(position: Int, converView: View?, viewGroup: ViewGroup?): View? {
        var view: View? = converView
        val viewHolder: ViewHolder

        if (view == null) {
            viewHolder = ViewHolder()
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.list_item,null,true)

            viewHolder.name = view.findViewById(R.id.tv_city_name_id)
            viewHolder.imageID = view.findViewById(R.id.iv_location_img_id)

            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.name?.text = name[position]
        viewHolder.imageID?.setImageResource(imageID[position])

        return view
    }

    private inner class ViewHolder{
        var name: TextView? = null
        internal var imageID: ImageView? = null
    }
}