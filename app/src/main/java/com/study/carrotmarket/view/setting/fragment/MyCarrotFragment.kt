package com.study.carrotmarket.view.setting.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.UserInfo
import com.study.carrotmarket.presenter.setting.MyCarrotPresenter
import com.study.carrotmarket.view.setting.activity.*
import kotlinx.android.synthetic.main.fragment_mycarrot.*
import kotlinx.android.synthetic.main.fragment_mycarrot.view.*
import kotlinx.android.synthetic.main.toolbar.*

class MyCarrotFragment : Fragment() {
    private var fragmentView: View? = null
    private lateinit var presenter:MyCarrotPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        presenter = MyCarrotPresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentView = LayoutInflater.from(activity).inflate(R.layout.fragment_mycarrot, container, false).apply {
            mycarrot_layout_userInformation?.setOnClickListener {
                if (!presenter.isLogIn()) {
                    Toast.makeText(context,"로그인이 필요합니다.",Toast.LENGTH_SHORT).show()
                } else {
                    startActivity(Intent(context, ProfileActivity::class.java))
                }
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
                startActivity(Intent(context,KeywordNotifyActivity::class.java))
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

            mycarrot_imageview_userImage?.setOnClickListener {
                if (!presenter.isLogIn()) {
                    Toast.makeText(context,"로그인이 필요합니다.",Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(context, ProfileEditActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        activity?.toolbar_title?.text = "나의 당근"
        return fragmentView
    }

    override fun onResume() {
        super.onResume()
        context?.let { presenter.setContext(it) }
        getProfile()
    }

    private fun getProfile() {
        if (!presenter.isLogIn()) {
            mycarrot_imageview_userImage.setImageResource(R.drawable.ic_baseline_face_24)
            mycarrot_userInfo_tv.apply {
                text = "로그인이 필요합니다"
                typeface = Typeface.DEFAULT_BOLD
            }
            return
        }

        mycarrot_userInfo_tv.text = "${presenter.getUserID()}\n${presenter.getUserRegion()}"
        Glide.with(this).asBitmap().load(presenter.loadProfileUri()).circleCrop().into(mycarrot_imageview_userImage)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//      super.onCreateOptionsMenu(menu, inflater)
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