package com.stegnerd.jeopardy.util

import com.squareup.moshi.*
import com.squareup.moshi.internal.Util
import java.lang.reflect.Type

/**
 * Used to create annotation class that can be used on other classes.
 */
@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class  NullToDefaultPointValue

/**
 * Used in a Moshi adapter that converts null Int values to a default value of 100.
 * If the value is not null just use it.
 *
 * This solution came from this Github issue: https://github.com/square/moshi/issues/878
 */
object NullToDefaultPointValueFactory : JsonAdapter.Factory {
    val annotationClass = NullToDefaultPointValue::class.java

    override fun create(type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi): JsonAdapter<*>? {
        // If data contains this annotation return this adapter on it.
        if(annotations containsAssignable annotationClass) return Adapter
        return null
    }

    private object Adapter: JsonAdapter<Int>() {
        override fun fromJson(reader: JsonReader): Int? {
            with(reader) {
                // Looking at the next value is null return a default of 100
                // otherwise use the value given
                return when (peek()) {
                    JsonReader.Token.NULL -> {
                        nextNull<Any>()
                        100
                    }
                    else -> {
                        reader.nextInt()
                    }
                }
            }
        }

        override fun toJson(writer: JsonWriter, value: Int?) {
            writer.value(value.toString())
        }

    }
}

private infix fun Set<Annotation>.containsAssignable(clazz: Class<out Annotation>): Boolean {
    return Util.isAnnotationPresent(this, clazz)
}