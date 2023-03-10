package com.geniobits.recipeapp.views.recipeBrowser.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.geniobits.recipeapp.databinding.FragmentRecipeBrowserBinding
import com.geniobits.recipeapp.data.network.RetrofitHelper
import com.geniobits.recipeapp.data.network.WebServices
import com.geniobits.recipeapp.utils.ResultResponse
import com.geniobits.recipeapp.data.db.dao.RecipeDao
import com.geniobits.recipeapp.data.db.RecipeDatabase
import com.geniobits.recipeapp.views.recipeBrowser.listener.FavoriteClickListener
import com.geniobits.recipeapp.views.recipeBrowser.listener.RecipeClickListener
import com.geniobits.recipeapp.views.recipeBrowser.model.Recipe
import com.geniobits.recipeapp.views.recipeBrowser.repository.RecipeBrowserRepositoryImpl
import com.geniobits.recipeapp.views.recipeBrowser.viewmodel.RecipeBrowserViewModel
import com.geniobits.recipeapp.views.recipeBrowser.viewmodel.RecipeBrowserViewModelFactory
import com.geniobits.recipeapp.views.meal.recipeDetails.RecipeDetailsActivity
import com.google.gson.Gson

class RecipeBrowserFragment : Fragment() {

    private lateinit var binding: FragmentRecipeBrowserBinding
    private lateinit var viewModel: RecipeBrowserViewModel
    private var adapter = RecipeBrowserAdapter()
    private var mFavoriteRecipe = arrayListOf<Recipe>()
    private var isFirstTimeCalled = false

    companion object {
        fun getInstance(): RecipeBrowserFragment {
            return RecipeBrowserFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeBrowserBinding.inflate(inflater, container, false)
        setupData()
        binding.ivProgress.visibility = View.VISIBLE
        getFavouriteRecipe()
        onSearchQueryListener()
        onSearchCloseListener()
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

    private fun onRecipeObserver(query: String) {
        hideKeyboard()
        binding.ivProgress.visibility = View.VISIBLE
        binding.rvRecipes.itemAnimator = null
        viewModel.getRecipes(query)
            .observe(viewLifecycleOwner) {
                when (it) {
                    is ResultResponse.Error -> {
                        Log.e("TAG", "onRecipeObserver: " + it.message)
                        binding.ivProgress.visibility = View.GONE
                        binding.rvRecipes.visibility = View.GONE
                        binding.tvNotFound.visibility = View.GONE
                    }
                    is ResultResponse.Success -> {
                        if (it.data != null) {
                            if (it.data.recipes != null) {
                                if (it.data.recipes.isNotEmpty()) {
                                    binding.rvRecipes.visibility = View.VISIBLE
                                    binding.tvNotFound.visibility = View.GONE
                                    setupRecipeAdapter(mData = it.data.recipes)
                                } else {
                                    binding.rvRecipes.visibility = View.GONE
                                    binding.tvNotFound.visibility = View.VISIBLE
                                }
                            }
                        }
                        binding.ivProgress.visibility = View.GONE
                    }
                }
            }
    }

    private fun onSearchQueryListener() {
        //binding.searchView.queryHint = "HEY"
        binding.searchView.isIconified = false
        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                hideKeyboard()
                binding.searchView.clearFocus()
                onRecipeObserver(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.clearData()
                val count = newText?.length ?: 0
                if (count == 0) {
                    onRecipeObserver("")
                }
                return false
            }

        })
    }

    private fun onSearchCloseListener() {
        binding.searchView.setOnCloseListener {
            onRecipeObserver("")
            return@setOnCloseListener false
        }

    }

    private fun setupRecipeAdapter(mData: List<Recipe>) {
        val mRecipe = arrayListOf<Recipe>()
        if (mFavoriteRecipe.isNotEmpty()) {
            mData.forEachIndexed { index, recipe ->
                mFavoriteRecipe.forEachIndexed { subindex, favRecipe ->
                    if (!recipe.strMeal.isNullOrEmpty()) {
                        if (recipe.strMeal.equals(favRecipe.strMeal)) {
                            recipe.isSelectFavorite = true
                        }
                    }
                }
                mRecipe.add(recipe)
            }
        }else {
            mRecipe.addAll(mData)
        }
        binding.rvRecipes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRecipes.adapter = adapter
        adapter.setList(mRecipe)
    }

    private fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchView.windowToken, 0)
    }

    private fun setAdapterListener() {
        adapter.setRecipeClickListener(object : RecipeClickListener {
            override fun onRecipe(recipe: Recipe) {
                val intent = Intent(requireActivity(), RecipeDetailsActivity::class.java)
                val gson = Gson()
                val recipeJSONString = gson.toJson(recipe)
                intent.putExtra(RecipeDetailsActivity.RECIPE_KEY, recipeJSONString)
                requireActivity().startActivity(intent)
            }
        })

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

    private fun getFavouriteRecipe() {
        viewModel.getFavouriteRecipes().observe(viewLifecycleOwner) {
            when (it) {
                is ResultResponse.Error -> {
                    if (!isFirstTimeCalled) {
                        onRecipeObserver("")
                        isFirstTimeCalled = true
                    }
                }
                is ResultResponse.Success -> {
                    mFavoriteRecipe = it.data as ArrayList<Recipe>
                    if (!isFirstTimeCalled) {
                        onRecipeObserver("")
                        isFirstTimeCalled = true
                    }
                }
            }
        }
    }
}