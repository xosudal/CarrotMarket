package com.study.carrotmarket

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.study.carrotmarket.setting.activity.*
import kotlinx.android.synthetic.main.fragment_mycarrot.view.*
import kotlinx.android.synthetic.main.toolbar.*

class MyCarrotFragment : Fragment() {
    var fragmentView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentView = LayoutInflater.from(activity).inflate(R.layout.fragment_mycarrot, container, false).apply {
            mycarrot_show_profile_button?.setOnClickListener {
                startActivity(Intent(context, ProfileActivity::class.java))
            }

            mycarrot_sales_history?.setOnClickListener {
                startActivity(Intent(context, SalesActivity::class.java))
            }

            mycarrot_purchase_history?.setOnClickListener {
                startActivity(Intent(context, PurchaseActivity::class.java))
            }

            mycarrot_favorite_list?.setOnClickListener {
                startActivity(Intent(context, FavoriteActivity::class.java))
            }

            mycarrot_setting_my_location?.setOnClickListener {
                startActivity(Intent(context,RegionSettingActivity::class.java))
            }

            mycarrot_auth_my_location?.setOnClickListener {
                startActivity(Intent(context,CheckInActivity::class.java))
            }

            mycarrot_alert_keyword?.setOnClickListener {
                startActivity(Intent(context,KeyWordNotifyActivity::class.java))
            }

            mycarrot_collect_list?.setOnClickListener {
                startActivity(Intent(context,FollowingArticleActivity::class.java))
            }

            mycarrot_neighbor_written?.setOnClickListener {
                startActivity(Intent(context, UserStoryArticlesActivity::class.java).apply {
                    putExtra("tab",0)
                })
            }

            mycarrot_neighbor_comment?.setOnClickListener {
                startActivity(Intent(context, UserStoryArticlesActivity::class.java).apply {
                    putExtra("tab",1)
                })
            }

            mycarrot_neighbor_subject_list?.setOnClickListener {
                startActivity(Intent(context, InterestsActivity::class.java))
            }

            mycarrot_invite_friends?.setOnClickListener {
                startActivity(Intent(context, WebViewActivity::class.java).apply {
                    putExtra("mode",0)
                })
            }

            mycarrot_share_carrot?.setOnClickListener {
                startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply{
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT,"공유할 text")
                },"당근마켓 공유하기"))
            }

            mycarrot_notice?.setOnClickListener {
                startActivity(Intent(context, WebViewActivity::class.java).apply {
                    putExtra("mode",1)
                })
            }

            mycarrot_frequentlyAskedQuestion?.setOnClickListener {
                startActivity(Intent(context, WebViewActivity::class.java).apply {
                    putExtra("mode",2)
                })
            }

            mycarrot_app_setting?.setOnClickListener {
                startActivity(Intent(context, SettingActivity::class.java))
            }
        }
        activity?.toolbar_title?.text = "나의 당근"
        return fragmentView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_mycarrot,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.mycarrot_setting -> {
                startActivity(Intent(context, SettingActivity::class.java))
            }
        }
        return true
    }
}