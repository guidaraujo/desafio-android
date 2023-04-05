package com.picpay.desafio.android.presentation.contact_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.picpay.desafio.android.domain.model.AsyncResult
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.usecase.GetContactListUseCase
import com.picpay.desafio.android.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class ContactListViewModelTest{

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val ruleForLiveData = InstantTaskExecutorRule()

    private lateinit var viewModel: ContactListViewModel

    private val getContactListUseCase: GetContactListUseCase = mock()

    @Before
    fun setup() {
        viewModel = ContactListViewModel(getContactListUseCase)
    }

    @Test
    fun `SHOULD pass contact list to livedata WHEN request is successful `() = runTest {
        val contactListObserver: Observer<List<User>?> = mock()
        val contactListResponse = listOf<User>()
        whenever(getContactListUseCase()).thenReturn(flow {
            emit(AsyncResult.Success(contactListResponse))
        })

        with(viewModel){
            getContactList()
            contactList.observeForever(contactListObserver)
        }

        verify(contactListObserver).onChanged(contactListResponse)
    }

    @Test
    fun `SHOULD call loading event WHEN request contact list `() = runTest {
        val loadingObserver: Observer<Boolean> = mock()
        whenever(getContactListUseCase()).thenReturn(flow {
            emit(AsyncResult.Loading)
        })

        with(viewModel){
            getContactList()
            onLoading.observeForever(loadingObserver)
        }

        verify(loadingObserver).onChanged(true)
    }
}