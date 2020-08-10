package com.stegnerd.jeopardy.ui.categoryselect

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.stegnerd.jeopardy.data.local.RepositoryImpl
import com.stegnerd.jeopardy.data.model.Category
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
 * Unit tests for the [CategorySelectViewModel]
 *
 * We use relaxed mocks so that we do not have to specify every property of the object being mocked
 */
@ExperimentalCoroutinesApi
class CategorySelectViewModelTest  {

    // Executes tasks in Architecture components in the same thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Overrides Dispatchers.Main used in Coroutines and replaces it with a test thread.
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    // System under test
    private lateinit var viewModel: CategorySelectViewModel

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
    lateinit var  dataObserver: Observer<Result<List<Category>>>

    val errorString: String = "Unit test error"

    @Before
    fun setup(){
        MockKAnnotations.init(this)
        coEvery { mockNetworkHelper.isNetworkConnected() } returns true
        viewModel = CategorySelectViewModel(mockRepository, mockNetworkHelper)

        // Attach observers so that we can watch for events being emitted by live data
        viewModel.loading.observeForever(loadingObserver)
        viewModel.categories.observeForever(dataObserver)
    }

    @After
    fun cleanUp(){
        // Wipe all mocks and remove observers looking at data
        clearAllMocks()
        viewModel.loading.removeObserver(loadingObserver)
        viewModel.categories.removeObserver(dataObserver)
    }

    @Test
    fun getCategories_ReturnsListCategory_WhenSuccessful(){
        // Given
        val expectedState: Status = Status.SUCCESS
        coEvery { mockRepository.getRandomCategories() } returns Result.success(FakeData.fakeCategoryList)

        // When
        viewModel.getCategories()

        // Then
        Assert.assertEquals(expectedState, viewModel.categories.value?.status)

        verify { loadingObserver.onChanged(true) }
        verify { dataObserver.onChanged(viewModel.categories.value) }
        verify { loadingObserver.onChanged(false) }
        coVerify { mockRepository.getRandomCategories() }

    }

    @Test
    fun getCategories_ReturnsError_WhenErrorRetrievingData(){
        // Given
        val expectedState: Status = Status.ERROR
        val expectedErrorMessage: String = errorString
        coEvery { mockRepository.getRandomCategories() } returns Result.error(errorString, null)

        // When
        viewModel.getCategories()

        // Then
        Assert.assertEquals(expectedState, viewModel.categories.value?.status)
        Assert.assertEquals(expectedErrorMessage, viewModel.categories.value?.message)

        verify { loadingObserver.onChanged(true) }
        verify { dataObserver.onChanged(viewModel.categories.value) }
        verify { loadingObserver.onChanged(false) }
        coVerify { mockRepository.getRandomCategories() }
    }

    @Test
    fun getCategories_ReturnsError_WhenNotConnectToInternet(){
        // Given
        val expectedState: Status = Status.ERROR
        val expectedErrorMessage: String = Constants.NetworkConnectionError
        every { mockNetworkHelper.isNetworkConnected() } returns  false

        // When
        viewModel.getCategories()

        // Then
        Assert.assertEquals(expectedState, viewModel.categories.value?.status)
        Assert.assertEquals(expectedErrorMessage, viewModel.categories.value?.message)

        verify { loadingObserver.onChanged(true) }
        verify { dataObserver.onChanged(viewModel.categories.value) }
        verify { loadingObserver.onChanged(false) }
        coVerify(exactly = 0) { mockRepository.getRandomCategories() }
    }
}