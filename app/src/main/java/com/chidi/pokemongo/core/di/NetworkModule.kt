package com.chidi.pokemongo.core.di

import android.app.Application
import com.chidi.pokemongo.BuildConfig
import com.chidi.pokemongo.data.local.SharedPreferenceManager
import com.chidi.pokemongo.data.remote.api.HttpInterceptor
import com.chidi.pokemongo.data.remote.api.PokemonGOService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providePref(app: Application): SharedPreferenceManager = SharedPreferenceManager(app)

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL


    @Singleton
    @Provides
    fun provideOkHttpClient(pref: SharedPreferenceManager) = if (BuildConfig.DEBUG) {
        val logginInterceptor = HttpLoggingInterceptor()
        logginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(logginInterceptor)
            .addInterceptor(HttpInterceptor(pref))
            .build()
    } else {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(HttpInterceptor(pref))
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun providePokemonGOService(retrofit: Retrofit): PokemonGOService = retrofit.create(PokemonGOService::class.java)
}

/* @Provides
 @Singleton
 fun provideApiHelper(apiHelper: ApiServiceHelperImpl): ApiServiceHelper = apiHelper
}*/