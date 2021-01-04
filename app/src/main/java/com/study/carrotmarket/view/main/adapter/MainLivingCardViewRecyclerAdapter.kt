package com.study.carrotmarket.view.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.MainLivingCardViewItem
import com.study.carrotmarket.util.toHumanReadable
import kotlinx.android.synthetic.main.layout_living_cardview.view.*

class MainLivingCardViewRecyclerAdapter(private val mFragment: Fragment, private val dataset: List<MainLivingCardViewItem>, private val mainLiving: MainLiving): RecyclerView.Adapter<MainLivingCardViewRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.card_view
        val categoryView: TextView = itemView.cardview_text_category
        val nicknameView: TextView = itemView.cardview_text_nickname
        val timestampView: TextView = itemView.cardview_text_timestamp
        val profileView: ImageView = itemView.cardview_image_profile
        val userAddInfoView: TextView = itemView.cardview_text_userinfo
        val userCommentView: TextView = itemView.cardview_text_usercomment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_living_cardview, parent, false)
        return ViewHolder(listView)
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val profile:String = "https://randomuser.me/api/portraits/med/women/17.jpg"
        with(dataset[position]) {
            Glide.with(mFragment).load(profile)
                .circleCrop().into(holder.profileView)

            holder.categoryView.text = "CATEGORY"
            holder.nicknameView.text = cardUserName
            holder.timestampView.text = cardTimeSamp.toHumanReadable()
            holder.userAddInfoView.text = cardUserAddInfo
            holder.userCommentView.text = cardUserComment


        }
        holder.itemView.living_layout.setOnClickListener {
            mainLiving.callActivity(profile, dataset[position])
        }
    }
}

interface MainLiving {
    //fun callActivity(profileUri:String, category:String, nickname:String, timestamp:String, userInfo:String, comment:String)
    fun callActivity(profile:String, mainLiving:MainLivingCardViewItem)
}