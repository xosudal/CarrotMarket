package com.study.carrotmarket.presenter.chat

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import com.google.gson.Gson
import com.study.carrotmarket.constant.UsedItems
import com.study.carrotmarket.constant.WriteUsedArticleContract
import com.study.carrotmarket.model.CarrotMarketDataRepository
import com.study.carrotmarket.view.chat.UploadImageAdapter
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class WriteUsedArticlePresenter(private val context : Context, private val view: WriteUsedArticleContract.View) : WriteUsedArticleContract.Presenter {
    private val TAG = WriteUsedArticlePresenter::class.java.simpleName

    override fun sendUsedArticle(usedItem: UsedItems, uploadImageAdapter: UploadImageAdapter): Boolean {
        val body = MultipartBody.Builder()
            .addFormDataPart("nickname", usedItem.nickname)

        for(item in 0 until uploadImageAdapter.itemCount) {
            getRealPathFromUri(uploadImageAdapter.getItem(item))?.let {
                val tmpFile = File(it)
                val requestBody = tmpFile.asRequestBody("image/*".toMediaTypeOrNull())
                body.addFormDataPart("item_$item", "item_$item.png", requestBody)
            }
        }
        val jsonString = Gson().toJson(usedItem)
        body.addFormDataPart("data", jsonString)

        val dispose = CarrotMarketDataRepository.sendUsedArticle(body.build().parts)
            .subscribe({
                Log.d(TAG, "Result: $it")
                view.onResult(
                    WriteUsedArticleContract.ResultType.UPLOAD,
                    WriteUsedArticleContract.ResultCode.OK
                )
            }, {
                view.onResult(
                    WriteUsedArticleContract.ResultType.UPLOAD,
                    WriteUsedArticleContract.ResultCode.ERROR
                )
                Log.d(TAG, it.localizedMessage!!)
            })
        return true
    }

    override fun getSimpleUsedItem(): Boolean {
        val composite = CompositeDisposable()
        composite.add(
            CarrotMarketDataRepository.getSimpleUsedItem().subscribe {
                view.setUsedItemList(it)
            }
        )
        return true
    }

    private fun getRealPathFromUri(uri: Uri): String? {
        // DocumentProvider
        if (isMediaDocument(uri)) { // Recent path
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":").toTypedArray()
            val type = split[0] // Content:
            var contentUri: Uri? = null
            if ("image" == type) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI // "media/external/images/media"
            }
            val selection = "_id=?"
            val selectionArgs = arrayOf(split[1])
            Log.d(TAG, "docID: $docId, type:$type, uri:$contentUri")
            return getDataColumn(context, contentUri, selection, selectionArgs);
        } else { // Gallery, Photo path
            val projection = arrayOf("_data")
            val cursor : Cursor? = context.contentResolver.query(uri, projection, null, null, null)

            cursor?.let {
                cursor.moveToFirst()
                val columnIdx = cursor.getColumnIndexOrThrow("_data")
                return  cursor.getString(columnIdx)
            }?:run {
                Log.e(TAG, "cursor is null")
            }
        }
        return null
    }

    private fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)
        try {
            cursor = uri?.let {
                context.contentResolver.query(
                    it, projection, selection, selectionArgs, null)
            }
            if (cursor != null && cursor.moveToFirst()) {
                val index: Int = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }
}