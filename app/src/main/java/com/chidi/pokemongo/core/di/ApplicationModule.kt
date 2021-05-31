package com.chidi.pokemongo.core.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    /*@Singleton
    @Provides
    fun provideRunningDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        GoDatabase::class.java,
        Constants.APP_DB_NAME
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideCenterDao(database: GoDatabase): PokemonDao = database.pokemonDao*/
}
