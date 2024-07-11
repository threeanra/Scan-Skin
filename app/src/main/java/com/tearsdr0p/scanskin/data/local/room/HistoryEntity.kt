package com.tearsdr0p.scanskin.data.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String? = null,

    @ColumnInfo(name = "time")
    val time: String? = null,

    @ColumnInfo(name = "image")
    val image: String? = null,
)