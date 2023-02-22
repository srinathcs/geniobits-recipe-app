package com.geniobits.recipeapp.views.favouriteRecipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.geniobits.recipeapp.databinding.FragmentFavoriteRecipeBinding
import com.geniobits.recipeapp.data.network.RetrofitHelper
import com.geniobits.recipeapp.data.network.WebServices
import com.geniobits.recipeapp.utils.ResultResponse
import com.geniobits.recipeapp.data.db.dao.RecipeDao
import com.geniobits.recipeapp.data.db.RecipeDatabase
import com.geniobits.recipeapp.views.recipeBrowser.listener.FavoriteClickListener
import com.geniobits.recipeapp.views.recipeBrowser.model.Recipe
import com.geniobits.recipeapp.views.recipeBrowser.presentation.RecipeBrowserAdapter
import com.geniobits.recipeapp.views.recipeBrowser.repository.RecipeBrowserRepositoryImpl
import com.geniobits.recipeapp.views.recipeBrowser.viewmodel.RecipeBrowserViewModel
import com.geniobits.recipeapp.views.recipeBrowser.viewmodel.RecipeBrowserViewModelFactory


class FavouriteRecipeFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteRecipeBinding
    private lateinit var viewModel: RecipeBrowserViewModel
    private var adapter = RecipeBrowserAdapter()
    private var mFavoriteRecipe = arrayListOf<Recipe>()


    companion object {
        fun getInstance(): FavouriteRecipeFragment {
            return FavouriteRecipeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteRecipeBinding.inflate(inflater, container, false)
        setupData()
        setUpAdapter()
        getFavouriteRecipe()
        setAdapterListener()
        return binding.root
    }

    private fun setupData() {
        val webServices = RetrofitHelper.getInstance().create(WebServices::class.java)
        val recipeDatabase = RecipeDatabase.getInstance(requireActivity())
        var recipeDao: RecipeDao? = null
        if (recipeDatabase != null) {
            recipeDao = recipeDatabase.recipeDao()
        }
        val repository = RecipeBrowserRepositoryImpl(webServices, recipeDao)
        val factory = RecipeBrowserViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[RecipeBrowserViewModel::class.java]
    }

    private fun setUpAdapter() {
        binding.ivFavoriteProgress.visibility = View.VISIBLE
        binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorite.adapter = adapter
    }

    private fun getFavouriteRecipe() {
        viewModel.getFavouriteRecipes().observe(viewLifecycleOwner) {
            when (it) {
                is ResultResponse.Error -> {
                    binding.ivFavoriteProgress.visibility = View.GONE
                }
                is ResultResponse.Success -> {

                    if (it.data.isNotEmpty()) {
                        binding.rvFavorite.visibility = View.VISIBLE
                        binding.tvNotFound.visibility = View.GONE
                        mFavoriteRecipe = it.data as ArrayList<Recipe>
                        adapter.setList(mFavoriteRecipe)
                    } else {
                        binding.rvFavorite.visibility = View.GONE
                        binding.tvNotFound.visibility = View.VISIBLE
                    }
                    binding.ivFavoriteProgress.visibility = View.GONE
                }
            }
        }
    }

    private fun setAdapterListener() {
        adapter.setFavoriteClickListener(object : FavoriteClickListener {
            override fun onFavourite(isClicked: Boolean, recipe: Recipe) {
                if (isClicked) {
                    saveFavoriteRecipe(recipe)
                } else {
                    deleteFavoriteRecipe(recipe)
                }
            }
        })
    }

    private fun saveFavoriteRecipe(recipe: Recipe) {
        viewModel.saveRecipe(recipe).observe(viewLifecycleOwner) {
            when (it) {
                is ResultResponse.Error -> {
                    Toast.makeText(
                        requireActivity(),
                        "Hello, We unable to store your favorite recipe. Please contact IT support team. Thank you",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is ResultResponse.Success -> {
                    Log.e("TAG", "saveFavoriteRecipe: Saved")
                    getFavouriteRecipe()
                }
            }

        }
    }

    private fun deleteFavoriteRecipe(recipe: Recipe) {
        viewModel.deleteRecipe(recipe).observe(viewLifecycleOwner) {
            when (it) {
                is ResultResponse.Error -> {
                    Toast.makeText(
                        requireActivity(),
                        "Hello, We unable to delete your favorite recipe. Please contact IT support team. Thank you",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is ResultResponse.Success -> {
                    Log.e("TAG", "deleteFavoriteRecipe: Deleted")
                    getFavouriteRecipe()
                }
            }
        }
    }
}