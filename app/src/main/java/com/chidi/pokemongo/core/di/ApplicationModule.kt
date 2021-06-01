package com.chidi.pokemongo.core.di

import android.content.Context
import androidx.room.Room
import com.chidi.pokemongo.data.GoDatabase
import com.chidi.pokemongo.data.local.CapturedDao
import com.chidi.pokemongo.data.local.CommunityDao
import com.chidi.pokemongo.data.local.TeamDao
import com.chidi.pokemongo.presentation.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Singleton
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
    fun provideMyTeamDao(database: GoDatabase): TeamDao = database.teamDao

    @Provides
    fun provideCapturedDao(database: GoDatabase): CapturedDao = database.capturedDao

    @Provides
    fun provideCommunityDao(database: GoDatabase): CommunityDao = database.communityDao
}
