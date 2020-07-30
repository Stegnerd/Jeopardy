package com.stegnerd.jeopardy.data.api

import com.stegnerd.jeopardy.data.model.Category
import com.stegnerd.jeopardy.data.model.Question
import retrofit2.Response

/**
 * Interface of calls the api is capable of making for data to the remote api.
 */
interface ApiClient {

    /**
     * Gets a list of random [Category]
     */
    suspend fun getRandomCategories(): Response<List<Category>>

    /**
     * Gets a list of [Question] based on a [Category]
     */
    suspend fun getQuestionsByCategory(categoryId: Int): Response<List<Question>>

    /**
     * Gets a random [Question] from a random [Category]
     *
     * This is always a list of one.
     */
    suspend fun getRandomQuestion(): Response<List<Question>>
}