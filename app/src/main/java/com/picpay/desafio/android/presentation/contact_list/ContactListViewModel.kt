package com.picpay.desafio.android.presentation.contact_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.model.AsyncResult
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.usecase.GetContactListUseCase
import com.picpay.desafio.android.util.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val getContactListUseCase: GetContactListUseCase
): ViewModel() {

    private val _contactList = MutableLiveData<List<User>?>()
    val contactList: LiveData<List<User>?> get() = _contactList

    val onLoading = SingleEvent<Boolean>()
    val onError = SingleEvent<String>()

    fun getContactList() = viewModelScope.launch {
        getContactListUseCase().collect { result ->
            onLoading.value = result is AsyncResult.Loading
            if (result is AsyncResult.Success) {
                _contactList.value = result.value
            } else if (result is AsyncResult.Error) {
                onError.value = result.message
            }
        }
    }
}