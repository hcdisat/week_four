package com.hcdisat.weekfour.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hcdisat.weekfour.databinding.FragmentConfigBinding

class ConfigFragment : BaseFragment() {

    private var _binding: FragmentConfigBinding? = null
    private val binding: FragmentConfigBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentConfigBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}