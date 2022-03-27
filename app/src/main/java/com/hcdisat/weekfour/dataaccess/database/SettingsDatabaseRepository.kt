package com.hcdisat.weekfour.dataaccess.database

import com.hcdisat.weekfour.models.Settings

class SettingsDatabaseRepository(
    private val settingsDao: SettingsDao
): SettingsDatabaseRepositoryContract {

    override suspend fun setSettings(settings: Settings) {
        settingsDao.setSettings(settings)
    }

    override suspend fun getSettings(): Settings =
        settingsDao.getSettings(SettingsDatabaseRepositoryContract.SETTINGS_ID)
}

interface SettingsDatabaseRepositoryContract {
    suspend fun setSettings(settings: Settings)
    suspend fun getSettings(): Settings

    companion object {
        const val SETTINGS_ID = SettingsDao.SETTINGS_ID
    }
}
