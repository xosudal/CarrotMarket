package com.study.carrotmarket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.study.carrotmarket.R
import com.study.carrotmarket.model.SellListItem
import kotlinx.android.synthetic.main.fragment_main_market.view.*
import kotlinx.android.synthetic.main.layout_listview_item.view.*

class SellingItemAdapter(mContext: Context, private val items: List<SellListItem>): BaseAdapter() {
    private val inflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.layout_listview_item, null)
        view.sellingItemName.text = items.get(position).itemName
//        view.sellingItemPoster?.setImageResource(items.get(position).posterId)
        view.sellingItemAddress.text = items.get(position).itemAddress
        view.sellingItemPrice.text = items.get(position).itemPrice.toString()
        return view
    }

    override fun getItem(position: Int): Any {
        return items.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()

    }

    override fun getCount(): Int {
        return items.size
    }
}