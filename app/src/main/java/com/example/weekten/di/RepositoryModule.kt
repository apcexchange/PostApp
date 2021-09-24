package com.example.weekten.di

import com.example.weekten.api.ApiServices
import com.example.weekten.repository.Repository
import com.example.weekten.room.CachedCommentMapper
import com.example.weekten.room.CachedPostMapper
import com.example.weekten.room.RoomDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule{

    @Provides
    @Singleton
    fun provideRepo(
        userDao: RoomDao,
        apiServices: ApiServices,
        catchedMapper: CachedPostMapper,
        cachedComment: CachedCommentMapper
    ) : Repository{
        return Repository(userDao,catchedMapper,cachedComment,apiServices)
    }
}
