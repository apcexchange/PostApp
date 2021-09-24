package com.example.weekten.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [PostEntity::class, CommentEntity::class], version = 1)

abstract class LocalDatabase : RoomDatabase() {
    abstract fun userDoa() : RoomDao
}