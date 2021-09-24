package com.example.weekten.di

import android.content.Context
import androidx.room.Room
import com.example.weekten.room.LocalDatabase
import com.example.weekten.room.RoomDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

    @Module
    @InstallIn(SingletonComponent::class)
    object RoomModule {

    @Singleton
    @Provides
    fun providesLocalDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "user_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
        @Singleton
        @Provides
        fun providesRoomDao(localDatabase: LocalDatabase) : RoomDao{
            return localDatabase.userDoa()
        }
}