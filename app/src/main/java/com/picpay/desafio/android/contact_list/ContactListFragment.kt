package com.picpay.desafio.android.contact_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.network.PicPayService
import com.picpay.desafio.android.databinding.FragmentContactListBinding
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.util.toggleVisibility
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class ContactListFragment: Fragment() {
    private lateinit var adapter: ContactListAdapter
    private var binding: FragmentContactListBinding? = null

    private val url = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

    private val gson: Gson by lazy { GsonBuilder().create() }

    private val okHttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(url)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private val service: PicPayService by lazy {
        retrofit.create(PicPayService::class.java)
    }

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
        subscribeUI()
    }

    private fun subscribeUI() = with(view) {}

    private fun setupContactList() = binding?.run {
        recyclerContacts.adapter = adapter
        recyclerContacts.layoutManager = LinearLayoutManager(root.context)
    }

    private fun showLoading(loading: Boolean) = binding?.progressBar?.toggleVisibility(loading)

    override fun onResume() {
        super.onResume()

        adapter = ContactListAdapter()
        binding?.recyclerContacts?.adapter = adapter
        binding?.recyclerContacts?.layoutManager = LinearLayoutManager(context)
        binding?.progressBar?.visibility = View.VISIBLE
        service.getUsers()
            .enqueue(object: Callback<List<User>> {
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    val message = getString(R.string.error)

                    binding?.progressBar?.visibility = View.GONE
                    binding?.recyclerContacts?.visibility = View.GONE

                    Toast.makeText(context, message, Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    binding?.progressBar?.visibility = View.GONE

                    adapter.users = response.body()!!
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}