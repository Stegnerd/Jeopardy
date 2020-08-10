package com.stegnerd.jeopardy.ui.question

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.stegnerd.jeopardy.data.local.RepositoryImpl
import com.stegnerd.jeopardy.data.model.Question
import com.stegnerd.jeopardy.test.util.MainCoroutineRule
import com.stegnerd.jeopardy.test.util.fakes.FakeData
import com.stegnerd.jeopardy.util.Constants
import com.stegnerd.jeopardy.util.NetworkHelper
import com.stegnerd.jeopardy.util.Result
import com.stegnerd.jeopardy.util.Status
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*


/**
 * Unit tests for the [QuestionViewModel].
 *
 * We used relaxed mocks so that we don't have to be strict with the setup of models, and it won't through errors.
 */
@ExperimentalCoroutinesApi
class QuestionViewModelTest {

    // Executes tasks in Architecture components in the same thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Overrides Dispatchers.Main used in Coroutines and replaces it with a test thread.
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    // System under test
    private lateinit var viewModel: QuestionViewModel

    @RelaxedMockK
    lateinit var mockNetworkHelper: NetworkHelper

    @RelaxedMockK
    lateinit var mockRepository: RepositoryImpl

    /**
     * Used to watch for data changing on the loading property of the viewModel.
     */
    @RelaxedMockK
    lateinit var loadingObserver: Observer<Boolean>

    /**
     * Used to watch for data changing on the question property of the viewModel.
     */
    @RelaxedMockK
    lateinit var dataObserver: Observer<Result<Question>>

    val errorString: String = "Unit test error"

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        every { mockNetworkHelper.isNetworkConnected() } returns true
        viewModel = QuestionViewModel(mockRepository, mockNetworkHelper)

