package com.picpay.desafio.android.contact_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.domain.model.User

class ContactListAdapter : RecyclerView.Adapter<ContactListItemViewHolder>() {

    var users = emptyList<User>()
        set(value) {
            val result = DiffUtil.calculateDiff(
                ContactListDiffCallback(
                    field,
                    value
                )
            )
            result.dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_user, parent, false)

        return ContactListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactListItemViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size
}