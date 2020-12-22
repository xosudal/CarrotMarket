package com.study.carrotmarket.view.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.study.carrotmarket.R
import kotlinx.android.synthetic.main.layout_story_chat.view.*

class StoryRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1
    private val parentList:ArrayList<Temp> = arrayListOf(
        Temp("1", "첫 번째 내용"),
        Temp("2", "두 번째 내용"),
        Temp("3", "비자더리밪더ㅣ랍저다ㅣ"),
        Temp("4", "ㅁㄴㅇ리ㅏ먼이라ㅓ")
    )
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
        if (holder is HeaderViewHolder) {

        } else {
            val viewHolder = (holder as StoryViewHolder).itemView
            viewHolder.story_chat_nickname.text = parentList[position-1].nickname
            viewHolder.story_chat_main_content_tv.text = parentList[position-1].mainText
        }
    }

    override fun getItemCount(): Int {
        return parentList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> TYPE_HEADER
            else -> TYPE_ITEM
        }
    }
}


data class Temp (
    var nickname:String,
    var mainText:String
)