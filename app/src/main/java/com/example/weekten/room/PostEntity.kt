package com.example.weekten.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "post_table")
data class PostEntity(
    val userId: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val body: String,
): Parcelable