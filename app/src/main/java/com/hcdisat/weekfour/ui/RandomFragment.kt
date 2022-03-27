package com.hcdisat.weekfour.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hcdisat.weekfour.databinding.FragmentRandomBinding
import com.hcdisat.weekfour.dataaccess.network.EndPoints

class RandomFragment : BaseFragment() {

    private var _binding: FragmentRandomBinding? = null
    private val binding get() = _binding!!

    override fun canRequestJoke(canRequestJoke: Boolean) {
        if (canRequestJoke) {
            binding.mainContainer.visibility = View.VISIBLE
            binding.loadingPlaceholder.loading.visibility = View.GONE
            return
        }

        binding.mainContainer.visibility = View.GONE
        binding.loadingPlaceholder.loading.visibility = View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomBinding.inflate(inflater, container, false)

        // observer livedata
        jokesViewModel.endPoint = EndPoints.RANDOM
        jokesViewModel.state.observe(viewLifecycleOwner, ::handleResponse)

        // do api call
        binding.btnContainer.btnNewJoke.setOnClickListener {
            jokesViewModel.getJoke()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}