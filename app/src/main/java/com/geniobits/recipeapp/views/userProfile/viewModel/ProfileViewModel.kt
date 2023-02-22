package com.geniobits.recipeapp.views.userProfile.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geniobits.recipeapp.utils.NoData
import com.geniobits.recipeapp.utils.ResultResponse
import com.geniobits.recipeapp.views.userProfile.model.Profile
import com.geniobits.recipeapp.views.userProfile.repository.ProfileRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {
    private var mSaveProfile = MutableLiveData<ResultResponse<NoData>>()
    private var mUpdateProfile = MutableLiveData<ResultResponse<Int>>()
    private var mGetProfile = MutableLiveData<ResultResponse<List<Profile>>>()

    fun saveProfile(profile: Profile): LiveData<ResultResponse<NoData>> {
        viewModelScope.launch {
            try {
                repository.saveProfile(profile)
                mSaveProfile.postValue(ResultResponse.Success(NoData()))
            } catch (e: Exception) {
                e.printStackTrace()
                mSaveProfile.postValue(ResultResponse.Error(e.message ?: ""))
            }
        }
        return mSaveProfile
    }

    fun getProfile(): LiveData<ResultResponse<List<Profile>>> {
        viewModelScope.launch {
            try {
                val response = repository.getProfile() ?: arrayListOf()
                mGetProfile.postValue(ResultResponse.Success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                mGetProfile.postValue(ResultResponse.Error(e.message ?: ""))
            }
        }
        return mGetProfile
    }

    fun updateProfile(profile: Profile): LiveData<ResultResponse<Int>> {
        viewModelScope.launch {
            try {
                val data = repository.updateProfile(profile)
                data?.let {
                    mUpdateProfile.postValue(ResultResponse.Success(data))
                }?:mUpdateProfile.postValue(ResultResponse.Error(""))

            } catch (e: Exception) {
                e.printStackTrace()
                mUpdateProfile.postValue(ResultResponse.Error(e.message ?: ""))
            }
        }
        return mUpdateProfile
    }
}