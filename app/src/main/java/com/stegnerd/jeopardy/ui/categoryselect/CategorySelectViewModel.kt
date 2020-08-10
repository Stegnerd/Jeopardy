package com.stegnerd.jeopardy.ui.categoryselect

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.stegnerd.jeopardy.data.local.Repository
import com.stegnerd.jeopardy.data.model.Category
import com.stegnerd.jeopardy.util.Constants
import com.stegnerd.jeopardy.util.NetworkHelper
import com.stegnerd.jeopardy.util.Result
import com.stegnerd.jeopardy.util.Status
import kotlinx.coroutines.launch

/**
 * ViewModel for the [CategorySelectFragment]. Contains a RecyclerView for the list of [Category]
 *
 * Injects dependencies with Dagger hilt [ViewModelInject].
 */
class CategorySelectViewModel @ViewModelInject constructor(private val repository: Repository, private val networkHelper: NetworkHelper): ViewModel() {

    /**
     * List of [Category] that can up updated via [Repository]
     */
    private val _categories = MutableLiveData<Result<List<Category>>>()

    /**
     * This is the list of [Category]. Can be used in the ui.
     */
    val categories: LiveData<Result<List<Category>>> = _categories

    /**
     * Flag to determine if retrieving data is done or not.
     */
    private val _loading = MutableLiveData<Boolean>()

    /**
     * Flag to determine if retrieving data. Can be used in the ui.
     */
    val loading: LiveData<Boolean> = _loading

    // used by the layout to determine when the list of items is empty
    val empty: LiveData<Boolean> = Transformations.map(_categories) {
        it.data?.isEmpty()
    }

/*    init {
        getCategories()
    }*/

    /**
     * Gets a random list of [Category] from [Repository].
     */
    fun getCategories() {
        viewModelScope.launch {
            // Set the ui to think it is loading and not render the list
            _loading.value = true
            // If connected to the internet is some fashion try to get categories
            if(networkHelper.isNetworkConnected()){
                    repository.getRandomCategories().let {
                    if(it.status == Status.SUCCESS){
                        // stop the loading flag and return the result
                        _loading.value = false
                        _categories.value = it
                    }else {
                        _loading.value = false
                        _categories.value = Result.error(it.message!!, null)
                    }
                }
            }else {
                _loading.value = false
                _categories.value = Result.error(Constants.NetworkConnectionError, null)
            }
        }
    }

}