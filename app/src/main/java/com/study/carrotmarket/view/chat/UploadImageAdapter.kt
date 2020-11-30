package com.study.carrotmarket.view.chat

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.study.carrotmarket.R
import kotlinx.android.synthetic.main.layout_write_usedarticle_uploadimages.view.*

class UploadImageAdapter(private var removeItem : RemoveItem) : RecyclerView.Adapter<UploadImageAdapter.UploadImageViewHolder>() {
    private var mUploadImagesItems = ArrayList<Uri>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_write_usedarticle_uploadimages, parent, false)
        return UploadImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: UploadImageViewHolder, position: Int) {
        val context = holder.itemView.context
        Glide.with(context).load(mUploadImagesItems[position]).into(holder.thumbnail)
        holder.thumbnail.clipToOutline = true

        holder.remove.setOnClickListener {
            removeItem(position)
            onRemovedItem()
            Toast.makeText(context, "삭제", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = mUploadImagesItems.size

    fun getItem(position : Int) : Uri {
        return mUploadImagesItems[position]
    }

    fun addItem(uri : Uri) {
        if(!mUploadImagesItems.contains(uri))
            mUploadImagesItems.add(uri)

        notifyDataSetChanged()
    }

    private fun removeItem(position : Int) {
        mUploadImagesItems.removeAt(position)
        notifyDataSetChanged()
    }

    private fun onRemovedItem() {
        removeItem.onRemovedItem()
    }

    class UploadImageViewHolder(item : View) : RecyclerView.ViewHolder(item) {
        val thumbnail = item.thumbnail_upload_img
        val remove = item.remove_upload_img
    }
}