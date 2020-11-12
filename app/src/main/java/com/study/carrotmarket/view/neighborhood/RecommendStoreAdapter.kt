package com.study.carrotmarket.view.neighborhood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.study.carrotmarket.R
import kotlinx.android.synthetic.main.layout_recommand_store_item.view.*

class RecommendStoreAdapter(val dataSet : List<RecommendStoreItem>) : RecyclerView.Adapter<RecommendStoreAdapter.RecommandStoreViewHolder>() {

    class RecommandStoreViewHolder(item : View) : RecyclerView.ViewHolder(item) {
        val f_img = item.first_preview
        val s_img = item.second_preview
        val s_name = item.store_name
        val s_region = item.store_region
        val s_desc = item.store_desc
        val s_vote = item.store_vote
        val s_review = item.store_review
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommandStoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_recommand_store_item, parent, false)
        return RecommandStoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommandStoreViewHolder, position: Int) {
        holder.f_img.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, dataSet[position].firstImg))
        holder.s_img.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, dataSet[position].secondImg))
        holder.s_name.text = dataSet[position].storeName
        holder.s_region.text = dataSet[position].storeRegion
        holder.s_desc.text = dataSet[position].storeDesc
        holder.s_vote.text = dataSet[position].storeVote
        holder.s_review.text = dataSet[position].storeReview
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}

data class RecommendStoreItem(
    var firstImg : Int,
    var secondImg : Int,
    var storeName : String,
    var storeRegion : String,
    var storeDesc : String,
    var storeVote : String,
    var storeReview : String)