package com.geniobits.recipeapp.views.userProfile.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
class Profile(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firstName: String?,
    val lastName: String?,
    val phoneNumber: String?,
    val age: Int,
    val address: String?
)