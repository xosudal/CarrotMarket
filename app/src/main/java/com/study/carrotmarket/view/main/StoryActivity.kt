package com.study.carrotmarket.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.MainLivingCardViewItem
import com.study.carrotmarket.constant.Reply
import com.study.carrotmarket.view.main.adapter.ReplyImpl
import com.study.carrotmarket.view.main.adapter.StoryRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_story.*
import kotlinx.android.synthetic.main.toolbar.*

class StoryActivity : AppCompatActivity(), ReplyImpl {
    private val STORY_EDIT = 1000
    private lateinit var storyRecyclerView:StoryRecyclerViewAdapter
    private var replyList:ArrayList<Reply>  = arrayListOf(
        Reply("1", "첫 번째 내용", 1,0),
        Reply("2", "대 댓글 1", 1,1),
        Reply("3", "대 댓글 2", 1,2),
        Reply("4", "비자더리밪더ㅣ랍저다ㅣ", 2,0),
        Reply("5", "ㅁㄴㅇ리ㅏ먼이라ㅓ", 3,0),
        Reply("6", "대 댓글 3", 3,1),
        Reply("6", "댓글 4", 4,0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
        settingToolbar()
        storyRecyclerView = StoryRecyclerViewAdapter(this)
        story_recyclerview.apply {
            adapter = storyRecyclerView
        }
        addReply()
    }

    override fun onResume() {
        super.onResume()
        storyRecyclerView.setContent(intent.getStringExtra("PROFILE"), intent.getParcelableExtra("CONTENT")!!)
        storyRecyclerView.changeList(replyList)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_story, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun addReply() {
        send_btn.setOnClickListener {
            if (edit_tv.text != null) {
                val next:Int = if (replyList.isEmpty()) {
                    0
                } else {
                    replyList[replyList.lastIndex].parentIndex
                }
                Log.d("test","next : $next")
                val reply = Reply("nick", edit_tv.text.toString(), next+1, 0)
                replyList.add(reply)
                storyRecyclerView.changeList(replyList)
                edit_tv.text = null
            } else {
                Toast.makeText(applicationContext, "Please Input Text!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun callReplyActivity(list: ArrayList<Reply>, parent: Int) {
        val intent = Intent(applicationContext,StoryReplyActivity::class.java).apply {
            putExtra("PARENT",parent)
            putExtra("LIST",replyList)
        }
        startActivityForResult(intent,STORY_EDIT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == STORY_EDIT) {
            replyList = data?.getParcelableArrayListExtra<Reply>("LIST") as ArrayList<Reply>
        }
    }
}