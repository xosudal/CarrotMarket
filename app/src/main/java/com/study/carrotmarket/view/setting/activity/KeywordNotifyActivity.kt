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
import kotlinx.android.synthetic.main.activity_key_word_notify.*
import kotlinx.android.synthetic.main.layout_keyword.view.*
import kotlinx.android.synthetic.main.toolbar.*
import org.json.JSONArray

class KeywordNotifyActivity : AppCompatActivity() {
    private val MAXLIST = 30
    var keywordList = arrayListOf<String>()
    private lateinit var keywordAdapter:KeyWordNotifyRecyclerView

    private lateinit var presenter:KeywordNotifyPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_key_word_notify)
        settingToolbar()

        presenter = KeywordNotifyPresenter().apply {
            model = KeywordNotifyModel(this@KeywordNotifyActivity)
        }

        keywordAdapter = KeyWordNotifyRecyclerView()
        keyword_recyclerview.apply {
            adapter = keywordAdapter
            layoutManager = FlexboxLayoutManager(context).apply {
                flexWrap = FlexWrap.WRAP
            }
            addItemDecoration(RecyclerViewDecoration(10))
        }

        keyword_edit_register_tv.setOnClickListener {
            if (presenter.getSize() == MAXLIST) {
                Toast.makeText(this,"키워드를 더 이상 입력할 수 없습니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (keyword_edit_tv.text.toString().isEmpty()) {
                Toast.makeText(this,"키워드를 입력해주세요",Toast.LENGTH_SHORT).show()
            } else {
                presenter.addKeyword(keyword_edit_tv.text.toString())
                keyword_edit_tv.text = null
                keyword_register_count.text = getString(R.string.keyword_count, keywordList.size)
                keywordAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.loadKeyword()
        keyword_register_count.text = getString(R.string.keyword_count, presenter.getSize())
    }

    override fun onPause() {
        super.onPause()
        presenter.saveKeyword()
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

    inner class KeyWordNotifyRecyclerView : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = layoutInflater.inflate(R.layout.layout_keyword, parent,false)

            return KeyWordViewHolder(view)
        }

        inner class KeyWordViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = (holder as KeyWordViewHolder).itemView
            viewHolder.tv_keyword.text =keywordList[position]
            viewHolder.keyword_iv_layout.setOnClickListener {
                TODO()
                keywordList.removeAt(position)
                keyword_register_count.text = getString(R.string.keyword_count, keywordList.size)
                notifyDataSetChanged()
            }
        }

        override fun getItemCount(): Int {
            return keywordList.size
        }
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
}