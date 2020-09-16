package com.stegnerd.jeopardy.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.stegnerd.jeopardy.data.api.ApiClientImpl
import com.stegnerd.jeopardy.test.util.MainCoroutineRule
import com.stegnerd.jeopardy.test.util.fakes.FakeData
import com.stegnerd.jeopardy.util.Status
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import retrofit2.Response

@ExperimentalCoroutinesApi
class RepositoryImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private lateinit var repository: RepositoryImpl

    @RelaxedMockK
    lateinit var mockApiClient: ApiClientImpl

    val errorString: String = "Unit test error"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        repository = RepositoryImpl(mockApiClient)
    }

    @After
    fun cleanUp(){
        clearAllMocks()
    }

    @Test
    fun getRandomCategories_getsCategories_whenApiSuccessful() = runBlockingTest{
        // Given
        coEvery { mockApiClient.getRandomCategories() } returns Response.success(FakeData.fakeCategoryList)

        // When
        val result = repository.getRandomCategories()

        // Then
        Assert.assertEquals(5, result.data?.count())
        Assert.assertEquals(null, result.message)
        Assert.assertEquals(Status.SUCCESS, result.status)
    }

    @Test
    fun getRandomCategories_returnsError_whenApiNotSuccessful() = runBlockingTest{
        // Given

        // When
        var result = repository.getRandomCategories()

        // Then
    }

    @Test
    fun getRandomCategories_returnsError_whenApiClientException(){

    }

    @Test
    fun getQuestionByCategory_returnsQuestions_whenApiSuccessful(){

    }

    @Test
    fun getQuestionByCategory_returnsError_whenApiNotSuccessful(){

    }

    @Test
    fun getQuestionByCategory_returnsError_whenApiException() {

    }

    @Test
    fun getRandomQuestion_returnsQuestion_whenApiSuccessful(){

    }

    @Test
    fun getRandomQuestion_returnsError_whenApiNotSuccessful(){

    }

    @Test
    fun getRandomQuestion_returnsError_whenApiException() {

    }
}