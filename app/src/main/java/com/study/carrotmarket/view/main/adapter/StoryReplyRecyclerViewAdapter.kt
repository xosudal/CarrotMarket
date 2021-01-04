package com.study.carrotmarket.view.main.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.Reply
import kotlinx.android.synthetic.main.layout_story_chat.view.*

class StoryReplyRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private lateinit var replyList:ArrayList<Reply>
    var parent:Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_story_chat,
            parent,
            false
        )
        return StoryReplyViewHolder(view)
    }

    inner class StoryReplyViewHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = holder.itemView
            viewHolder.story_chat_nickname.text = replyList[position].nickname
            viewHolder.story_chat_main_content_tv.text = replyList[position].mainText

            if (replyList[position].childIndex != 0) {
                val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
                params.leftMargin = 80
                holder.itemView.layoutParams = params
                holder.itemView.story_chat_reply_tv.visibility = View.GONE
                holder.itemView.child_reply_layout.visibility = View.GONE

            } else {
                val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
                params.leftMargin = 0
                holder.itemView.apply {
                    layoutParams = params
                    child_reply_layout.visibility = View.GONE
                    story_chat_reply_tv.visibility = View.VISIBLE
                }
            }

    }

    override fun getItemCount(): Int {
        return replyList.size
    }

    fun changeList(list:ArrayList<Reply>) {
        replyList = list.filter { it.parentIndex == parent } as ArrayList<Reply>
        notifyDataSetChanged()
    }
}