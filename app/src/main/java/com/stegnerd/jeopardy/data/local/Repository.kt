package com.stegnerd.jeopardy.data.local

import com.stegnerd.jeopardy.data.api.ApiClient
import javax.inject.Inject

/**
 * Gets data from local and remote sources.
 *
 * Currently there is only remote sources.
 */
class Repository @Inject constructor(private val apiClient: ApiClient){

    suspend fun getRandomCategories() = apiClient.getRandomCategories()

    suspend fun getQuestionsByCategory(categoryId: Int) = apiClient.getQuestionsByCategory(categoryId)

    suspend fun getRandomQuestion() = apiClient.getRandomQuestion()
}