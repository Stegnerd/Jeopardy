package com.stegnerd.jeopardy.ui.question

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stegnerd.jeopardy.data.local.Repository
import com.stegnerd.jeopardy.data.model.Category
import com.stegnerd.jeopardy.data.model.Question
import com.stegnerd.jeopardy.util.NetworkHelper
import com.stegnerd.jeopardy.util.Result
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
     * User input answer for the question.
     */
    private var _userAnswer = MutableLiveData<String>()

    /**
     * User input answer for the question. Can be used in the ui.
     */
    var userAnswer: LiveData<String> = _userAnswer

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
        return question.value?.data?.answer?.toLowerCase(Locale.getDefault()) == userAnswer.value?.toLowerCase(Locale.getDefault())
    }
    /**
     * Gets a random [Question] from [Repository] that is not based on a [Category].
     */
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

    /**
     * Gets a [Question] from [Repository] that is based on specific [Category]
     */
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

    /**
     * Used to grab one item from a list.
     *
     * Note: Abstracted now because will ube used to cross reference against if already
     * answered before.
     */
    private fun filterQuestion(items: List<Question>): Question{
        val array = items.toTypedArray()
        return array.random()
    }
}