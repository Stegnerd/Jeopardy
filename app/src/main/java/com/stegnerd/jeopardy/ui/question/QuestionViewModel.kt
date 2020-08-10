package com.stegnerd.jeopardy.ui.question

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stegnerd.jeopardy.data.local.Repository
import com.stegnerd.jeopardy.data.model.Category
import com.stegnerd.jeopardy.data.model.Question
import com.stegnerd.jeopardy.util.Constants
import com.stegnerd.jeopardy.util.Extensions.filterQuestion
import com.stegnerd.jeopardy.util.NetworkHelper
import com.stegnerd.jeopardy.util.Result
import com.stegnerd.jeopardy.util.Status
import kotlinx.coroutines.launch
import java.util.*

class QuestionViewModel @ViewModelInject constructor(private val repository: Repository, private val networkHelper: NetworkHelper) : ViewModel() {

    /**
     * This is the id of the [Category] that is passed from navigation.
     */
    var categoryId: Int = 0

    /**
     * This is the [Question] that can be updated via [Repository]
     */
    private val _question = MutableLiveData<Result<Question>>()

    /**
     * This is the [Question] that can be used in the ui
     */
    val question: LiveData<Result<Question>> = _question

    /**
     * Flag to determine if retrieving data is done or not.
     */
    private val _loading = MutableLiveData<Boolean>()

    /**
     * Flag to determine if retrieving data. Can be used in the ui.
     */
    val loading: LiveData<Boolean> = _loading

    /**
     * This is the value input by the user.
     *
     * It is no a mutable live data so that two way data binding can easily access it.
     */
    var userAnswer: String = ""

    /**
     * Determines if need to grab a question based on a category or not.
     * Also sets the categoryId from nav args if it is not null
     */
    fun loadQuestion(catId:Int?){
        if(catId != null){
            categoryId = catId
            getQuestionFromCategory()
        }else {
            getRandomQuestion()
        }
    }

    fun validate(): Boolean {
        return _question.value?.data?.answer?.toLowerCase(Locale.getDefault()) == userAnswer.toLowerCase(Locale.getDefault())
    }

    /**
     * Gets a random [Question] from [Repository] that is not based on a [Category].
     */
    private fun getRandomQuestion() {
        viewModelScope.launch {
            _loading.value = true

            if (networkHelper.isNetworkConnected()) {
                repository.getRandomQuestion().let {
                    if (it.status == Status.SUCCESS) {
                        _loading.value = false
                        _question.value = Result.success(it.data)
                    } else {
                        _loading.value = false
                        _question.value = it
                    }
                }
            } else {
                _loading.value = false
                _question.value = Result.error(Constants.NetworkConnectionError, null)
            }
        }
    }

    /**
     * Gets a [Question] from [Repository] that is based on specific [Category]
     */
    private fun getQuestionFromCategory(){
        viewModelScope.launch {
            _loading.value = true

            if(networkHelper.isNetworkConnected()){
                repository.getQuestionsByCategory(categoryId).let {
                    if(it.status == Status.SUCCESS){
                        _loading.value = false
                        val filteredQuestion = filterQuestion(it.data!!)
                        _question.value = Result.success(filteredQuestion)
                    }else {
                        _loading.value = false
                        _question.value = Result.error(it.message!!, null)
                    }
                }
            }else {
                _loading.value = false
                _question.value = Result.error(Constants.NetworkConnectionError, null)
            }
        }
    }
}