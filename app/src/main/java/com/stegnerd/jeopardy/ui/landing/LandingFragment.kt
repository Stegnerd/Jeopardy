package com.stegnerd.jeopardy.ui.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.stegnerd.jeopardy.databinding.LandingFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * This is the fragment for the landing page. This is using an instance of view binding not databinding!
 * Because there is no data there is no need for data binding.
 */
@AndroidEntryPoint
class LandingFragment : Fragment() {


    private val viewModel: LandingViewModel by viewModels()

    // Reference to view binding that starts as null before fragment is created.
    private var _binding: LandingFragmentBinding? = null

    // Getter for fragment view binding that is null asserted. Only should be used after the view is created.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = LandingFragmentBinding.inflate(inflater, container, false)

        setupNavigation()

        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Fragment can outlive their views so we need to remove it in order to avoid a memory leak.
        _binding = null
    }

    /**
     * Set up click listeners for button navigation
     */
    private fun setupNavigation(){
        binding.CategoryButton.setOnClickListener {
            val action = LandingFragmentDirections.actionLandingFragmentToCategorySelectFragment()
            findNavController().navigate(action)
        }
        binding.QuestionButton.setOnClickListener {
            // Since there was no category selected pass null as the parameter
            val action  = LandingFragmentDirections.actionLandingFragmentToQuestionFragment(null)
            findNavController().navigate(action)
        }
    }
}