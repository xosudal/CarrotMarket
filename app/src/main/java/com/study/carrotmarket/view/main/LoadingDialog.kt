package com.study.carrotmarket.view.main

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.study.carrotmarket.R

class LoadingDialog constructor(context: Context) : Dialog(context) {
    init {
        setCanceledOnTouchOutside(false)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.layout_dialog)
    }
}