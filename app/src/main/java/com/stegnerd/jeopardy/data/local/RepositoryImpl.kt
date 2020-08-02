package com.stegnerd.jeopardy.data.local

import com.stegnerd.jeopardy.data.api.ApiClient
import javax.inject.Inject

/**
 * Implementation of [Repository]. Gets questions from [ApiClient].
 */
class RepositoryImpl @Inject constructor(private val apiClient: ApiClient): Repository{

    override suspend fun getRandomCategories() = apiClient.getRandomCategories()

    override suspend fun getQuestionsByCategory(categoryId: Int) = apiClient.getQuestionsByCategory(categoryId)

    override suspend fun getRandomQuestion() = apiClient.getRandomQuestion()
}