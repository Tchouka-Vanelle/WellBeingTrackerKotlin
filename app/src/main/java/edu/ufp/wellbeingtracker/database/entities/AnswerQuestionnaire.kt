package edu.ufp.wellbeingtracker.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "answer_questionnaires",
    foreignKeys = [
        ForeignKey(
            entity = QuestionQuestionnaire::class,
            parentColumns = ["id"],
            childColumns = ["idQuestionQuestionnaire"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["idUser"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TypeAnswer::class,
            parentColumns = ["id"],
            childColumns = ["idTypeAnswer"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AnswerQuestionnaire(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    val idQuestionQuestionnaire: Int,
    val idUser: Int,
    val idTypeAnswer: Int,
    val dateAnswer: String,
    val dateTimeAnswer: String
)
