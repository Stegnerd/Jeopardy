package com.stegnerd.jeopardy.test.util.fakes

import com.stegnerd.jeopardy.data.local.Repository
import com.stegnerd.jeopardy.data.model.Category
import com.stegnerd.jeopardy.data.model.Question
import retrofit2.Response
import javax.inject.Inject

/**
 * Fake instance of the [Repository] for testing.
 */
class FakeRepository @Inject constructor() : Repository {
    override suspend fun getRandomCategories(): Response<List<Category>> {
        return Response.success(FakeData.fakeCategoryList)
    }

    override suspend fun getQuestionsByCategory(categoryId: Int): Response<List<Question>> {
        return Response.success(FakeData.fakeQuestionCategoryOneList)
    }

    override suspend fun getRandomQuestion(): Response<List<Question>> {
        return Response.success(FakeData.fakeRandomQuestionList)
    }
}