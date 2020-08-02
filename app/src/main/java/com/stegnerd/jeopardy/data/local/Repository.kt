package com.stegnerd.jeopardy.data.local

import com.stegnerd.jeopardy.data.model.Category
import com.stegnerd.jeopardy.data.model.Question
import retrofit2.Response

/**
 * Interface to the data layer. Gets data from local and remote sources.
 *
 * Currently there is only remote sources.
 */
interface Repository {

    /**
     * Gets a list of random [Category]
     */
    suspend fun getRandomCategories() : Response<List<Category>>

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