package com.study.carrotmarket.view.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.study.carrotmarket.R
import kotlinx.android.synthetic.main.layout_what_about_item.view.*

class WhatAboutAdapter(private var dataSet : List<WhatAboutItem>) : RecyclerView.Adapter<WhatAboutAdapter.WhatAboutViewHolder>() {
    private val TOTAL_COUNT = 6

    class WhatAboutViewHolder(private val item : View) : RecyclerView.ViewHolder(item) {
        val img = item.class_img
        val text = item.class_tv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WhatAboutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_what_about_item, parent, false)
        return WhatAboutViewHolder(view)
    }

    override fun onBindViewHolder(holder: WhatAboutViewHolder, position: Int) {
        val img = dataSet[position].imgId
        val text = dataSet[position].text
        holder.img.setImageDrawable(holder.itemView.context.getDrawable(img))
        holder.text.text = text

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "$text", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return TOTAL_COUNT
    }
}

data class WhatAboutItem(val imgId : Int, val text : String)
