package com.stegnerd.jeopardy.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.stegnerd.jeopardy.data.api.ApiClientImpl
import com.stegnerd.jeopardy.test.util.MainCoroutineRule
import com.stegnerd.jeopardy.test.util.fakes.FakeData
import com.stegnerd.jeopardy.util.Constants
import com.stegnerd.jeopardy.util.Status
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.*
import retrofit2.Response
import java.util.concurrent.TimeoutException

@ExperimentalCoroutinesApi
class RepositoryImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private lateinit var repository: RepositoryImpl

    @RelaxedMockK
    lateinit var mockApiClient: ApiClientImpl

    private val categoryId: Int  = FakeData.category1.id

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
        coEvery { mockApiClient.getRandomCategories() } returns Response.error(404, Json.encodeToString(FakeData.emptyFakeCategoryList).toResponseBody("application/json".toMediaTypeOrNull()))

        // When
        val result = repository.getRandomCategories()

        // Then
        Assert.assertEquals(null, result.data)
        Assert.assertEquals(Status.ERROR, result.status)
    }

    @Test
    fun getRandomCategories_returnsError_whenApiClientException() = runBlockingTest{
        // Given
        coEvery { mockApiClient.getRandomCategories() } throws TimeoutException()

        // When
        val result = repository.getRandomCategories()

        // Then
        Assert.assertEquals(Constants.NetworkConnectionError, result.message)
        Assert.assertEquals(null, result.data)
        Assert.assertEquals(Status.ERROR, result.status)
    }

    @Test
    fun getQuestionByCategory_returnsQuestions_whenApiSuccessful() = runBlockingTest{
        // Given
        coEvery { mockApiClient.getQuestionsByCategory(categoryId) } returns Response.success(FakeData.fakeQuestionCategoryOneList)

        // When
        val result = repository.getQuestionsByCategory(categoryId)

        // Then
        Assert.assertEquals(1, result.data?.count())
        Assert.assertEquals(null, result.message)
        Assert.assertEquals(Status.SUCCESS, result.status)
    }

    @Test
    fun getQuestionByCategory_returnsError_whenApiNotSuccessful() = runBlockingTest{
        // Given
        coEvery { mockApiClient.getQuestionsByCategory(categoryId) } returns Response.error(404, Json.encodeToString(FakeData.emptyFakeQuestionCategoryList).toResponseBody("application/json".toMediaTypeOrNull()))

        // When
        val result = repository.getQuestionsByCategory(categoryId)

        // Then
        Assert.assertEquals(null, result.data)
        Assert.assertEquals(Status.ERROR, result.status)
    }

    @Test
    fun getQuestionByCategory_returnsError_whenApiException() = runBlockingTest{
        // Given
        coEvery { mockApiClient.getQuestionsByCategory(categoryId) } throws TimeoutException()

        // When
        val result = repository.getQuestionsByCategory(categoryId)

        // Then
        Assert.assertEquals(Constants.NetworkConnectionError, result.message)
        Assert.assertEquals(null, result.data)
        Assert.assertEquals(Status.ERROR, result.status)
    }

    @Test
    fun getRandomQuestion_returnsQuestion_whenApiSuccessful() = runBlockingTest{
        // Given
        coEvery { mockApiClient.getRandomQuestion() } returns Response.success(FakeData.fakeRandomQuestionList)

        // When
        val result = repository.getRandomQuestion()

        // Then
        Assert.assertEquals(FakeData.fakeRandomQuestion.category.id, result.data?.category?.id)
        Assert.assertEquals(Status.SUCCESS, result.status)
        Assert.assertEquals(null, result.message)
    }

    @Test
    fun getRandomQuestion_returnsError_whenApiNotSuccessful() = runBlockingTest{
        // Given
        coEvery { mockApiClient.getRandomQuestion() } returns Response.error(404, Json.encodeToString(FakeData.emptyFakeRandomQuestionList).toResponseBody("application/json".toMediaTypeOrNull()))

        // When
        val result = repository.getRandomQuestion()

        // Then
        Assert.assertEquals(null, result.data)
        Assert.assertEquals(Status.ERROR, result.status)
    }

    @Test
    fun getRandomQuestion_returnsError_whenApiException() = runBlockingTest{
        // Given
        coEvery { mockApiClient.getRandomQuestion() } throws TimeoutException()

        // When
        val result = repository.getRandomQuestion()

        // Then
        Assert.assertEquals(Constants.NetworkConnectionError, result.message)
        Assert.assertEquals(null, result.data)
        Assert.assertEquals(Status.ERROR, result.status)
    }
}