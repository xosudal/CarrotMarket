package com.study.carrotmarket

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.study.carrotmarket.setting.activity.*
import com.study.carrotmarket.setting.model.UserData
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_mycarrot.*
import kotlinx.android.synthetic.main.fragment_mycarrot.view.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class MyCarrotFragment : Fragment() {
    var fragmentView: View? = null

    val PICK_IMAGE_FROM_ALBUM = 0

    var photoUri: Uri? = null

    lateinit var auth: FirebaseAuth

    lateinit var storage:FirebaseStorage

    lateinit var firestore:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        firestore = FirebaseFirestore.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentView = LayoutInflater.from(activity).inflate(R.layout.fragment_mycarrot, container, false).apply {
            mycarrot_show_profile_button?.setOnClickListener {
                startActivity(Intent(context, ProfileActivity::class.java))
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
                startActivity(Intent(context,KeyWordNotifyActivity::class.java))
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
                val photoPickerIntent = Intent(Intent.ACTION_PICK).apply {
                    type = "image/*"
                }
                startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)
            }
        }
        activity?.toolbar_title?.text = "나의 당근"
        return fragmentView
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_FROM_ALBUM) {
            if (resultCode == Activity.RESULT_OK) {
                photoUri = data?.data
                mycarrot_imageview_userImage.apply {
                    setImageURI(photoUri)
                    background = ShapeDrawable(OvalShape())
                    clipToOutline = true
                }
                uploadProfilePhoto()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun uploadProfilePhoto() {
        val timeStamp = SimpleDateFormat("yyyyMMDD_HHmmss").format(Date())
        var imageFileName = "IMAGE_$timeStamp.png"

        var storageReference = storage.reference.child("ProfileImage").child(imageFileName)

        photoUri?.let {
            storageReference.putFile(it).continueWithTask {
                return@continueWithTask storageReference.downloadUrl
            }
        }?.addOnSuccessListener {
            val userData = UserData().apply {
                imageUri = it.toString()
                uid = auth.currentUser?.uid
                userId = auth.currentUser?.email
            }
            firestore.collection("ProfileImage").document(userData.uid.toString()).set(userData)
            Toast.makeText(context,"upload success",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        getProfile()
    }

    private fun getProfile() {
        if (auth.currentUser == null) {
            mycarrot_imageview_userImage.setImageResource(R.drawable.ic_baseline_face_24)
            mycarrot_userInfo_tv.apply {
                text = "로그인이 필요합니다"
                typeface = Typeface.DEFAULT_BOLD
            }
            return
        }
        firestore.collection("ProfileImage").document(auth.currentUser?.uid.toString()).get().addOnSuccessListener {
            if (it == null) return@addOnSuccessListener
            if (it.data != null) {
                Glide.with(this).load(it.data!!["imageUri"]).apply(RequestOptions().circleCrop()).into(mycarrot_imageview_userImage)
                mycarrot_userInfo_tv.text = "${it.data!!["userId"]}\n${it.data!!["uid"]}"
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
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