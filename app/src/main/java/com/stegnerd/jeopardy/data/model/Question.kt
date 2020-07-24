package com.stegnerd.jeopardy.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.stegnerd.jeopardy.util.NullToDefaultPointValue
import java.util.*

/**
 * Question to be asked to the user based on a category and value.
 */
@JsonClass(generateAdapter = true)
data class Question(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "question") val text: String,
    @field:Json(name = "answer") val answer: String,
    @NullToDefaultPointValue    // Sometimes the incoming value is null, we need to specify a default value of 100.
    @field:Json(name = "value") val pointValue: Int,
    @field:Json(name = "category") val category: Category
)

fun Question.validate(userAnswer: String): Boolean = this.answer.toLowerCase(Locale.getDefault()) == userAnswer.toLowerCase(Locale.getDefault())