package edu.ufp.wellbeingtracker.database.entities_dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.ufp.wellbeingtracker.database.entities.TypeAnswer

@Dao
interface TypeAnswerDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTypeAnswers(vararg typeAnswers: TypeAnswer): List<Long>
    @Query("SELECT * FROM type_answers")
    suspend fun getAllTypeAnswers(): List<TypeAnswer>
    @Query("SELECT meaning FROM type_answers order by id desc")
    suspend fun getAllMeanings(): List<String>
}