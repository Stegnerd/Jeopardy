package com.stegnerd.jeopardy.ui.question

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.stegnerd.jeopardy.test.util.fakes.FakeRepository
import com.stegnerd.jeopardy.util.NetworkHelper
import org.junit.Before
import org.junit.Rule

class QuestionViewModelTest {

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: QuestionViewModel
    private lateinit var mockNetworkHelper: NetworkHelper

    @Before
    fun setup() {
        mockNetworkHelper = mock {
            on {isNetworkConnected()}.doReturn(true)
        }

        viewModel = createQuestionViewModel()
    }


    private fun createQuestionViewModel(): QuestionViewModel{

        val repository = FakeRepository()
        val networkHelper = mockNetworkHelper

        return QuestionViewModel(repository, networkHelper)
    }

}