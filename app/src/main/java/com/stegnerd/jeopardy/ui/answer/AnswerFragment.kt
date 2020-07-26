package com.stegnerd.jeopardy.ui.answer

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.stegnerd.jeopardy.R
import com.stegnerd.jeopardy.databinding.AnswerFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnswerFragment : Fragment() {

    private lateinit var binding: AnswerFragmentBinding

    private val answerViewModel: AnswerViewModel by viewModels()

    private val args: AnswerFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = AnswerFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = answerViewModel
        }

        val answerStatus = getAnswerStatus()
        answerViewModel.isCorrect = answerStatus

        return binding.root
    }

    private fun getAnswerStatus(): Boolean {
        return args.correctAnswer
    }

}