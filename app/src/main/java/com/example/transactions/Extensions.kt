package com.example.transactions

import android.view.View

fun View.isShow(canShow: Boolean) {
    this.visibility = if (canShow) View.VISIBLE else View.GONE
}