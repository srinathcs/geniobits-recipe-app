package com.geniobits.recipeapp.views.recipeBrowser.model


import com.google.gson.annotations.SerializedName

data class RecipeListResponse(
    @SerializedName("meals")
    val recipes: List<Recipe>?
)