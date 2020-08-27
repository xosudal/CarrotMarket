package com.study.carrotmarket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_my_carrot.view.*

class MyCarrot : Fragment() {
    var fragmentView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentView = LayoutInflater.from(activity).inflate(R.layout.fragment_my_carrot, container, false).apply {
            mycarrot_imageview_setting?.setOnClickListener {
                Toast.makeText(activity,"Setting",Toast.LENGTH_SHORT).show()
            }

            mycarrot_show_profile_button?.setOnClickListener {
                Toast.makeText(activity,"show profile",Toast.LENGTH_SHORT).show()
            }

            mycarrot_sales_history?.setOnClickListener {
                Toast.makeText(activity,"sales history",Toast.LENGTH_SHORT).show()
            }

            mycarrot_purchase_history?.setOnClickListener {
                Toast.makeText(activity,"purchase history",Toast.LENGTH_SHORT).show()
            }

            mycarrot_favorite_list?.setOnClickListener {
                Toast.makeText(activity,"favorite list",Toast.LENGTH_SHORT).show()
            }

            mycarrot_setting_my_location?.setOnClickListener {
                Toast.makeText(activity,"set my location",Toast.LENGTH_SHORT).show()
            }

            mycarrot_auth_my_location?.setOnClickListener {
                Toast.makeText(activity,"auth my location",Toast.LENGTH_SHORT).show()
            }

            mycarrot_alert_keyword?.setOnClickListener {
                Toast.makeText(activity,"keyword alert",Toast.LENGTH_SHORT).show()
            }

            mycarrot_collect_list?.setOnClickListener {
                Toast.makeText(activity,"list collection",Toast.LENGTH_SHORT).show()
            }

            mycarrot_neighbor_written?.setOnClickListener {
                Toast.makeText(activity,"neighborhood text",Toast.LENGTH_SHORT).show()
            }

            mycarrot_neighbor_comment?.setOnClickListener {
                Toast.makeText(activity,"neighborhood comment",Toast.LENGTH_SHORT).show()
            }

            mycarrot_neighbor_subject_list?.setOnClickListener {
                Toast.makeText(activity,"neighborhood list",Toast.LENGTH_SHORT).show()
            }

            mycarrot_invite_friends?.setOnClickListener {
                Toast.makeText(activity,"invite my friends",Toast.LENGTH_SHORT).show()
            }

            mycarrot_share_carrot?.setOnClickListener {
                Toast.makeText(activity,"share carrot market",Toast.LENGTH_SHORT).show()
            }

            mycarrot_notice?.setOnClickListener {
                Toast.makeText(activity,"Notice",Toast.LENGTH_SHORT).show()
            }

            mycarrot_frequentlyAskedQuestion?.setOnClickListener {
                Toast.makeText(activity,"FAQ",Toast.LENGTH_SHORT).show()
            }

            mycarrot_app_setting?.setOnClickListener {
                Toast.makeText(activity,"app setting",Toast.LENGTH_SHORT).show()
            }
        }
        //test
        return fragmentView
    }
}