package com.picpay.desafio.android.presentation.contact_list

import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.util.load
import com.picpay.desafio.android.util.setVisible

class ContactListItemViewHolder(
    private val binding: ListItemUserBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(user: User) = with(binding) {
        name.text = user.name
        username.text = user.username
        progressBar.setVisible(true)
        picture.load(
            imageURL = user.imageURL,
            success = {
                progressBar.setVisible(false)
            },
            failure = {
                progressBar.setVisible(false)
            }
        )
    }
}