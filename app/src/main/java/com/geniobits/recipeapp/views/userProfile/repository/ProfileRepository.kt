package com.geniobits.recipeapp.views.userProfile.repository

import com.geniobits.recipeapp.views.userProfile.model.Profile

interface ProfileRepository {
    suspend fun getProfile(): List<Profile>?
    suspend fun saveProfile(profile: Profile)
    suspend fun updateProfile(profile: Profile): Int?
}