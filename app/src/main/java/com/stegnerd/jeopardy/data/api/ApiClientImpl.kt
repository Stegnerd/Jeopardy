package com.stegnerd.jeopardy.data.api

import com.stegnerd.jeopardy.data.model.Category
import com.stegnerd.jeopardy.data.model.Question
import retrofit2.Response
import javax.inject.Inject

/**
 * Implementation of [ApiClient]. Uses the [ApiService] to call remote endpoint
 * to get categories and questions for the ui.
 */
class ApiClientImpl @Inject constructor(private val apiService: ApiService) : ApiClient {
    override suspend fun getRandomCategories(): Response<List<Category>> {
        return apiService.getRandomCategories(CATEGORY_RETRIEVAL_LIMIT, getRandomCategoryOffset())
    }

    override suspend fun getQuestionsByCategory(categoryId: Int): Response<List<Question>> {
        return apiService.getQuestionsByCategory(categoryId)
    }

    override suspend fun getRandomQuestion(): Response<Question> {
        return apiService.getRandomQuestion()
    }

    companion object {
        private const val MAX_CATEGORY_ID = 11566
        private const val CATEGORY_RETRIEVAL_LIMIT = 10
    }

    /**
     * Generate a random offset based on the amount of categories in remote endpoint.
     */
    private fun getRandomCategoryOffset(): Int {
        // Get random offset from 0-11566
        // https://stackoverflow.com/questions/45685026/how-can-i-get-a-random-number-in-kotlin
        return (0..MAX_CATEGORY_ID).random()
    }
}