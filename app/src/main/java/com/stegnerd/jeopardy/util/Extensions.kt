package com.stegnerd.jeopardy.util

import com.stegnerd.jeopardy.data.model.Question

object Extensions {

    /**
     * Used to grab one item from a list.
     *
     * Note: Abstracted now because will ube used to cross reference against if already
     * answered before.
     */
    fun filterQuestion(items: List<Question>): Question {
        val array = items.toTypedArray()
        return array.random()
    }

}