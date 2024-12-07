package edu.ufp.wellbeingtracker.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [Index(value = ["name"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    val name: String,
    val hashedPassword: String
)
