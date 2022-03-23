package com.hcdisat.weekfour.ui.random

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hcdisat.weekfour.databinding.FragmentRandomBinding
import com.hcdisat.weekfour.models.Joke
import com.hcdisat.weekfour.ui.dialogs.RandomJokeDialogFragment
import com.hcdisat.weekfour.ui.state.RandomState
import org.koin.androidx.viewmodel.ext.android.viewModel

class RandomFragment : Fragment() {

    private var _binding: FragmentRandomBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // view model used by this fragment
    private val randomViewModel: RandomViewModel by viewModel()

    /**
     * handles the Response from view model
     */
    private fun handleResponse(uiState: RandomState?) {
        when(uiState) {
            is RandomState.SUCCESS<*> -> showJoke(uiState.response as Joke)
            is RandomState.LOADING -> Toast.makeText(
                requireContext(),
                "Loading",
                Toast.LENGTH_SHORT).show()
            is RandomState.ERROR -> showError(uiState.throwable)
        }
    }

    /**
     * Displays error message too user
     */
    private fun showError(throwable: Throwable) {
        Log.e("TAG", "showError: ${throwable.localizedMessage}", )
    }

    /**
     * Displays the joke too the user
     */
    private fun showJoke(joke: Joke) {
        Log.d("TAG", "showJoke: ${joke.joke}")
        RandomJokeDialogFragment
            .newRandomJokeDialog(joke)
            .show(
                parentFragmentManager,
                RandomJokeDialogFragment.TAG
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomBinding.inflate(inflater, container, false)

        // observer livedata
        randomViewModel.randomJoke.observe(viewLifecycleOwner, ::handleResponse)

        // do api call
        randomViewModel.getJoke()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}