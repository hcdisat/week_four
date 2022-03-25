package com.hcdisat.weekfour.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.hcdisat.weekfour.databinding.FragmentCustomNameJokeBinding
import com.hcdisat.weekfour.network.EndPoints

class CustomNameJokeFragment : BaseFragment() {

    private var _binding: FragmentCustomNameJokeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun canRequestJoke(canRequestJoke: Boolean) {
        if (canRequestJoke) {
            binding.jokeForm.visibility = View.VISIBLE
            binding.loadingPlaceholder.loading.visibility = View.GONE
            binding.textName.apply {
                text.clear()
                requestFocus()
            }

            return
        }

        binding.jokeForm.visibility = View.GONE
        binding.loadingPlaceholder.loading.visibility = View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomNameJokeBinding.inflate(inflater, container, false)

        jokesViewModel.endPoint = EndPoints.CUSTOM
        jokesViewModel.randomJoke.observe(viewLifecycleOwner, ::handleResponse)

        binding.btnContainer.btnNewJoke.isEnabled = false
        binding.textName.addTextChangedListener{
            binding.btnContainer.btnNewJoke.isEnabled = true
        }
        binding.btnContainer.btnNewJoke.setOnClickListener {
            jokesViewModel.getJoke(binding.textName.text.toString())
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}