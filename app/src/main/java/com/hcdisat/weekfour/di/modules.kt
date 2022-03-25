package com.hcdisat.weekfour.di

import com.google.gson.Gson
import com.hcdisat.weekfour.network.JokesWebApi
import com.hcdisat.weekfour.viewmodels.JokesViewModel
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

    single { providesRetrofitService(get(), get()) }
    single { providesOkHttpClient(get()) }
    single { providesLoggingInterceptor() }
    single { providesGson() }
}

val viewModelsModule = module {

    viewModel { JokesViewModel(get()) }
}