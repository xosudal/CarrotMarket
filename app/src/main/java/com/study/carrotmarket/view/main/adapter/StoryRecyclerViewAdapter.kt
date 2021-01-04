package com.study.carrotmarket.view.main.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.MainLivingCardViewItem
import com.study.carrotmarket.constant.Reply
import com.study.carrotmarket.view.main.StoryReplyActivity
import kotlinx.android.synthetic.main.layout_header.view.*
import kotlinx.android.synthetic.main.layout_story_chat.view.*

class StoryRecyclerViewAdapter(private val replyImpl: ReplyImpl): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1
    private lateinit var replyList: ArrayList<Reply>
    private lateinit var content: MainLivingCardViewItem
    private lateinit var profileUri:String
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        var view:View = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_story_chat,
            parent,
            false
        )
        var holder:RecyclerView.ViewHolder = StoryViewHolder(view)
        when (viewType) {
            TYPE_HEADER -> {
                view = LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_header,
                    parent,
                    false
                )
                holder = HeaderViewHolder(view)
            }
            TYPE_ITEM -> {
                view = LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_story_chat,
                    parent,
                    false
                )
                holder = StoryViewHolder(view)
            }
        }

        return holder
    }

    inner class StoryViewHolder(view: View):RecyclerView.ViewHolder(view)

    inner class HeaderViewHolder(view:View):RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is StoryViewHolder) {
            val viewHolder = holder.itemView
            viewHolder.story_chat_nickname.text = replyList[position-1].nickname
            viewHolder.story_chat_main_content_tv.text = replyList[position-1].mainText

            if (replyList[position-1].childIndex != 0) {
                val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
                params.leftMargin = 80
                holder.itemView.layoutParams = params
                holder.itemView.story_chat_reply_tv.visibility = View.GONE

                if (position + 1 == itemCount || (position + 1 < itemCount && (replyList[position].childIndex == 0))) {
                    holder.itemView.child_reply_layout.visibility = View.VISIBLE
                } else {
                    holder.itemView.child_reply_layout.visibility = View.GONE
                }
            } else {
                val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
                params.leftMargin = 0
                holder.itemView.apply {
                    layoutParams = params
                    child_reply_layout.visibility = View.GONE
                    story_chat_reply_tv.visibility = View.VISIBLE
                }
            }

            holder.itemView.story_chat_reply_tv.setOnClickListener {
                Log.d("test","click 1")
                replyImpl.callReplyActivity(replyList,replyList[position-1].parentIndex)
            }

            holder.itemView.child_tv_layout.setOnClickListener {
                Log.d("test","click 2")
                replyImpl.callReplyActivity(replyList,replyList[position-1].parentIndex)
            }
        } else if (holder is HeaderViewHolder) {
            val viewHolder = holder.itemView
            viewHolder.story_nickname.text = content.cardUserName
            viewHolder.story_main_content_tv.text = content.cardUserComment
            viewHolder.story_auth_count.text = content.cardUserAddInfo
            Glide.with(context).load(profileUri).circleCrop().into(viewHolder.story_user_image)
        }
    }

    override fun getItemCount(): Int {
        return replyList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> TYPE_HEADER
            else -> TYPE_ITEM
        }
    }

    fun changeList(list:ArrayList<Reply>) {
        replyList = list
        Log.d("test",list.toString())
        notifyDataSetChanged()
    }

    fun setContent(profile:String, list:MainLivingCardViewItem) {
        profileUri = profile
        content = list
    }
}

interface ReplyImpl {
    fun callReplyActivity(list: ArrayList<Reply>, parent:Int)
}