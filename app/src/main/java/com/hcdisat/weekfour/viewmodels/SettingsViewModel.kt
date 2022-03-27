package com.hcdisat.weekfour.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcdisat.weekfour.dataaccess.database.SettingsDatabaseRepositoryContract
import com.hcdisat.weekfour.models.Settings
import com.hcdisat.weekfour.ui.state.SettingsState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class SettingsViewModel(
    private val settingsDatabaseRepository: SettingsDatabaseRepositoryContract,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private var _state: MutableLiveData<SettingsState> =
        MutableLiveData(SettingsState.DEFAULT)
    val state: MutableLiveData<SettingsState> get() = _state

    fun getSettings() {
        _state.value = SettingsState.LOADING
        viewModelScope.launch(ioDispatcher) {
            try {
                val settings = settingsDatabaseRepository.getSettings()
                _state.postValue(SettingsState.SUCCESS(settings))
            }
            catch (e: Exception) {
                _state.postValue(SettingsState.ERROR(e))
            }
        }
    }

    /**
     * sets the explicit settings
     */
    fun toggleExplicitContent(toggle: Boolean) {
        _state.value = SettingsState.LOADING
        viewModelScope.launch(ioDispatcher) {
            try {
                settingsDatabaseRepository
                    .setSettings(Settings(
                        SettingsDatabaseRepositoryContract.SETTINGS_ID,
                        toggle
                    ))
                _state.postValue(SettingsState.SUCCESS())

            } catch (e: Exception) {
                _state.postValue(SettingsState.ERROR(e))
            }
        }
    }
}