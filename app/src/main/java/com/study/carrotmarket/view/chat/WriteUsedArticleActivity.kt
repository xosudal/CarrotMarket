package com.study.carrotmarket.view.chat

import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.UsedItems
import com.study.carrotmarket.model.RestApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_write_usedarticle.*
import kotlinx.android.synthetic.main.layout_category_dialog.view.*
import kotlinx.android.synthetic.main.layout_write_usedarticle_uploadimages.view.*
import kotlinx.android.synthetic.main.toolbar.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody


class WriteUsedArticleActivity : AppCompatActivity(), RemoveItem {
    private val TAG = "WriteUsedArticleActivity"
    private lateinit var categoryList : Array<String>
    private val PICK_IMAGE_MULTIPLE = 1
    private lateinit var uploadImageAdapter : UploadImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_usedarticle)

        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        category_textview.setOnClickListener{
            showCategoryDialog()
        }
        toolbar_title?.text = "중고거래 글쓰기"

        article_content_edittext.hint = getString(R.string.used_content_hint, "도화동")
        upload_img_constrant.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_MULTIPLE
            )
        }

        uploadImageAdapter = UploadImageAdapter(this)
        photo_recycler_view.adapter = uploadImageAdapter
        photo_recycler_view.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.write_menu, menu)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
            R.id.menu_write_article_complete -> sendUsedArticle()
        }
        return super.onOptionsItemSelected(item);
    }

    private fun sendUsedArticle() {
        // Retrofit 2
        val usedItem = UsedItems("Pooh",
            "Mapo",
            Integer.parseInt(price_edittext.text.toString()),
            category_textview.text.toString(),
            title_edittext.text.toString(),
            article_content_edittext.text.toString())

        var body = MultipartBody.Builder()
            .addFormDataPart("nickname", usedItem.nickname)
            .addFormDataPart("id", "111")

        for(item in 0 until uploadImageAdapter.itemCount) {
            Log.d(TAG, "uri: ${uploadImageAdapter.getItem(item).path}")

            uploadImageAdapter.getItem(item).path?.toRequestBody("image/*".toMediaTypeOrNull())?.let {
                body.addFormDataPart("item_$item", "item_$item", it)
            }
        }
        val jsonString = Gson().toJson(usedItem)
        body.addFormDataPart("data", jsonString)

        val tmp = RestApi.setUsedArticle(body.build().parts)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe( {
                Log.d(TAG, "Result: $it")
                //this@WriteUsedArticleActivity.finish()
            }, {
                Log.d(TAG, it.localizedMessage)
            }
            )
        Log.d(TAG, "itemCount:${uploadImageAdapter.itemCount}, json:$jsonString, result: $tmp")

    }

    private fun showCategoryDialog() {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.layout_category_dialog, null)
        builder.setView(view)
        val alertDialog = builder.create()

        categoryList = resources.getStringArray(R.array.category_list)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoryList)

        view.category_listview.apply {
            this.adapter = adapter
            this.setOnItemClickListener { parent, view, position, id ->
                this@WriteUsedArticleActivity.category_textview?.text = categoryList[position]
                alertDialog.dismiss()
            }
        }
        alertDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult ${data?.clipData}")
        try {
            // When an Image is picked
            if (requestCode === PICK_IMAGE_MULTIPLE && resultCode === RESULT_OK) {
                // Get the Image from data
                if (data?.clipData != null) {
                    val mClipData: ClipData? = data.clipData
                    for (i in 0 until mClipData!!.itemCount) {
                        val item = mClipData.getItemAt(i)
                        val uri: Uri = item.uri

                        uploadImageAdapter.addItem(uri)
                    }
                    image_count.text = "${uploadImageAdapter.itemCount} / 10"
                }
            } else {
                Toast.makeText(
                    this, "You haven't picked Image",
                    Toast.LENGTH_LONG
                ).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Something went wrong : $e", Toast.LENGTH_LONG)
                .show()
            Log.d(TAG, "onActivityResult, error : $e")
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRemovedItem() {
        image_count.text = "${uploadImageAdapter.itemCount} / 10"
    }
}

class UploadImageAdapter(private var removeItem : RemoveItem) : RecyclerView.Adapter<UploadImageAdapter.UploadImageViewHolder>() {
    private var mUploadImagesItems = ArrayList<Uri>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_write_usedarticle_uploadimages, parent, false)
        return UploadImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: UploadImageViewHolder, position: Int) {
        val context = holder.itemView.context
        Glide.with(context).load(mUploadImagesItems[position]).into(holder.thumbnail)

        holder.remove.setOnClickListener {
            removeItem(position)
            onRemovedItem()
            Toast.makeText(context, "삭제", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = mUploadImagesItems.size

    fun getItem(position : Int) : Uri {
        return mUploadImagesItems[position]
    }

    fun addItem(uri : Uri) {
        if(!mUploadImagesItems.contains(uri))
            mUploadImagesItems.add(uri)

        notifyDataSetChanged()
    }

    private fun removeItem(position : Int) {
        mUploadImagesItems.removeAt(position)
        notifyDataSetChanged()
    }

    private fun onRemovedItem() {
        removeItem.onRemovedItem()
    }

    class UploadImageViewHolder(item : View) : RecyclerView.ViewHolder(item) {
        val thumbnail = item.thumbnail_upload_img
        val remove = item.remove_upload_img
    }
}

interface RemoveItem {
    fun onRemovedItem()
}