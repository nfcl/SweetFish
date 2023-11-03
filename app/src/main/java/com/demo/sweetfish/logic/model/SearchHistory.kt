package com.demo.sweetfish.logic.model

import androidx.room.Entity
import java.util.Date

@Entity(primaryKeys = ["content"])
data class SearchHistory(
    val content: String,
    val recordTime: Date,
)