package com.study.carrotmarket

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_chatting.*
import kotlinx.android.synthetic.main.toolbar.*

class ChattingFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.Theme_MaterialComponents_Light_NoActionBar)
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        return localInflater.inflate(R.layout.fragment_chatting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.toolbar_title?.text = "채팅"

        val adapter = ChattingListAdapter(fragmentManager!!)
        chatting_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        chatting_recycler.adapter = adapter

        adapter.addItem(ChattingListItem(R.drawable.ic_portrait_black_24dp, "푸랑이", "서울특별시 마포구, 1일전", "가격 선제시요."))
        adapter.addItem(ChattingListItem(R.drawable.ic_twotone_event_lesson_24, "허태녕", "서울특별시 가양구, 2일전", "아직 거래 가능한가요?"))
        adapter.addItem(ChattingListItem(R.drawable.ic_twotone_land_24, "최태양", "경기도 김포구, 5일전", "20에 제가 직접  찾아가는 조건이요."))
    }

}
