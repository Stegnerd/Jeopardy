package com.stegnerd.jeopardy.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.stegnerd.jeopardy.data.api.ApiClientImpl
import com.stegnerd.jeopardy.test.util.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
class RepositoryImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @RelaxedMockK
    lateinit var mockApiClient: ApiClientImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun cleanUp(){
        clearAllMocks()
    }
}