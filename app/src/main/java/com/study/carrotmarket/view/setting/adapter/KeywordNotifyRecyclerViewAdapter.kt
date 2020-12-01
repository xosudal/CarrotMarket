package com.study.carrotmarket.view.setting.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.study.carrotmarket.R
import kotlinx.android.synthetic.main.layout_keyword.view.*

class KeyWordNotifyRecyclerViewAdapter(private var temp:Temp) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var keywordList = arrayListOf<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_keyword, parent,false)

        return KeyWordViewHolder(view)
    }

    inner class KeyWordViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = (holder as KeyWordViewHolder).itemView
        viewHolder.tv_keyword.text =keywordList[position]
        viewHolder.keyword_iv_layout.setOnClickListener {
            temp.removeKeyword(position)
            temp.updateKeywordCount(keywordList.size)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return keywordList.size
    }

    fun setKeywordList(list:ArrayList<String>) {
        //keywordList = list
        keywordList.addAll(list) // model save 하게되면 list
    }

    fun addKeyword(keyword:String) {
        keywordList.add(keyword)
    }

    fun getKeywordList():ArrayList<String> {
        return keywordList
    }
}

interface Temp {
    fun updateKeywordCount(size:Int)
    fun removeKeyword(position:Int)
}
