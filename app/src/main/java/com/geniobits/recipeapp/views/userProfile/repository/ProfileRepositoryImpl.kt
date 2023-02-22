package com.geniobits.recipeapp.views.userProfile.repository

import com.geniobits.recipeapp.data.db.dao.ProfileDao
import com.geniobits.recipeapp.views.userProfile.model.Profile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfileRepositoryImpl(private val profileDao: ProfileDao?) : ProfileRepository {
    override suspend fun getProfile(): List<Profile>? {
        return withContext(Dispatchers.IO) {
            profileDao?.getProfile()
        }
    }

    override suspend fun saveProfile(profile: Profile) {
        return withContext(Dispatchers.IO) {
            profileDao?.insert(profile)
        }
    }

    override suspend fun updateProfile(profile: Profile): Int? {
        return withContext(Dispatchers.IO) {
            profileDao?.update(profile)
        }
    }
}