package com.study.carrotmarket

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.layout_write_bottom_sheet.*
import kotlinx.android.synthetic.main.layout_write_bottom_sheet_item.view.*

class WriteBottomSheetDialogFragment : BottomSheetDialogFragment() {
    companion object {
        fun AddWriteBsdf() : WriteBottomSheetDialogFragment {
            return WriteBottomSheetDialogFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_write_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        write_bs_recylerview.adapter = WriteBsdfAdapter()
        write_bs_recylerview.layoutManager = LinearLayoutManager(view.context)

        super.onViewCreated(view, savedInstanceState)
    }
}

class WriteBsdfAdapter : RecyclerView.Adapter<WriteBsdfAdapter.WriteViewHolder>() {
    companion object {
        val itemList : List<WriteBottomItem> = arrayListOf(
            WriteBottomItem(R.drawable.ic_outline_used_trade_24, "중고거래", "중고 물건을 판매할 수 있어요"),
            WriteBottomItem(R.drawable.ic_outline_question_answer_24, "동네생활", "우리 동네에 관련된 질문이다 이야기를 할 수 이어요"),
            WriteBottomItem(R.drawable.ic_outline_home_24, "동네홍보", "중고 물건을 판매할 수 있어요")
            )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_write_bottom_sheet_item, parent, false)
        return WriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: WriteViewHolder, position: Int) {
        WriteBsdfAdapter.itemList[position].let {
            holder.img.setImageDrawable(holder.itemView.context.getDrawable(it.img))
            holder.title.text = it.title
            holder.desc.text = it.desc

            holder.itemView.setOnClickListener {view ->
                Toast.makeText(view.context, "${it.title}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    class WriteViewHolder(item : View) : RecyclerView.ViewHolder(item) {
        val img = item.write_bs_imageview
        val title = item.write_bs_title
        val desc = item.write_bs_desc
    }

    data class WriteBottomItem(val img : Int, val title : String, val desc : String)

}
