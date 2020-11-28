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
import com.study.carrotmarket.constant.SellListItem
import com.study.carrotmarket.constant.SimpleUsedItemResponse
import com.study.carrotmarket.presenter.main.MainMarketPresenter
import com.study.carrotmarket.view.main.adapter.SellingItemRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_main_market.*
import kotlinx.android.synthetic.main.fragment_main_market.view.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainMarketFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainMarketFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener, MainMarketContract.View {
    private val TAG = MainMarketFragment::class.java.simpleName
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var presenter : MainMarketPresenter
    private lateinit var sellingAdapter : SellingItemRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_main_layout.setOnRefreshListener(this)
        presenter = MainMarketPresenter(this)

        sellingAdapter = SellingItemRecyclerAdapter(this@MainMarketFragment, ArrayList<SimpleUsedItemResponse>()){
            presenter.getDetailUsedItem(it.id)
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
        val view = inflater.inflate(R.layout.fragment_main_market, container, false)
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_main_market.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainMarketFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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