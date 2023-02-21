package com.geniobits.recipeapp.views.recipeBrowser.listener

import com.geniobits.recipeapp.views.recipeBrowser.model.Recipe

interface RecipeClickListener {
    fun onRecipe(recipe: Recipe)
}