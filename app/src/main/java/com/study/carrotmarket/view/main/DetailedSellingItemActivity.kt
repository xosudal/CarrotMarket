package com.study.carrotmarket.view.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.study.carrotmarket.R

import kotlinx.android.synthetic.main.activity_detailed_selling_item.*

class DetailedSellingItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_selling_item)

        main_selling_detailed_image_pager.adapter = SellingItemImagePagerAdapter(

            arrayOf(
                "https://t1.daumcdn.net/cfile/tistory/266D0F3C56E691F00E",
                "http://cdn.travelpartnerweb.com/DesiyaImages/Image/3/nxd/maw/tyk/fbr/0000010909RD.jpg",
                "https://t1.daumcdn.net/cfile/tistory/266D0F3C56E691F00E",
            )
        )
        
        Glide
            .with(this)
            .load("https://randomuser.me/api/portraits/med/women/17.jpg")
            .circleCrop()
            .into(main_selling_detailed_user_profile_image)

    }

    inner class SellingItemImagePagerAdapter(val imgUrls: Array<String>): PagerAdapter() {
        override fun getCount(): Int = imgUrls.size

        val context = this@DetailedSellingItemActivity
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val imgView = ImageView(context)
            container.addView(imgView, position)
            Log.d("SellingImagePager", "instantiateItem. ${imgUrls[position]}")
            Glide.with(context).load(imgUrls[position]).into(imgView)

            return imgView
        }
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            // 여기를 무조건 true로 하면 이미지가 잘 나오지 않는다.....
            return view == (`object` as ImageView)
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            Log.d("SellingImagePager", "destroy")
            container.removeView(`object` as View)

        }
    }
}

