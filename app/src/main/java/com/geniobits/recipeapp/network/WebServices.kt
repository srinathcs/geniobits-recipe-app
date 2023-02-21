package com.geniobits.recipeapp.network

import com.geniobits.recipeapp.views.recipeBrowser.model.RecipeListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("api/json/v1/1/search.php")
    suspend fun getRecipes(@Query("s") s: String) : RecipeListResponse
}