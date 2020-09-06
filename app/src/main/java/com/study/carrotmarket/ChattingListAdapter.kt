package com.study.carrotmarket

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_chatting_item.view.*

class ChattingListAdapter(private val fragmentManager : FragmentManager) : RecyclerView.Adapter<ChattingListAdapter.ChattingViewHolder>() {
    private var item = ArrayList<ChattingListItem>()

    class ChattingViewHolder(item : View) : RecyclerView.ViewHolder(item) {
        val profile = item.iv_user_profile
        val nickname = item.nickname
        val detail = item.detail_info
        val lastmention = item.last_mention
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChattingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_chatting_item, parent, false)

        return ChattingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChattingViewHolder, position: Int) {
        val context = holder.itemView.context
        item[position]?.let {
            holder.apply {
                profile.setImageDrawable(context.getDrawable(it.profile_img))
                nickname.text = it.nickname
                detail.text = it.detail
                lastmention.text = it.lastMention
            }
        }
        holder.itemView.setOnLongClickListener {
            ChattingBottomSheetDialogFragment().show(fragmentManager, "show")
            true
        }
    }

    override fun getItemCount(): Int {
        return item.size
    }

    fun addItem(item : ChattingListItem) {
        this.item.add(item)
        notifyDataSetChanged()
    }
}

data class ChattingListItem(val profile_img : Int, val nickname : String, val detail : String, val lastMention : String)
