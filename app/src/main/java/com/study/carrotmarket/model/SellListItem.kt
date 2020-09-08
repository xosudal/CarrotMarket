package com.study.carrotmarket.model

import android.graphics.drawable.Drawable
import java.util.*

class SellListItem ( val itemName: String,
                     val posterDrawable: Drawable,
                     val itemAddress: String,
                     val itemTimeStamp: Date,
                     val itemPrice: Int
)