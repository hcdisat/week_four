package com.hcdisat.weekfour.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.hcdisat.weekfour.dataaccess.database.*
import com.hcdisat.weekfour.dataaccess.network.JokesApiRepository
import com.hcdisat.weekfour.dataaccess.network.JokesApiRepositoryContract
import com.hcdisat.weekfour.dataaccess.network.JokesWebApi
import com.hcdisat.weekfour.viewmodels.JokesViewModel
import com.hcdisat.weekfour.viewmodels.SettingsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Request timeout used in http client
 */
private const val REQUEST_TIME_OUT = 30L

/**
 * Module used too inject webb api dependencies
 */
val restApiModule = module {

    /**
     * Provides [Gson]
     */
    fun providesGson(): Gson = Gson()

    /**
     * Provides [HttpLoggingInterceptor]
     */
    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    /**
     * Provides [OkHttpClient]
     */
    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .build()

    /**
     * provides [JokesWebApi]
     */
    fun providesRetrofitService(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): JokesWebApi =
        Retrofit
            .Builder()
            .baseUrl(JokesWebApi.BASE_PATH)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build().create(JokesWebApi::class.java)

    /**
     * provides [JokesApiRepositoryContract]
     */
    fun providesJokesApiRepository(jokesWebApi: JokesWebApi): JokesApiRepositoryContract =
        JokesApiRepository(jokesWebApi)

    single { providesJokesApiRepository(get()) }
    single { providesRetrofitService(get(), get()) }
    single { providesOkHttpClient(get()) }
    single { providesLoggingInterceptor() }
    single { providesGson() }
}

val viewModelsModule = module {

    viewModel { JokesViewModel(get(), get(), get()) }
    viewModel { SettingsViewModel(get()) }
}

val databaseModule = module {

    /**
     * provides [JokesDatabase]
     */
    fun providesDatabase(context: Context): JokesDatabase =
        Room.databaseBuilder(
            context,
            JokesDatabase::class.java,
            DB_NAME
        ).build()

    /**
     * provides [JokesDao]
     */
    fun providesJokesDao(database: JokesDatabase) = database.jokesDao()

    /**
     * provides [JokeDatabaseRepository]
     */
    fun providesJokeDatabaseRepository(jokesDao: JokesDao): JokeDatabaseRepositoryContract =
        JokeDatabaseRepository(jokesDao)

    /**
     * provides [SettingsDao]
     */
    fun providesSettingsDao(database: JokesDatabase) = database.settingsDao()

    /**
     * provides [SettingsDatabaseRepositoryContract]
     */
    fun providesSettingsDatabaseRepository(settingsDao: SettingsDao): SettingsDatabaseRepositoryContract =
        SettingsDatabaseRepository(settingsDao)

    single { providesDatabase(get()) }
    single { providesJokesDao(get()) }
    single { providesJokeDatabaseRepository(get()) }
    single { providesSettingsDao(get()) }
    single { providesSettingsDatabaseRepository(get()) }
}