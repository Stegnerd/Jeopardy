package com.stegnerd.jeopardy.ui.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.stegnerd.jeopardy.databinding.QuestionFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionFragment : Fragment() {

    private val questionViewModel: QuestionViewModel by viewModels()

    private lateinit var binding: QuestionFragmentBinding

    private val args: QuestionFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = QuestionFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = questionViewModel
        }

        setupUi()
        val categoryId = getCategoryId()
        questionViewModel.loadQuestion(categoryId)

        return binding.root
    }

    private fun getCategoryId(): Int? {
        return args.categoryId?.toInt()
    }

    private fun setupUi() {
        binding.QuestionSubmitButton.setOnClickListener {
            val result = questionViewModel.validate()
            val answer = questionViewModel.question.value?.data?.answer
            val action = QuestionFragmentDirections.actionQuestionFragmentToAnswerFragment(result, answer!!)
            binding.root.findNavController().navigate(action)
        }
    }
}