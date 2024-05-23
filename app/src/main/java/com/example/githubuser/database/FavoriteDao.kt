package com.example.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("SELECT * from Favorite WHERE username = :username")
    fun getUserFavorite(username: String?): LiveData<List<Favorite>>

    @Query("SELECT * from favorite ORDER BY username ASC")
    fun getAllUserFavorite(): LiveData<List<Favorite>>
}