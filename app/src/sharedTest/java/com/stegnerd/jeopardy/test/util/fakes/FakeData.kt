package com.stegnerd.jeopardy.test.util.fakes

import com.stegnerd.jeopardy.data.model.Category
import com.stegnerd.jeopardy.data.model.Question

/**
 * Static data used for responses in testing.
 */
object FakeData {

    // List of categories
    val category1= Category(11531, "mixed bad", 5)
    val category2 = Category(11532, "let's \"ch\"at", 5)
    val category3 = Category(5412, "prehistoric times", 10)
    val category4 = Category(11496, "acting families", 5)
    val category5 = Category(11498, "world city walk", 5)

    val fakeCategoryList: List<Category> = listOf(category1, category2, category3, category4, category5)
    // TODO UUSE THIS https://github.com/square/okhttp/tree/master/mockwebserver

    // List for question by category
    val fakeQuestionCategoryOne = Question(87824,
        "(Sarah of the Clue Crew holds a proverbial glass of, well, colored liquid of some sort.)  As an optimist, it's how I see this glass",
        "half full",
        400,
        category1)
    val fakeQuestionCategoryOneList: List<Question> = listOf(fakeQuestionCategoryOne)

    // List for random question
    val fakeRandomCategory = Category(2967, "just us chickens", 5)
    val fakeRandomQuestion = Question(26853,
        "Tyson Foods could tell you that this state leads the USA in broiler production",
        "Arkansas",
        500,
        fakeRandomCategory
    )
    val fakeRandomQuestionList: List<Question> = listOf(fakeRandomQuestion)
}