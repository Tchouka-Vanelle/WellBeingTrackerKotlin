package edu.ufp.wellbeingtracker.database.entities_dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.ufp.wellbeingtracker.database.entities.User

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT) // Abort if username already exists
    suspend fun insertUser(user: User): Long
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Int): User
    @Query("SELECT * FROM users WHERE name = :username LIMIT 1")
    suspend fun getUserByName(username: String): User?
}