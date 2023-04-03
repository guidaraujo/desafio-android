package com.picpay.desafio.android.presentation.contact_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.databinding.FragmentContactListBinding
import com.picpay.desafio.android.util.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactListFragment: Fragment() {

    private val viewModel by viewModels<ContactListViewModel>()
    private val adapter: ContactListAdapter by lazy {
        ContactListAdapter()
    }
    private var binding: FragmentContactListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        subscribeUI()
    }

    private fun subscribeUI() = with(viewModel) {
        getContactList()
        contactList.observe(viewLifecycleOwner, adapter::submitList)
        onLoading.observe(viewLifecycleOwner, ::showLoading)
        onError.observe(viewLifecycleOwner, ::showErrorMessage)
    }

    private fun setupUI() = binding?.run {
        recyclerContacts.adapter = adapter
        recyclerContacts.layoutManager = LinearLayoutManager(root.context)
    }

    private fun showLoading(loading: Boolean) =
        binding?.progressBar?.setVisible(loading)

    private fun showErrorMessage(message: String) = binding?.run {
        Toast.makeText(root.context, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}