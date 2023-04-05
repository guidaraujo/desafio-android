package com.picpay.desafio.android.presentation.contact_list

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.picpay.desafio.android.R
import com.picpay.desafio.android.util.RecyclerViewMatchers.atPosition
import com.picpay.desafio.android.domain.model.AsyncResult
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.usecase.GetContactListUseCase
import com.picpay.desafio.android.util.launchFragmentInHiltContainer
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class ContactListFragmentTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @BindValue
    val getContactListUseCase: GetContactListUseCase = mock()

    @Before
    fun start() {
        hiltAndroidRule.inject()
    }

    @Test
    fun shouldDisplayTitle_WhenOpenContactListFragment() = runTest {
        whenever(getContactListUseCase()).thenReturn(flow { AsyncResult.Success(listOf<User>()) })
        launchFragmentInHiltContainer<ContactListFragment>()
        onView(allOf(withId(R.id.text_title), withText(R.string.title)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayContactList_WhenReceiveContactListObject() = runTest {
        val response = listOf(
            User(name = "ui test 1", id = 1, username = "ui_test_1"),
            User(name = "ui test 2", id = 2, username = "ui_test_2"),
            User(name = "ui test 3", id = 3, username = "ui_test_3"),
        )
        whenever(getContactListUseCase()).thenReturn(flow { emit(AsyncResult.Success(response)) })
        launchFragmentInHiltContainer<ContactListFragment>()
        response.forEach {
            onView(withText(it.name))
                .check(matches(isDisplayed()))
            onView(withText(it.username))
                .check(matches(isDisplayed()))
        }
    }

    @Test
    fun shouldDisplayCorrectContactAtEachPosition_WhenReceiveContactListObject() = runTest {
        val response = listOf(
            User(name = "ui test 1", id = 1, username = "ui_test_1"),
            User(name = "ui test 2", id = 2, username = "ui_test_2"),
            User(name = "ui test 3", id = 3, username = "ui_test_3"),
        )
        whenever(getContactListUseCase()).thenReturn(flow { emit(AsyncResult.Success(response)) })
        launchFragmentInHiltContainer<ContactListFragment>()
        response.forEachIndexed { index, user ->
            val nameMatcher = allOf(withId(R.id.text_name), withText(user.name))
            val usernameMatcher = allOf(withId(R.id.text_username), withText(user.username))
            onView(withId(R.id.recycler_contacts))
                .check(matches(atPosition(index, hasDescendant(nameMatcher))))
                .check(matches(atPosition(index, hasDescendant(usernameMatcher))))
        }
    }
}