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
import com.study.carrotmarket.constant.DetailUsedItemResponse

import kotlinx.android.synthetic.main.activity_detailed_selling_item.*

class DetailedSellingItemActivity : AppCompatActivity() {
    private val TAG = DetailedSellingItemActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_selling_item)

        val imgUriList = ArrayList<String>()

        val detail = intent.getBundleExtra("bundle")?.getParcelable<DetailUsedItemResponse>("DetailUsedItem")


        detail?.let {
            for(i in 0 until (detail.images)) {
                imgUriList.add("http://csh0303.iptime.org:8080/static/${detail.nickname}/${detail.id}/item_$i.png")
            }

            main_selling_detailed_image_pager.adapter = SellingItemImagePagerAdapter(imgUriList)

            main_selling_detailed_user_nickname_text.text = it.nickname
            main_selling_detailed_user_region_text.text = it.region
            selling_item_name.text = it.title
            selling_item_category.text = it.category
            selling_item_content.text = it.desc
            manner_temp.text = it.manner_temp.toString()

        }

        Glide
            .with(this)
            .load("https://randomuser.me/api/portraits/med/women/17.jpg")
            .circleCrop()
            .into(main_selling_detailed_user_profile_image)
    }

    inner class SellingItemImagePagerAdapter(val imgUrls: ArrayList<String>): PagerAdapter() {
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

