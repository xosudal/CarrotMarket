package com.study.carrotmarket.view.main

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.MainLivingCardViewItem
import com.study.carrotmarket.view.main.adapter.MainLiving
import com.study.carrotmarket.view.main.adapter.MainLivingCardViewRecyclerAdapter
import com.study.carrotmarket.view.neighborhood.NeighborhoodLifeArticleActivity
import kotlinx.android.synthetic.main.fragment_main_living.view.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*


class MainLivingFragment : Fragment(), MainLiving {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_living, container, false)
        with (view.main_living_cardview_recyclerview) {
            layoutManager = LinearLayoutManager(context!!)
            adapter = MainLivingCardViewRecyclerAdapter(this@MainLivingFragment, getSampleCards(), this@MainLivingFragment)
        }

        activity?.toolbar_title?.text = "동네생활"

        view.fab.setOnClickListener {
            startActivity(Intent(context, NeighborhoodLifeArticleActivity::class.java))
        }

        return view
    }

    private fun getSampleCards(): List<MainLivingCardViewItem> {
        return listOf(
            MainLivingCardViewItem(0, Date(Date().time - 10000), "서동재", "의정부지검 인증 181회", "부장님께 내 말씀좀 드려..", 18001),
            MainLivingCardViewItem(1, Date(Date().time - 1000000), "황시목", "서부지검 인증 18회", "아닌데요", 18010),
            MainLivingCardViewItem(0, Date(Date().time - 10000), "서동재", "의정부지검 인증 181회", "부장님께 내 말씀좀 드려..", 18001),
            MainLivingCardViewItem(1, Date(Date().time - 1000000), "황시목", "서부지검 인증 18회", "아닌데요", 18010),
            MainLivingCardViewItem(0, Date(Date().time - 10000), "서동재", "의정부지검 인증 181회", "부장님께 내 말씀좀 드려..", 18001),
            MainLivingCardViewItem(1, Date(Date().time - 1000000), "황시목", "서부지검 인증 18회", "아닌데요", 18010)
        )
    }

    override fun callActivity(profile:String, mainLiving: MainLivingCardViewItem) {
        val intent = Intent(context, StoryActivity::class.java).apply {
            putExtra("PROFILE", profile)
            putExtra("CONTENT", mainLiving)
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_neighborhood_life, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.search -> {
                Toast.makeText(context, "search", Toast.LENGTH_SHORT).show()
            }
            R.id.list -> {

            }
            R.id.notify -> {

            }
        }
        return true
    }
}