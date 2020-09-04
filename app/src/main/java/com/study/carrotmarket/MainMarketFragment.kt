package com.study.carrotmarket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.study.carrotmarket.adapter.SellingItemAdapter
import com.study.carrotmarket.model.SellListItem
import kotlinx.android.synthetic.main.fragment_main_market.view.*

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_market, container, false)
        val adapter = SellingItemAdapter(context!!, getSampleItems())
        view.main_market_listview.adapter = adapter
        view.main_market_listview.onItemClickListener = object: AdapterView.OnItemClickListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(context, (adapter.getItem(position) as SellListItem).itemAddress, Toast.LENGTH_SHORT).show()
            }

        }
        return view
    }

    fun getSampleItems(): List<SellListItem> {
        return listOf(
            /*SellListItem("폴리", R.drawable.sample_item_1, "서울시 관악구", Date(), 150000),
            SellListItem("범블비", R.drawable.sample_item_2, "서울시 중랑구", Date(), 150000),
            SellListItem("케이캅스",R.drawable.sample_item_3, "서울시 강서구", Date(), 150000),
            SellListItem("폴리", R.drawable.sample_item_1, "서울시 관악구", Date(), 150000),
            SellListItem("범블비", R.drawable.sample_item_2, "서울시 중랑구", Date(), 150000),
            SellListItem("케이캅스",R.drawable.sample_item_3, "서울시 강서구", Date(), 150000),
            SellListItem("폴리", R.drawable.sample_item_1, "서울시 관악구", Date(), 150000),
            SellListItem("범블비", R.drawable.sample_item_2, "서울시 중랑구", Date(), 150000),
            SellListItem("케이캅스",R.drawable.sample_item_3, "서울시 강서구", Date(), 150000),
            SellListItem("폴리", R.drawable.sample_item_1, "서울시 관악구", Date(), 150000),
            SellListItem("범블비", R.drawable.sample_item_2, "서울시 중랑구", Date(), 150000),
            SellListItem("케이캅스",R.drawable.sample_item_3, "서울시 강서구", Date(), 150000),
            SellListItem("폴리", R.drawable.sample_item_1, "서울시 관악구", Date(), 150000),
            SellListItem("범블비", R.drawable.sample_item_2, "서울시 중랑구", Date(), 150000),
            SellListItem("케이캅스",R.drawable.sample_item_3, "서울시 강서구", Date(), 150000),
            SellListItem("폴리", R.drawable.sample_item_1, "서울시 관악구", Date(), 150000),
            SellListItem("범블비", R.drawable.sample_item_2, "서울시 중랑구", Date(), 150000),
            SellListItem("케이캅스",R.drawable.sample_item_3, "서울시 강서구", Date(), 150000)*/
        )
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