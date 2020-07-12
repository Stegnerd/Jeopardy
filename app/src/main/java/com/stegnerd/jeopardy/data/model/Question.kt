package com.stegnerd.jeopardy.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

/**
 * Question to be asked to the user based on a category and value.
 */
@JsonClass(generateAdapter = true)
data class Question(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "question") val text: String,
    @field:Json(name = "answer") val answer: String,
    @field:Json(name = "value") val pointValue: Int,
    @field:Json(name = "category_id") val categoryId: Int
)

fun Question.validate(userAnswer: String): Boolean = this.answer.toLowerCase(Locale.getDefault()) == userAnswer.toLowerCase(Locale.getDefault())