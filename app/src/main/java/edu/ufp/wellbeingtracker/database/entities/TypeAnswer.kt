package edu.ufp.wellbeingtracker.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "type_answers",
    indices = [Index(value = ["value"], unique = true)]
)
data class TypeAnswer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val value: Int,
    val meaning: String
)
