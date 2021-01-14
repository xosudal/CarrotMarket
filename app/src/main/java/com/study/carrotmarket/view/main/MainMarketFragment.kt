package com.study.carrotmarket.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.DetailUsedItemResponse
import com.study.carrotmarket.constant.MainMarketContract
import com.study.carrotmarket.constant.SimpleUsedItemResponse
import com.study.carrotmarket.presenter.main.MainMarketPresenter
import com.study.carrotmarket.view.chat.WriteUsedArticleActivity
import com.study.carrotmarket.view.main.adapter.SellingItemRecyclerAdapter
import com.study.carrotmarket.view.neighborhood.NeighborhoodAdvertisementArticleActivity
import kotlinx.android.synthetic.main.fragment_main_market.*
import kotlinx.android.synthetic.main.fragment_main_market.view.*
import java.util.*

class MainMarketFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener, MainMarketContract.View {
    private val TAG = MainMarketFragment::class.java.simpleName
    private lateinit var presenter : MainMarketPresenter
    private lateinit var sellingAdapter : SellingItemRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_main_layout.setOnRefreshListener(this)
        presenter = MainMarketPresenter(this)

        sellingAdapter = SellingItemRecyclerAdapter(this@MainMarketFragment, ArrayList<SimpleUsedItemResponse>()){
            presenter.getDetailUsedItem(it.id, it.e_mail)
        }

        with (view.main_market_recyclerview) {
            layoutManager = LinearLayoutManager(context!!)
            adapter = sellingAdapter
        }

        presenter.getSimpleUsedItem()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_market, container, false).apply {
            fab_menu.setClosedOnTouchOutside(true)
            fab_sub1.setOnClickListener {
                startActivity(Intent(context, NeighborhoodAdvertisementArticleActivity::class.java))
            }
            fab_sub2.setOnClickListener {
                startActivity(Intent(context, WriteUsedArticleActivity::class.java))
            }
        }
        return view
    }

    override fun onRefresh() {
        Log.d(TAG, "onRefresh")
        presenter.getSimpleUsedItem()
    }

    override fun setUsedItemList(list: List<SimpleUsedItemResponse>) {
        sellingAdapter.addAllUsedItems(list)
        swipe_main_layout.isRefreshing = false
    }

    override fun setDetailUsedItem(item: DetailUsedItemResponse) {
        val intent = Intent(context, DetailedSellingItemActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("DetailUsedItem", item)
        intent.putExtra("bundle", bundle)
        context?.startActivity(intent)
    }
}