package com.study.carrotmarket.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.study.carrotmarket.R
import kotlinx.android.synthetic.main.layout_horizontal_picture.view.*

class HorizontalPictureAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val test = arrayListOf(
        "1", "2", "3", "4", "5", "6", "1", "2", "3", "4", "5", "6", "1", "2", "3", "4", "5", "6"
    )
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_horizontal_picture, parent, false)
        return HorizontalPictureViewHolder(view)
    }

    inner class HorizontalPictureViewHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder.itemView
        viewHolder.test_tv.text = test[position]
    }

    override fun getItemCount(): Int {
        return test.size
    }
}