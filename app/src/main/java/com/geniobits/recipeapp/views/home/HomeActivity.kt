package com.geniobits.recipeapp.views.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.geniobits.recipeapp.R
import com.geniobits.recipeapp.databinding.ActivityHomeBinding
import com.geniobits.recipeapp.views.userProfile.presentation.UserProfileActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(LayoutInflater.from(this))
        supportActionBar?.hide()
        setContentView(binding.root)
        setUpNavControl()
        onBottomSetup()
        onFABSetup()
    }

    private fun onFABSetup() {
        binding.myFab.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setUpNavControl() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.navigate(R.id.recipeFragment)
    }

    private fun onBottomSetup() {
        binding.bnvMenu.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.recipeMenu -> {
                    navController.navigate(R.id.recipeFragment)
                    true
                }
                R.id.favoriteMenu -> {
                    navController.navigate(R.id.favouriteFragment)
                    true
                }
                R.id.shopMenu -> {
                    navController.navigate(R.id.shoppingFragment)
                    true
                }
                R.id.mealMenu -> {
                    navController.navigate(R.id.mailFragment)
                    true
                }
                else -> false
            }
        }
    }
}