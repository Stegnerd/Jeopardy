package com.stegnerd.jeopardy.ui.answer

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

        getAnswerStatus()
        setupUi(answerViewModel.isCorrect)
        setupNavigation()

        return binding.root
    }

    private fun getAnswerStatus(){
        answerViewModel.isCorrect = args.correctAnswer
        answerViewModel.answer = args.answer
    }

    /**
     * Toggle ui based on the result of the question answered by the user.
     *
     * This sets the background color, image, and text color
     */
    private fun setupUi(isCorrect: Boolean){

        var lightColor: Int = 0
        var darkColor: Int = 0

        if(isCorrect){
            // getColor was deprecated in api 23, so we need to access the color
            // depending on the version of the phone
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                lightColor = context?.getColor(R.color.right_light)!!
                darkColor = context?.getColor(R.color.right_dark)!!
            }else{
                lightColor = resources.getColor(R.color.right_light)
                darkColor = resources.getColor(R.color.right_dark)
            }

            binding.answerResultImage.setImageResource(R.drawable.ic_check_circle_24)
            binding.answerResultText.setText(R.string.right_answer)
        }else{
             if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                 lightColor = context?.getColor(R.color.wrong_light)!!
                 darkColor = context?.getColor(R.color.wrong_dark)!!
            }else{
                 lightColor = resources.getColor(R.color.wrong_light)
                 darkColor = resources.getColor(R.color.wrong_dark)
            }

            binding.answerResultImage.setImageResource(R.drawable.ic_cancel_circle_24)

            // Update the text to use the wrong color scheme
            val text = getString(R.string.wrong_answer, answerViewModel.answer)
            binding.answerResultText.text = text
            binding.answerResultText.setTextColor(darkColor)

            // Update the button to use thw wrong color scheme
            binding.playAgainButton.setTextColor(lightColor)
            binding.playAgainButton.setBackgroundColor(darkColor)
        }

        binding.root.setBackgroundColor(lightColor)
    }

    private fun setupNavigation(){
        binding.playAgainButton.setOnClickListener {
            val action = AnswerFragmentDirections.actionAnswerFragmentToLandingFragment()
            findNavController().navigate(action)
        }
    }

}