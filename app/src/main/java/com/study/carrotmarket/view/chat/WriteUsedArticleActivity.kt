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
import com.google.gson.Gson
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.SimpleUsedItemResponse
import com.study.carrotmarket.constant.UsedItems
import com.study.carrotmarket.constant.WriteUsedArticleContract
import com.study.carrotmarket.model.RestApi
import com.study.carrotmarket.presenter.chat.WriteUsedArticlePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_write_usedarticle.*
import kotlinx.android.synthetic.main.layout_category_dialog.view.*
import kotlinx.android.synthetic.main.layout_write_usedarticle_uploadimages.view.*
import kotlinx.android.synthetic.main.toolbar.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*


class WriteUsedArticleActivity : AppCompatActivity(), RemoveItem, WriteUsedArticleContract.View {
    private val TAG = WriteUsedArticleActivity::class.java.simpleName
    private lateinit var categoryList : Array<String>
    private val PICK_IMAGE_MULTIPLE = 1
    private lateinit var uploadImageAdapter : UploadImageAdapter

    private lateinit var presenter : WriteUsedArticlePresenter

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

        presenter = WriteUsedArticlePresenter(applicationContext, this)

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
/*        if(!isValidateChecker()) {
            Toast.makeText(this, "입력 값을 확인해 주세요.", Toast.LENGTH_SHORT).show()
            return
        }*/

        val usedItem = UsedItems("코코몽",
            "서울시 도화동",
            Integer.parseInt(price_edittext.text.toString()),
            category_textview.text.toString(),
            uploadImageAdapter.itemCount,
            title_edittext.text.toString(),
            article_content_edittext.text.toString())

        presenter.sendUsedArticle(usedItem, uploadImageAdapter)
    }

    private fun isValidateChecker() :Boolean {
        if(uploadImageAdapter.itemCount > 1 &&
            Integer.parseInt(price_edittext.text.toString()) > 0 &&
            category_textview.text.length > 0 &&
            title_edittext.text.length > 10 &&
            article_content_edittext.text.length > 30) return true

        Log.d(TAG, "Checker: ${Integer.parseInt(price_edittext.text.toString())}, ${category_textview.text.length}, ${title_edittext.text.length}, ${article_content_edittext.text.length}")
        return false;
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

    override fun onResult(
        resultType: WriteUsedArticleContract.ResultType,
        resultCode: WriteUsedArticleContract.ResultCode
    ) {
        Log.d(TAG, "ResultType : $resultType, ResultCode: $resultCode")
        Toast.makeText(this, "ResultType : $resultType, ResultCode: $resultCode", Toast.LENGTH_SHORT).show()
    }

    override fun setUsedItemList(list: List<SimpleUsedItemResponse>) {
        TODO("Not yet implemented")
    }
}

interface RemoveItem {
    fun onRemovedItem()
}