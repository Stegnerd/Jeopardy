package com.stegnerd.jeopardy.data.api

import com.stegnerd.jeopardy.data.model.Category
import com.stegnerd.jeopardy.data.model.Question
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Descriptions of calls to remote api and their parameters.
 */
interface ApiService {

    /**
     * Gets a list of [Category] to pick questions from.
     *
     * @param count How many categories to grab. Default is 10.
     * @param offset Offset to start grabbing categories from. Default is 0.
     */
    @GET("categories")
    suspend fun getRandomCategories(
        @Query("count") count: Int = 5,
        @Query("offset") offset: Int = 0
    ): Response<List<Category>>

    /**
     * Gets a list of [Question] from a specific category. Will return all
     * questions for that category.
     *
     * @param category The id of the category to get questions for.
     */
    @GET("clues")
    suspend fun getQuestionsByCategory(
        @Query("category") category: Int
    ): Response<List<Question>>

    /**
     * Gets a random [Question] from a random category.
     *
     * This is a list of [Question]
     */
    @GET("random")
    suspend fun getRandomQuestion(): Response<List<Question>>
}