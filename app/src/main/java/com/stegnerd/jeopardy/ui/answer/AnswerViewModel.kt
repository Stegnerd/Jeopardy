package com.stegnerd.jeopardy.ui.answer

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

class AnswerViewModel @ViewModelInject constructor() : ViewModel() {
    var isCorrect: Boolean = false


}