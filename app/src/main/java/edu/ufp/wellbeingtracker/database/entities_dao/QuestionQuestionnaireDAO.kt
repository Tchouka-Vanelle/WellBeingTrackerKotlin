package edu.ufp.wellbeingtracker.database.entities_dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.ufp.wellbeingtracker.database.entities.QuestionQuestionnaire

@Dao
interface QuestionQuestionnaireDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(vararg  questions: QuestionQuestionnaire): List<Long>
    @Query("SELECT * FROM question_questionnaires WHERE idQuestionnaire = :questionnaireId")
    suspend fun getAllQuestionsForQuestionnaire(questionnaireId: Int): List<QuestionQuestionnaire>
}