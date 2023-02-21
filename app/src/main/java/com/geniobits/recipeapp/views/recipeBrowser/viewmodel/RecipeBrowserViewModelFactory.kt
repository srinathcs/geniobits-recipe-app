package com.geniobits.recipeapp.views.recipeBrowser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.geniobits.recipeapp.views.recipeBrowser.repository.RecipeBrowserRepository

class RecipeBrowserViewModelFactory(private val repository: RecipeBrowserRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeBrowserViewModel::class.java)) {
            return RecipeBrowserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}