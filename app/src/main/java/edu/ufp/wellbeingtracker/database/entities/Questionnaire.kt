package edu.ufp.wellbeingtracker.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questionnaires")
data class Questionnaire(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val isMultipleAnswer: Boolean,
    val isMultipleAnswerInDay: Boolean
)
