package com.geniobits.recipeapp.views.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.geniobits.recipeapp.views.recipeBrowser.model.Recipe

@Database(entities = [Recipe::class], version = 1)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

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