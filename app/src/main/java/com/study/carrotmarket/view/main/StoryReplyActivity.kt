package com.study.carrotmarket.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.Reply
import com.study.carrotmarket.view.main.adapter.StoryReplyRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_story_reply_acitivty.*
import kotlinx.android.synthetic.main.toolbar.*

class StoryReplyActivity : AppCompatActivity() {
    private lateinit var replyList:ArrayList<Reply>
    private lateinit var storyReplyRecyclerViewAdapter: StoryReplyRecyclerViewAdapter
    private var parent:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_reply_acitivty)
        settingToolbar()
        replyList = intent.getParcelableArrayListExtra<Reply>("LIST") as ArrayList<Reply>
        parent = intent.getIntExtra("PARENT",100)
        Log.d("test","list : $replyList, parent : ${intent.getIntExtra("PARENT",100)}")
        storyReplyRecyclerViewAdapter = StoryReplyRecyclerViewAdapter()
        story_reply_recyclerview.apply {
            adapter = storyReplyRecyclerViewAdapter
        }
        addReply()
    }

    override fun onResume() {
        super.onResume()
        storyReplyRecyclerViewAdapter.apply {
            parent = intent.getIntExtra("PARENT",100)
            changeList(replyList)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val i = Intent(applicationContext, StoryActivity::class.java).apply {
                    putExtra("LIST",replyList)
                }
                setResult(RESULT_OK, i)
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun addReply() {
        Log.d("test","???")
        reply_send_btn.setOnClickListener {
            if (reply_edit_tv.text != null) {
                val temp = replyList.filter { it.parentIndex == parent }
                val childIndex = temp[temp.lastIndex].childIndex
                val reply = Reply("testChild", reply_edit_tv.text.toString(), parent, childIndex+1)
                val nextIndex = findIndex()
                if (nextIndex == 0) {
                    replyList.add(reply)
                } else {
                    replyList.add(nextIndex, reply)
                }
                storyReplyRecyclerViewAdapter.changeList(replyList)
                reply_edit_tv.text = null
            } else {
                Toast.makeText(applicationContext, "Please Input Text!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun findIndex():Int {
        val temp = replyList.filter { it.parentIndex == parent + 1 }
        return if (temp.isEmpty()) {
            0
        } else {
            replyList.indexOf(temp[0])
        }
    }
}