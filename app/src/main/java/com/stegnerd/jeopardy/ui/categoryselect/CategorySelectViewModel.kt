package com.stegnerd.jeopardy.ui.categoryselect

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stegnerd.jeopardy.data.local.Repository
import com.stegnerd.jeopardy.data.model.Category
import com.stegnerd.jeopardy.util.NetworkHelper
import com.stegnerd.jeopardy.util.Result
import kotlinx.coroutines.launch

class CategorySelectViewModel @ViewModelInject constructor(private val repository: Repository, private val networkHelper: NetworkHelper): ViewModel() {

    private val _categories = MutableLiveData<Result<List<Category>>>()
    val categories: LiveData<Result<List<Category>>> = _categories

    fun getCategories() {
        viewModelScope.launch {
            _categories.postValue(Result.loading(null))
            if(networkHelper.isNetworkConnected()){
                repository.getRandomCategories().let {
                    if(it.isSuccessful){
                        _categories.postValue(Result.success(it.body()))
                    }else {
                        _categories.postValue(Result.error(it.errorBody().toString(), null))
                    }
                }
            }else {
                _categories.postValue(Result.error("No internet connection", null))
            }
        }
    }

}