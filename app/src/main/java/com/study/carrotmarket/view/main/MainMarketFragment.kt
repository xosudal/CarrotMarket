package com.study.carrotmarket.view.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.SellListItem
import com.study.carrotmarket.view.main.adapter.SellingItemRecyclerAdapter
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
class MainMarketFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //(activity as AppCompatActivity).supportActionBar?.hide()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_market, container, false)
        val sampleItemList = getSampleItems()

        with (view.main_market_recyclerview) {
            layoutManager = LinearLayoutManager(context!!)
            adapter = SellingItemRecyclerAdapter(this@MainMarketFragment, sampleItemList) {
//                Toast.makeText(context, "Hello ${it.itemName}", Toast.LENGTH_LONG).show()
                val intent = Intent(context, DetailedSellingItemActivity::class.java)
                context.startActivity(intent)
            }

        }
        return view

    }

    fun getSampleItems(): List<SellListItem> {

        context!!.let {
            return listOf(
                SellListItem("폴리", it.getDrawable((R.drawable.sample_item_1)), "서울시 관악구", Date(), 150000),
                SellListItem("범블비", it.getDrawable((R.drawable.sample_item_2)), "경기도 의왕시", Date(), 150000),
                SellListItem("짭새", it.getDrawable((R.drawable.sample_item_3)), "서울시 마포구", Date(), 150000),
                SellListItem("폴리", it.getDrawable((R.drawable.sample_item_1)), "서울시 관악구", Date(), 150000),
                SellListItem("범블비", it.getDrawable((R.drawable.sample_item_2)), "경기도 의왕시", Date(), 150000),
                SellListItem("짭새", it.getDrawable((R.drawable.sample_item_3)), "서울시 마포구", Date(), 150000),
                SellListItem("폴리", it.getDrawable((R.drawable.sample_item_1)), "서울시 관악구", Date(), 150000),
                SellListItem("범블비", it.getDrawable((R.drawable.sample_item_2)), "경기도 의왕시", Date(), 150000),
                SellListItem("짭새", it.getDrawable((R.drawable.sample_item_3)), "서울시 마포구", Date(), 150000),
                SellListItem("폴리", it.getDrawable((R.drawable.sample_item_1)), "서울시 관악구", Date(), 150000),
                SellListItem("범블비", it.getDrawable((R.drawable.sample_item_2)), "경기도 의왕시", Date(), 150000),
                SellListItem("짭새", it.getDrawable((R.drawable.sample_item_3)), "서울시 마포구", Date(), 150000),
                SellListItem("폴리", it.getDrawable((R.drawable.sample_item_1)), "서울시 관악구", Date(), 150000),
                SellListItem("범블비", it.getDrawable((R.drawable.sample_item_2)), "경기도 의왕시", Date(), 150000),
                SellListItem("짭새", it.getDrawable((R.drawable.sample_item_3)), "서울시 마포구", Date(), 150000),
                SellListItem("폴리", it.getDrawable((R.drawable.sample_item_1)), "서울시 관악구", Date(), 150000),
                SellListItem("범블비", it.getDrawable((R.drawable.sample_item_2)), "경기도 의왕시", Date(), 150000),
                SellListItem("짭새", it.getDrawable((R.drawable.sample_item_3)), "서울시 마포구", Date(), 150000)
            )
        }
        return listOf()
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
}