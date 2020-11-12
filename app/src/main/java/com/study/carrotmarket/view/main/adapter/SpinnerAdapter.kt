package com.study.carrotmarket.view.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.study.carrotmarket.R

import kotlinx.android.synthetic.main.layout_spinner.view.*
import kotlinx.android.synthetic.main.layout_spinner_dropdown.view.*

class SpinnerAdapter(val context: Context, val list: Array<String>): BaseAdapter() {
    val inflater:LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.layout_spinner,parent,false)
        view.spinner_text.text = list[position]
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.layout_spinner_dropdown,parent,false)
        view.spinner_dropdown_text.text = list[position]
        return view
    }
}