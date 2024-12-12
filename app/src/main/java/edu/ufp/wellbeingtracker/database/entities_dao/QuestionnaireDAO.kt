package edu.ufp.wellbeingtracker.database.entities_dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.ufp.wellbeingtracker.database.entities.Questionnaire

@Dao
interface QuestionnaireDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestionnaires(vararg questionnaires: Questionnaire): List<Long>
    @Query("SELECT * FROM questionnaires")
    suspend fun getAllQuestionnaires(): List<Questionnaire>
    @Query("SELECT isMultipleAnswerInDay FROM questionnaires WHERE id = :id")
    suspend fun isMultipleAnswerInDay(id: Int): Boolean
}