package com.hcdisat.weekfour.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.hcdisat.weekfour.databinding.FragmentRandomJokeDialogBinding
import com.hcdisat.weekfour.models.Joke
import com.hcdisat.weekfour.models.Jokes

class RandomJokeDialogFragment(): DialogFragment() {

    private val binding by lazy {
        FragmentRandomJokeDialogBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments.let {
            binding.joke.text = it?.getString(JOKE_ARG, "No Joke!")
        }

        return binding.root
    }

    companion object {
        const val TAG = "JokeDialog"
        private const val JOKE_ARG = "JOKE_ARG"
        private const val JOKE_ID_ARG = "JOKE_ID_ARG"

        fun newRandomJokeDialog(joke: Joke): RandomJokeDialogFragment {
            Bundle().apply {
                putString(JOKE_ARG, joke.joke)
                putInt(JOKE_ID_ARG, joke.id)
            }.also {
                return RandomJokeDialogFragment().apply {
                    arguments = it
                }
            }
        }

    }
}