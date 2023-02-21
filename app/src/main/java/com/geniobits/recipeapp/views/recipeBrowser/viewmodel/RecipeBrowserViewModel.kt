package com.geniobits.recipeapp.views.recipeBrowser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geniobits.recipeapp.utils.NoData
import com.geniobits.recipeapp.utils.ResultResponse
import com.geniobits.recipeapp.views.recipeBrowser.model.Recipe
import com.geniobits.recipeapp.views.recipeBrowser.model.RecipeListResponse
import com.geniobits.recipeapp.views.recipeBrowser.repository.RecipeBrowserRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class RecipeBrowserViewModel(private val repository: RecipeBrowserRepository) : ViewModel() {
    private var mRecipesFromNetwork = MutableLiveData<ResultResponse<RecipeListResponse>>()
    private var mSaveFavoriteRecipe = MutableLiveData<ResultResponse<NoData>>()
    private var mDeleteFavoriteRecipe = MutableLiveData<ResultResponse<NoData>>()
    private var mFavoriteRecipes = MutableLiveData<ResultResponse<List<Recipe>>>()

    fun getRecipes(s: String) : LiveData<ResultResponse<RecipeListResponse>> {
        viewModelScope.launch {
            try {
                val response = repository.getRecipes(s)
                mRecipesFromNetwork.postValue(ResultResponse.Success(response))
            } catch (e:Exception) {
                e.printStackTrace()
                mRecipesFromNetwork.postValue(ResultResponse.Error(e.message ?: ""))
            }
        }
        return mRecipesFromNetwork
    }

    fun saveRecipe(recipe: Recipe) : LiveData<ResultResponse<NoData>> {
        viewModelScope.launch {
            try {
                repository.saveFavourite(recipe)
                mSaveFavoriteRecipe.postValue(ResultResponse.Success(NoData()))
            } catch (e:Exception) {
                e.printStackTrace()
                mSaveFavoriteRecipe.postValue(ResultResponse.Error(e.message ?: ""))
            }
        }
        return mSaveFavoriteRecipe
    }

    fun deleteRecipe(recipe: Recipe) : LiveData<ResultResponse<NoData>> {
        viewModelScope.launch {
            try {
                repository.deleteFavourite(recipe)
                mDeleteFavoriteRecipe.postValue(ResultResponse.Success(NoData()))
            } catch (e:Exception) {
                e.printStackTrace()
                mDeleteFavoriteRecipe.postValue(ResultResponse.Error(e.message ?: ""))
            }
        }
        return mDeleteFavoriteRecipe
    }

    fun getFavouriteRecipes() : LiveData<ResultResponse<List<Recipe>>> {
        viewModelScope.launch {
            try {
                val response = repository.getFavouriteRecipe() ?: arrayListOf()
                mFavoriteRecipes.postValue(ResultResponse.Success(response))
            } catch (e:Exception) {
                e.printStackTrace()
                mFavoriteRecipes.postValue(ResultResponse.Error(e.message ?: ""))
            }
        }
        return mFavoriteRecipes
    }

    fun clearData() {
        mRecipesFromNetwork.postValue(ResultResponse.Error(""))
    }
}