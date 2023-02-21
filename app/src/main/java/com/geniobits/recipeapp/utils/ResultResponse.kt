package com.geniobits.recipeapp.utils

sealed class ResultResponse<out T> {
    data class Success<out T>(val data: T) : ResultResponse<T>()
    data class Error(val message: String) : ResultResponse<Nothing>()
    //object Loading : ResultResponse<Nothing>()
}