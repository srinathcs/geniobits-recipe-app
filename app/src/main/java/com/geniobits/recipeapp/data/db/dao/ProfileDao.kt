package com.geniobits.recipeapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.geniobits.recipeapp.views.userProfile.model.Profile


@Dao
interface ProfileDao {
    @Insert
    suspend fun insert(profile: Profile)

    @Update
    fun update(profile: Profile): Int

    @Query("SELECT * FROM profile")
    suspend fun getProfile(): List<Profile>
}