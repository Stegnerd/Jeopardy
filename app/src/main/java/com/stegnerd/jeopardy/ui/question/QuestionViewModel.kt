package com.stegnerd.jeopardy.ui.question

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stegnerd.jeopardy.data.local.Repository
import com.stegnerd.jeopardy.data.model.Question
import com.stegnerd.jeopardy.util.NetworkHelper
import com.stegnerd.jeopardy.util.Result
import kotlinx.coroutines.launch

class QuestionViewModel @ViewModelInject constructor(private val repository: Repository, private val networkHelper: NetworkHelper) : ViewModel() {

    var categoryId: Int = 0

    private val _question = MutableLiveData<Result<Question>>()
    val question: LiveData<Result<Question>> = _question

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun loadQuestion(catId:Int?){
        if(catId != null){
            categoryId = catId
            getQuestionFromCategory()
        }else {
            getRandomQuestion()
        }
    }

    private fun getRandomQuestion() {
        viewModelScope.launch {
            _loading.value = true

            if (networkHelper.isNetworkConnected()) {
                repository.getRandomQuestion().let {
                    if (it.isSuccessful) {
                        _loading.value = false
                        val filteredQuestion = filterQuestion(it.body()!!)
                        _question.value = Result.success(filteredQuestion)
                    } else {
                        _loading.value = false
                        _question.value = Result.error(it.errorBody().toString(), null)
                    }
                }
            } else {
                _loading.value = false
                _question.value = Result.error("No internet connection", null)
            }
        }
    }

    private fun getQuestionFromCategory(){
        viewModelScope.launch {
            _loading.value = true

            if(networkHelper.isNetworkConnected()){
                repository.getQuestionsByCategory(categoryId).let {
                    if(it.isSuccessful){
                        _loading.value = false
                        val filteredQuestion = filterQuestion(it.body()!!)
                        _question.value = Result.success(filteredQuestion)
                    }else {
                        _loading.value = false
                        _question.value = Result.error(it.errorBody().toString(), null)
                    }
                }
            }else {
                _loading.value = false
                _question.value = Result.error("No internet connection", null)
            }
        }
    }

    private fun filterQuestion(items: List<Question>): Question{
        val array = items.toTypedArray()
        return array.random()
    }
}