        // Attach observers so that we can watch for events being omitted
        viewModel.loading.observeForever(loadingObserver)
        viewModel.question.observeForever(dataObserver)
    }

    @After
    fun cleanUp(){
        // Wipe all mocks and remove observers looking at data
        clearAllMocks()
        viewModel.loading.removeObserver(loadingObserver)
        viewModel.question.removeObserver(dataObserver)
    }

    @Test
    fun loadQuestion_GetsRandomQuestion_WhenNoCategoryIdPassedIn() {
        // Given
        val categoryId: Int? = null
        coEvery { mockRepository.getRandomQuestion() } returns Result.success(FakeData.fakeRandomQuestion)

        // When
        viewModel.loadQuestion(categoryId)

        // Then
        val expectedState: Status = Status.SUCCESS
        Assert.assertEquals(expectedState,viewModel.question.value?.status)

        verify { loadingObserver.onChanged(true) }
        verify { dataObserver.onChanged(viewModel.question.value) }
        verify { loadingObserver.onChanged(false) }
        coVerify { mockRepository.getRandomQuestion() }
    }

    @Test
    fun loadQuestion_GetsRandomQuestion_ReturnsError_WhenErrorRetrievingData_CategoryIdNotPassedIn(){
        // Given
        val categoryId: Int? = null
        val expectedErrorMessage: String = errorString
        coEvery { mockRepository.getRandomQuestion() } returns Result.error(errorString, null)

        // When
        viewModel.loadQuestion(categoryId)

        // Then
        val expectedState: Status = Status.ERROR
        Assert.assertEquals(expectedState, viewModel.question.value?.status)
        Assert.assertEquals(expectedErrorMessage, viewModel.question.value?.message)

        verify { loadingObserver.onChanged(true) }
        verify { loadingObserver.onChanged(false) }
        coVerify { mockRepository.getRandomQuestion() }
    }

    @Test
    fun loadQuestion_GetsRandomQuestion_ReturnsError_WhenNotConnectedToInternet_CategoryIdNotPassedIn(){
        // Given
        val catgegoryId : Int? = null
        val expectedErrorMessage: String = Constants.NetworkConnectionError
        every { mockNetworkHelper.isNetworkConnected() } returns false

        // When
        viewModel.loadQuestion(catgegoryId)

        // Then
        val expectedState: Status = Status.ERROR
        Assert.assertEquals(expectedState, viewModel.question.value?.status)
        Assert.assertEquals(expectedErrorMessage, viewModel.question.value?.message)

        verify { loadingObserver.onChanged(true) }
        verify { loadingObserver.onChanged(false) }
        coVerify(exactly = 0) { mockRepository.getRandomQuestion() }
    }

    @Test
    fun loadQuestion_GetsQuestionByCategory_WhenCategoryIdPassedIn() {
        // Given
        val categoryId: Int? = FakeData.category1.id
        coEvery { mockRepository.getQuestionsByCategory(categoryId!!) } returns Result.success(FakeData.fakeQuestionCategoryOneList)

        // When
        viewModel.loadQuestion(categoryId)

        // Then
        val expectedState: Status = Status.SUCCESS
        Assert.assertEquals(expectedState, viewModel.question.value?.status)

        verify { loadingObserver.onChanged(true) }
        verify { dataObserver.onChanged(viewModel.question.value) }
        verify { loadingObserver.onChanged(false) }
        coVerify { mockRepository.getQuestionsByCategory(categoryId!!) }
    }

    @Test
    fun loadQuestion_GetsQuestionByCategory_ReturnsError_WhenErrorRetrievingData_CategoryIdPassedIn(){
        // Given
        val categoryId: Int? = FakeData.category1.id
        val expectedErrorMessage: String = errorString
        coEvery { mockRepository.getQuestionsByCategory(categoryId!!) } returns Result.error(errorString, null)

        // When
        viewModel.loadQuestion(categoryId)

        // Then
        val expectedState: Status = Status.ERROR
        Assert.assertEquals(expectedState, viewModel.question.value?.status)
        Assert.assertEquals(expectedErrorMessage, viewModel.question.value?.message)

        verify { loadingObserver.onChanged(true) }
        verify { dataObserver.onChanged(viewModel.question.value) }
        verify { loadingObserver.onChanged(false) }
        coVerify { mockRepository.getQuestionsByCategory(categoryId!!) }
    }

    @Test
    fun loadingQuestion_GetsQuestionByCategory_ReturnsError_WhenNotConnectedToInternet_CategoryIdPassedIn(){
        // Given
        val categoryId: Int? = FakeData.category1.id
        val expectedErrorMessage: String = Constants.NetworkConnectionError
        every { mockNetworkHelper.isNetworkConnected() } returns false

        // When
        viewModel.loadQuestion(categoryId)

        // Then
        val expectedState: Status = Status.ERROR
        Assert.assertEquals(expectedState, viewModel.question.value?.status)
        Assert.assertEquals(expectedErrorMessage, viewModel.question.value?.message)

        verify { loadingObserver.onChanged(true) }
        verify { dataObserver.onChanged(viewModel.question.value) }
        verify { loadingObserver.onChanged(false) }
        coVerify(exactly = 0) { mockRepository.getQuestionsByCategory(categoryId!!) }
    }

    @Test
    fun validate_returnsTrue_WhenAnswerCorrect() {
        // Given
        val categoryId: Int? = null
        viewModel.userAnswer = "Wrong Answer"
        coEvery { mockRepository.getRandomQuestion() } returns Result.success(FakeData.fakeRandomQuestion)

        // When
        viewModel.loadQuestion(categoryId)
        val result = viewModel.validate()

        // Then
        Assert.assertEquals(result, false)
    }

    @Test
    fun validate_returnsFalse_WhenAnswerWrong() {
        // Given
        val categoryId: Int? = null
        viewModel.userAnswer = "Arkansas"
        coEvery { mockRepository.getRandomQuestion() } returns Result.success(FakeData.fakeRandomQuestion)

        // When
        viewModel.loadQuestion(categoryId)
        val result = viewModel.validate()

        // Then
        Assert.assertEquals(result, true)
    }

}
