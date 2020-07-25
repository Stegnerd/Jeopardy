package com.stegnerd.jeopardy.ui.answer

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stegnerd.jeopardy.R

class AnswerFragment : Fragment() {

    companion object {
        fun newInstance() = AnswerFragment()
    }

    private lateinit var viewModel: AnswerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.answer_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AnswerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}