package com.study.carrotmarket.view.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.DetailUsedItemResponse
import kotlinx.android.synthetic.main.activity_detailed_selling_item.*
import kotlinx.android.synthetic.main.toolbar.*

class DetailedSellingItemActivity : AppCompatActivity() {
    private val TAG = DetailedSellingItemActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_selling_item)

        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar_title?.text = ""

        val imgUriList = ArrayList<String>()

        val detail = intent.getBundleExtra("bundle")?.getParcelable<DetailUsedItemResponse>("DetailUsedItem")


        detail?.let {
            for(i in 0 until (detail.images)) {
                imgUriList.add("http://csh0303.iptime.org:8080/static/${detail.nickname}/${detail.id}/item_$i.png")
            }
            Log.d(TAG, "list count: ${imgUriList.size}")

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_used_article, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item);
    }

    inner class SellingItemImagePagerAdapter(private val imgUrls: ArrayList<String>): PagerAdapter() {
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val context = container.context
            val imgView = ImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
            }

            Log.d("SellingImagePager", "size:${imgUrls.size}, instantiateItem. ${imgUrls[position]}")
            Glide.with(context).load(imgUrls[position]).into(imgView)

            container.addView(imgView)
            return imgView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view == obj
        }
        override fun getCount(): Int = imgUrls.size
    }
}

