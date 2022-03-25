package com.hcdisat.weekfour.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.hcdisat.weekfour.databinding.FragmentRandomJokeDialogBinding
import com.hcdisat.weekfour.viewmodels.JokesViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class JokeDialogFragment(): DialogFragment() {

    private val binding by lazy {
        FragmentRandomJokeDialogBinding.inflate(layoutInflater)
    }

    private val jokesViewModel: JokesViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments.let {
            binding.joke.text = jokesViewModel.selectedJoke.joke
        }

        binding.btnDismiss.setOnClickListener { dismissAllowingStateLoss() }

        return binding.root
    }

    companion object {
        const val TAG = "JokeDialog"

        fun newRandomJokeDialog(): JokeDialogFragment {
            return JokeDialogFragment()
        }

    }
}