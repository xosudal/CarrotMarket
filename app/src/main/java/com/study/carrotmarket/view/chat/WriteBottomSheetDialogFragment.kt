package com.study.carrotmarket.view.chat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.study.carrotmarket.R
import com.study.carrotmarket.view.neighborhood.NeighborhoodAdvertisementArticleActivity
import com.study.carrotmarket.view.neighborhood.NeighborhoodLifeArticleActivity
import kotlinx.android.synthetic.main.layout_write_bottom_sheet.*
import kotlinx.android.synthetic.main.layout_write_bottom_sheet_item.view.*

class WriteBottomSheetDialogFragment : BottomSheetDialogFragment(), OnStartActivity {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_write_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        write_bs_recylerview.adapter = WriteBsdfAdapter(this)
        write_bs_recylerview.layoutManager = LinearLayoutManager(view.context)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK)  dismiss()
    }

    override fun onStartActivityAsResult(intent: Intent) {
        activity?.let {
            startActivityForResult(intent, 1)
        }
    }
}

class WriteBsdfAdapter(private val callback : OnStartActivity) : RecyclerView.Adapter<WriteBsdfAdapter.WriteViewHolder>() {
    private lateinit var context : Context;
    companion object {
        val itemList : List<WriteBottomItem> = arrayListOf(
            WriteBottomItem(R.drawable.ic_outline_used_trade_24, "중고거래", "중고 물건을 판매할 수 있어요"),
            WriteBottomItem(R.drawable.ic_outline_question_answer_24, "동네생활", "우리 동네에 관련된 질문이다 이야기를 할 수 이어요"),
            WriteBottomItem(R.drawable.ic_outline_home_24, "동네홍보", "중고 물건을 판매할 수 있어요")
            )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_write_bottom_sheet_item, parent, false)
        context = parent.context
        return WriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: WriteViewHolder, position: Int) {
        itemList[position].let { it ->
            holder.img.setImageDrawable(
                ContextCompat.getDrawable(holder.itemView.context, it.img))
            holder.title.text = it.title
            holder.desc.text = it.desc

            holder.itemView.setOnClickListener { view ->
                Toast.makeText(view.context, it.title, Toast.LENGTH_SHORT).show()

                when (position) {
                    0 -> WriteUsedArticleActivity::class.java
                    1 -> NeighborhoodLifeArticleActivity::class.java
                    2 -> NeighborhoodAdvertisementArticleActivity::class.java
                    else -> null
                }?.let { classJava ->
                    callback.onStartActivityAsResult(Intent(holder.itemView.context, classJava))
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class WriteViewHolder(item : View) : RecyclerView.ViewHolder(item) {
        val img = item.write_bs_imageview
        val title = item.write_bs_title
        val desc = item.write_bs_desc
    }

    data class WriteBottomItem(val img : Int, val title : String, val desc : String)
}

interface OnStartActivity{
    fun onStartActivityAsResult(intent : Intent)
}
