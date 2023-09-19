package com.example.transactions

import android.view.View
import android.widget.EditText

fun View.isShow(canShow: Boolean) {
    this.visibility = if (canShow) View.VISIBLE else View.GONE
}

fun EditText.isFilled(): Boolean {
    return this.text.toString().isNotBlank()
}