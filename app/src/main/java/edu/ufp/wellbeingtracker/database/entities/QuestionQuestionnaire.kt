package edu.ufp.wellbeingtracker.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "question_questionnaires",
    foreignKeys = [ForeignKey(
        entity = Questionnaire::class,
        parentColumns = ["id"],
        childColumns = ["idQuestionnaire"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["idQuestionnaire"])]
)
data class QuestionQuestionnaire(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idQuestionnaire: Int,
    val title: String
)
