package com.quangtd.hlovepuzzle.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.PropertyName
import com.google.gson.annotations.SerializedName

@Entity(tableName = "content")
data class ContentEntity(
    @PrimaryKey
    val id: Int = 0,

    @SerializedName("img")
    @PropertyName("img")
    @ColumnInfo(name = "img")
    val img: String = "",

    @SerializedName("content")
    @PropertyName("content")
    @ColumnInfo(name = "content")
    val content: String = "",

    @SerializedName("link")
    @PropertyName("link")
    @ColumnInfo(name = "link")
    val link: String = "",

    @SerializedName("level")
    @PropertyName("level")
    @ColumnInfo(name = "level")
    val level: Int = 0
)