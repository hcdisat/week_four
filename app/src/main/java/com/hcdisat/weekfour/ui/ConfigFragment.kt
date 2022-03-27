package com.hcdisat.weekfour.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.hcdisat.weekfour.databinding.FragmentConfigBinding
import com.hcdisat.weekfour.ui.state.SettingsState
import com.hcdisat.weekfour.ui.state.UIState
import com.hcdisat.weekfour.viewmodels.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConfigFragment : BaseFragment() {

    private var _binding: FragmentConfigBinding? = null
    private val binding: FragmentConfigBinding get() = _binding!!

    private val settingsViewModel: SettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentConfigBinding.inflate(inflater, container, false)

        binding.explicitJokesSwitch.setOnCheckedChangeListener(::onExplicitJokeSwitchChanged)

        settingsViewModel.state.observe(viewLifecycleOwner, ::handleStateChange)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        settingsViewModel.getSettings()
    }

    /**
     * turns on and off explicit content requests
     */
    private fun onExplicitJokeSwitchChanged(button: CompoundButton, switchState: Boolean){
        settingsViewModel.toggleExplicitContent(switchState)
    }

    private fun handleStateChange(settingsState: SettingsState) {
        when(settingsState) {
            is SettingsState.SUCCESS -> {
                settingsState.settings?.let {
                    binding.explicitJokesSwitch.isChecked = it.showExplicitContent
                }
                canToggleSwitch(true)
            }
            is SettingsState.LOADING -> canToggleSwitch(false)
            else -> {}
        }
    }

    /**
     * while saving in the database I'm disabling the switch
     */
    private fun canToggleSwitch(toggle: Boolean) {
        binding.explicitJokesSwitch.isEnabled = toggle
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}