package com.geniobits.recipeapp.views.recipeBrowser.repository

import com.geniobits.recipeapp.views.recipeBrowser.model.Recipe
import com.geniobits.recipeapp.views.recipeBrowser.model.RecipeListResponse

interface RecipeBrowserRepository {
    suspend fun getRecipes(s: String) : RecipeListResponse
    suspend fun saveFavourite(recipe: Recipe)
    suspend fun deleteFavourite(recipe: Recipe)
    suspend fun getFavouriteRecipe() : List<Recipe>?
}