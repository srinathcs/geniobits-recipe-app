package com.geniobits.recipeapp.views.db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.geniobits.recipeapp.views.recipeBrowser.model.Recipe

@Dao
interface RecipeDao {
    @Insert
    suspend fun insert(recipe: Recipe)

    @Query("DELETE FROM recipe WHERE strMeal=:title")
    suspend fun delete(title:String)

    @Query("SELECT * FROM recipe")
    suspend fun getRecipes(): List<Recipe>
}