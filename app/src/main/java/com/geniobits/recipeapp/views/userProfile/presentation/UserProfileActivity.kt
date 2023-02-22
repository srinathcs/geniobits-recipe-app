package com.geniobits.recipeapp.views.userProfile.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.geniobits.recipeapp.databinding.ActivityUserBinding
import com.geniobits.recipeapp.utils.ResultResponse
import com.geniobits.recipeapp.data.db.RecipeDatabase
import com.geniobits.recipeapp.data.db.dao.ProfileDao
import com.geniobits.recipeapp.views.userProfile.model.Profile
import com.geniobits.recipeapp.views.userProfile.repository.ProfileRepositoryImpl
import com.geniobits.recipeapp.views.userProfile.viewModel.ProfileViewModel
import com.geniobits.recipeapp.views.userProfile.viewModel.ProfileViewModelFactory


class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private lateinit var viewModel: ProfileViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        supportActionBar?.hide()
        setupData()
        setupEvent()
        getProfile()
    }

    private fun setupData() {
        val recipeDatabase = RecipeDatabase.getInstance(this)
        var profileDao: ProfileDao? = null
        if (recipeDatabase != null) {
            profileDao = recipeDatabase.profileDao()
        }
        val repository = ProfileRepositoryImpl(profileDao)
        val factory = ProfileViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]
    }

    private fun setupEvent() {
        binding.btnSave.setOnClickListener {
            if (!binding.btnSave.text.isNullOrEmpty() && binding.btnSave.text.equals("Save")) {
                if (isValidate()) {
                    saveProfile(generateProfile())
                } else {
                    showToast()
                }
            }else {
                if (isValidate()) {
                    updateProfile(generateProfile())
                } else {
                   showToast()
                }
            }
        }
    }

    private fun saveProfile(profile: Profile) {
        viewModel.saveProfile(profile).observe(this) {
            when (it) {
                is ResultResponse.Error -> {
                    Toast.makeText(
                        this,
                        "Hello, We unable to store your favorite recipe. Please contact IT support team. Thank you",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is ResultResponse.Success -> {
                    Log.e("TAG", "saveFavoriteRecipe: Saved")
                    getProfile()
                }
            }

        }
    }

    private fun updateProfile(profile: Profile) {
        viewModel.updateProfile(profile).observe(this) {
            when (it) {
                is ResultResponse.Error -> {
                    Toast.makeText(
                        this,
                        "Hello, We unable to store your favorite recipe. Please contact IT support team. Thank you",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is ResultResponse.Success -> {
                    Log.e("TAG", "saveFavoriteRecipe: Saved")
                    getProfile()
                }
            }

        }
    }

    private fun getProfile() {
        viewModel.getProfile().observe(this) {
            when (it) {
                is ResultResponse.Error -> {

                }
                is ResultResponse.Success -> {
                    val mProfile = it.data as ArrayList<Profile>
                    if (mProfile.isNotEmpty()) {
                        setData(mProfile[0])
                        binding.btnSave.text = "Update"
                    } else {
                        binding.btnSave.text = "Save"
                    }

                }
            }
        }
    }

    private fun setData(profile: Profile) {
        binding.tetFirstName.setText(profile.firstName)
        binding.tetLastName.setText(profile.lastName)
        binding.tetPhoneNumber.setText(profile.phoneNumber)
        binding.tetAddress.setText(profile.address)
        binding.tetAge.setText(profile.age.toString())
    }

    private fun isValidate(): Boolean {
        val mFirstname = binding.tetFirstName.text?.trim() ?: ""
        val mLastname = binding.tetLastName.text?.trim() ?: ""
        val mAge = binding.tetAge.text?.trim() ?: ""
        val mPhoneNumber = binding.tetPhoneNumber.text?.trim() ?: ""
        val mAddress = binding.tetAddress.text?.trim() ?: ""
        return mFirstname.isNotEmpty() && mLastname.isNotEmpty() && mAge.isNotEmpty() && mPhoneNumber.isNotEmpty() && mAddress.isNotEmpty()
    }

    private fun generateProfile(): Profile {
        val mFirstname = binding.tetFirstName.text ?: ""
        val mLastname = binding.tetLastName.text ?: ""
        val mAge = binding.tetAge.text ?: ""
        val mPhoneNumber = binding.tetPhoneNumber.text ?: ""
        val mAddress = binding.tetAddress.text ?: ""
        return Profile(
            1,
            mFirstname.trim().toString(),
            mLastname.trim().toString(),
            mPhoneNumber.trim().toString(),
            mAge.trim().toString().toInt(),
            mAddress.trim().toString()
        )
    }

    private fun showToast() {
        Toast.makeText(
            this,
            "Please fill all the filed of form. Thank you",
            Toast.LENGTH_SHORT
        ).show()
    }

}