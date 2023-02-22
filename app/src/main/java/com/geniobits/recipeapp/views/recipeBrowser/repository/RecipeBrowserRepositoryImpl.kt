package com.geniobits.recipeapp.views.recipeBrowser.repository

import com.geniobits.recipeapp.data.network.WebServices
import com.geniobits.recipeapp.data.db.dao.RecipeDao
import com.geniobits.recipeapp.views.recipeBrowser.model.Recipe
import com.geniobits.recipeapp.views.recipeBrowser.model.RecipeListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeBrowserRepositoryImpl(private val webServices: WebServices, private val recipeDao: RecipeDao?): RecipeBrowserRepository {

    override suspend fun getRecipes(s: String): RecipeListResponse {
        return withContext(Dispatchers.IO) {
            webServices.getRecipes(s)
        }
    }

    override suspend fun saveFavourite(recipe: Recipe) {
        return withContext(Dispatchers.IO) {
            recipeDao?.insert(recipe)
        }
    }

    override suspend fun deleteFavourite(recipe: Recipe) {
        return withContext(Dispatchers.IO) {
            recipeDao?.delete(recipe.strMeal!!)
        }
    }

    override suspend fun getFavouriteRecipe(): List<Recipe>? {
        return withContext(Dispatchers.IO) {
            recipeDao?.getRecipes()
        }
    }
}