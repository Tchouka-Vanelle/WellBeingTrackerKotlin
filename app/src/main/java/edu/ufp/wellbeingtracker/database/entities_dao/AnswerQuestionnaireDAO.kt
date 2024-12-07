package edu.ufp.wellbeingtracker.database.entities_dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import edu.ufp.wellbeingtracker.database.entities.AnswerQuestionnaire

@Dao
interface AnswerQuestionnaireDAO {
    @Insert
    suspend fun insertAnswer(answer: AnswerQuestionnaire):Long
    @Query("SELECT * FROM answer_questionnaires WHERE idUser = :userId")
    suspend fun getAnswersForUser(userId: Int): List<AnswerQuestionnaire>
}