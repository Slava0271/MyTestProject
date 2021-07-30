package com.example.mytestproject.database.users

import androidx.room.*

@Dao
interface UsersDao {
    @Insert
    suspend fun insert(users: Users)

    @Update
    suspend fun update(users: Users)

    @Query("SELECT * FROM users_table ORDER BY usersId ASC")
    suspend fun getAllAnswers(): List<Users>

    @Query("Delete from users_table")
    suspend fun clear()

    @Query("Delete from users_table where usersId= :id")
    suspend fun delete(id: Long)

}