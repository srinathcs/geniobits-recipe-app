package com.geniobits.recipeapp.data.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.geniobits.recipeapp.data.db.dao.ProfileDao
import com.geniobits.recipeapp.data.db.dao.RecipeDao
import com.geniobits.recipeapp.views.recipeBrowser.model.Recipe
import com.geniobits.recipeapp.views.userProfile.model.Profile

@Database(entities = [Recipe::class, Profile::class], version = 1)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun profileDao(): ProfileDao

    companion object {
        private var instance: RecipeDatabase? = null

        @Synchronized
        fun getInstance(mContext: Context): RecipeDatabase? {
            if (instance == null) {
                    instance = Room.databaseBuilder(
                        mContext.applicationContext,
                        RecipeDatabase::class.java, "recipe_db"
                    ).build()
            }
            return instance
        }
    }
}