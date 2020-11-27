package com.study.carrotmarket.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.SellListItem
import com.study.carrotmarket.constant.SimpleUsedItemResponse
import com.study.carrotmarket.constant.UsedItems
import kotlinx.android.synthetic.main.layout_listview_item.view.*

class SellingItemRecyclerAdapter(private val mFragment: Fragment,
                                 private val dataSet: ArrayList<SimpleUsedItemResponse>,
                                 val itemClick: (SimpleUsedItemResponse) -> Unit
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

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        dataSet[position].apply {
            Glide.with(mFragment).load("http://csh0303.iptime.org:8080/static/$nickname/$id/item_0.png").apply(
                RequestOptions.bitmapTransform(
                RoundedCorners(50)
            )).into(holder.imageView)
            holder.nameView.setText(title)
            holder.priceView.setText(price.toString())
            holder.addressView.setText(region)
            holder.itemView.setOnClickListener{itemClick(dataSet[position])}
        }
    }


    fun addAllUsedItems(list : List<SimpleUsedItemResponse>) {
        if(!dataSet.isEmpty()) dataSet.clear()
        dataSet.addAll(list)

        notifyDataSetChanged()
    }
}