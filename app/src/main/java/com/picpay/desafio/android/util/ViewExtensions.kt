package com.picpay.desafio.android.util

import android.view.View
import android.widget.ImageView
import com.picpay.desafio.android.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun ImageView.load(
    imageURL: String,
    success: (() -> Unit)? = null,
    failure: ((Exception?) -> Unit)? = null
) {
    Picasso.get()
        .load(imageURL)
        .error(R.drawable.ic_round_account_circle)
        .into(this, object: Callback {
            override fun onSuccess() {
                success?.invoke()
            }

            override fun onError(e: Exception?) {
                failure?.invoke(e)
            }
        })
}