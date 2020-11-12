package com.study.carrotmarket.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.MainLivingCardViewItem
import com.study.carrotmarket.view.main.adapter.MainLivingCardViewRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_main_living.view.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainLivingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainLivingFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_main_living, container, false)

        with (view.main_living_cardview_recyclerview) {
            layoutManager = LinearLayoutManager(context!!)
            adapter = MainLivingCardViewRecyclerAdapter(this@MainLivingFragment, getSampleCards())
        }

        return view

    }

    fun getSampleCards(): List<MainLivingCardViewItem> {
        return listOf(
            MainLivingCardViewItem(0, Date(Date().time - 10000), "서동재", "의정부지검 인증 181회", "부장님께 내 말씀좀 드려..", 18001),
            MainLivingCardViewItem(1, Date(Date().time - 1000000), "황시목", "서부지검 인증 18회", "아닌데요", 18010)
        )
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainLivingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainLivingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}