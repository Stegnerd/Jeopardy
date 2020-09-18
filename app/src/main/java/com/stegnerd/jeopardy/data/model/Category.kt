package com.stegnerd.jeopardy.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

/**
 * Container for a type of question based on a topic.
 *
 * the field:Json is how moshi will find the property when deserializing and serializing the class
 */
@Serializable
@JsonClass(generateAdapter = true)
data class Category (
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "clues_count") val questionCount: Int
)