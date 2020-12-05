package com.study.carrotmarket.view.setting.activity

import android.graphics.Rect
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.study.carrotmarket.R
import com.study.carrotmarket.model.setting.KeywordNotifyModel
import com.study.carrotmarket.presenter.setting.KeywordNotifyPresenter
import com.study.carrotmarket.view.setting.adapter.KeyWordNotifyRecyclerViewAdapter
import com.study.carrotmarket.view.setting.adapter.Temp
import kotlinx.android.synthetic.main.activity_key_word_notify.*
import kotlinx.android.synthetic.main.layout_keyword.view.*
import kotlinx.android.synthetic.main.toolbar.*
import org.json.JSONArray

class KeywordNotifyActivity : AppCompatActivity(), Temp {
    private val MAXLIST = 30
    private lateinit var keywordAdapter:KeyWordNotifyRecyclerViewAdapter

    private lateinit var presenter:KeywordNotifyPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_key_word_notify)
        settingToolbar()

        presenter = KeywordNotifyPresenter()

        keywordAdapter = KeyWordNotifyRecyclerViewAdapter(this)
        keyword_recyclerview.apply {
            adapter = keywordAdapter
            layoutManager = FlexboxLayoutManager(context).apply {
                flexWrap = FlexWrap.WRAP
            }
            addItemDecoration(RecyclerViewDecoration(10))
        }

        keyword_edit_register_tv.setOnClickListener {
            if (keywordAdapter.itemCount == MAXLIST) {
                Toast.makeText(this,"키워드를 더 이상 입력할 수 없습니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (keyword_edit_tv.text.toString().isEmpty()) {
                Toast.makeText(this,"키워드를 입력해주세요",Toast.LENGTH_SHORT).show()
            } else {
                keywordAdapter.addKeyword(keyword_edit_tv.text.toString())
                keyword_edit_tv.text = null
                keyword_register_count.text = getString(R.string.keyword_count, keywordAdapter.itemCount)
                keywordAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        keywordAdapter.setKeywordList(presenter.loadKeyword())
        keyword_register_count.text = getString(R.string.keyword_count, keywordAdapter.itemCount)
    }

    override fun onPause() {
        super.onPause()
        presenter.saveKeyword(keywordAdapter.getKeywordList())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun settingToolbar() {
        setSupportActionBar(toolbar).apply {
            title = null
        }
        toolbar_title.text = "키워드 알림"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    inner class RecyclerViewDecoration(private val divHeight: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.top = divHeight
            outRect.right = divHeight
        }
    }

    override fun updateKeywordCount(size: Int) {
        keyword_register_count.text = getString(R.string.keyword_count, size)
    }

    override fun removeKeyword(position: Int) {
        //presenter.removeKeyword(position)
    }
}