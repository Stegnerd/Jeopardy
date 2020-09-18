package com.stegnerd.jeopardy.data.local

import com.stegnerd.jeopardy.data.api.ApiClient
import com.stegnerd.jeopardy.data.model.Category
import com.stegnerd.jeopardy.data.model.Question
import com.stegnerd.jeopardy.util.Constants
import com.stegnerd.jeopardy.util.Extensions.filterQuestion
import com.stegnerd.jeopardy.util.Result
import javax.inject.Inject

/**
 * Implementation of [Repository]. Gets questions from [ApiClient].
 */
class RepositoryImpl @Inject constructor(private val apiClient: ApiClient): Repository{

    override suspend fun getRandomCategories() : Result<List<Category>> {
        try {
            apiClient.getRandomCategories().let {
                return if(it.isSuccessful){
                    Result.success(it.body())
                }else {
                    Result.error(it.errorBody().toString(), null)
                }
            }
        }catch (exception: Exception){
            return Result.error(Constants.NetworkConnectionError, null)
        }
    }

    override suspend fun getQuestionsByCategory(categoryId: Int) : Result<List<Question>>{
        try {
            apiClient.getQuestionsByCategory(categoryId).let {
                return if(it.isSuccessful){
                    Result.success(it.body())
                }else {
                    Result.error(it.errorBody().toString(), null)
                }
            }
        }  catch (exception: Exception){
            return Result.error(Constants.NetworkConnectionError, null)
        }
    }

    override suspend fun getRandomQuestion() : Result<Question> {
      try {
          apiClient.getRandomQuestion().let {
              return if (it.isSuccessful){
                  val question = filterQuestion(it.body()!!)
                  Result.success(question)
              }else {
                  Result.error(it.errorBody().toString(), null)
              }
          }
      }  catch (exception: Exception){
          return Result.error(Constants.NetworkConnectionError, null)
      }
    }


}