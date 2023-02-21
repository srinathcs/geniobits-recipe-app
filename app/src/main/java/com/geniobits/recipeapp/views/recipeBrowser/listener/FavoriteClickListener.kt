package com.geniobits.recipeapp.views.recipeBrowser.listener

import com.geniobits.recipeapp.views.recipeBrowser.model.Recipe

interface FavoriteClickListener {
    fun onFavourite(isClicked:Boolean, recipe: Recipe)
}