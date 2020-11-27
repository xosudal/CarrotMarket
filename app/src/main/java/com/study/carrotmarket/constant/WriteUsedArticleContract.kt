package com.study.carrotmarket.constant

import android.database.Observable
import com.study.carrotmarket.view.chat.UploadImageAdapter

interface WriteUsedArticleContract {
    enum class ResultType{
        UPLOAD
    }

    enum class ResultCode {
        OK,
        ERROR,
    }
    interface View {
        fun onResult(resultType : ResultType, resultCode : ResultCode)
        fun setUsedItemList(list : List<SimpleUsedItemResponse>)
    }

    interface Presenter {
        fun sendUsedArticle(usedItem: UsedItems, uploadImageAdapter: UploadImageAdapter): Boolean
        fun getSimpleUsedItem() : Boolean
    }
}