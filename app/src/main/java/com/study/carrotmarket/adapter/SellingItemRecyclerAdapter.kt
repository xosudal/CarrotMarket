package com.study.carrotmarket.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.study.carrotmarket.R
import com.study.carrotmarket.model.SellListItem
import kotlinx.android.synthetic.main.layout_listview_item.view.*

class SellingItemRecyclerAdapter(private val mFragment: Fragment,
                                 private val dataset: List<SellListItem>,
                                 val itemClick: (SellListItem) -> Unit
): RecyclerView.Adapter<SellingItemRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.sellingItemPoster
        val nameView: TextView = itemView.sellingItemName
        val priceView = itemView.sellingItemPrice
        val addressView = itemView.sellingItemAddress
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_listview_item, parent, false)
        return ViewHolder(listView)
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(dataset[position]) {
            Glide.with(mFragment).load(posterDrawable).apply(
                RequestOptions.bitmapTransform(
                RoundedCorners(50)
            )).into(holder.imageView)
//            holder.imageView.setImageDrawable(posterDrawable)
            holder.nameView.setText(itemName)
            holder.priceView.setText(itemPrice.toString())
            holder.addressView.setText(itemAddress)
            holder.itemView.setOnClickListener{itemClick(dataset[position])}
        }
    }
}