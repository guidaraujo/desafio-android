package com.picpay.desafio.android.util

import android.view.View

fun View.toggleVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}