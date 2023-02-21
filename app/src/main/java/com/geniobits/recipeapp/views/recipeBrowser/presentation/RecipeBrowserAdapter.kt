package com.geniobits.recipeapp.views.recipeBrowser.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geniobits.recipeapp.R
import com.geniobits.recipeapp.databinding.ItemRecipeBrowserBinding
import com.geniobits.recipeapp.views.recipeBrowser.listener.FavoriteClickListener
import com.geniobits.recipeapp.views.recipeBrowser.listener.RecipeClickListener
import com.geniobits.recipeapp.views.recipeBrowser.model.Recipe
import kotlinx.coroutines.withContext

class RecipeBrowserAdapter() : RecyclerView.Adapter<RecipeBrowserAdapter.RecipeViewHolder>() {

    private lateinit var mRecipeClickListener: RecipeClickListener
    private lateinit var mFavoriteClickListener: FavoriteClickListener

    private var mRecipes: List<Recipe> = arrayListOf()

    inner class RecipeViewHolder(val binding: ItemRecipeBrowserBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding =
            ItemRecipeBrowserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        with(holder) {
            with(mRecipes[position]) {
                binding.tvTitle.text = strMeal
                binding.tvDescription.text = strInstructions
                Glide.with(binding.root)
                    .load(strMealThumb)
                    .into(binding.ivImage)
                if (isSelectFavorite) {
                    Glide.with(binding.root)
                        .load(R.drawable.ic_favorite)
                        .into(binding.ivFavourite)
                } else {
                    Glide.with(binding.root)
                        .load(R.drawable.ic_favorite_border)
                        .into(binding.ivFavourite)
                }
                binding.ivFavourite.setOnClickListener {
                    isSelectFavorite = !isSelectFavorite
                    if (this@RecipeBrowserAdapter::mFavoriteClickListener.isInitialized) {
                        mFavoriteClickListener.onFavourite(isSelectFavorite,this)
                    }
                    notifyItemChanged(position)
                }

                binding.root.setOnClickListener {
                    if (this@RecipeBrowserAdapter::mRecipeClickListener.isInitialized) {
                        mRecipeClickListener.onRecipe(this)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mRecipes.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(mData: List<Recipe>) {
        this.mRecipes = mData
        notifyDataSetChanged()
    }

    fun setRecipeClickListener( mRecipeClickListener: RecipeClickListener) {
        this.mRecipeClickListener = mRecipeClickListener
    }

    fun setFavoriteClickListener(mFavoriteClickListener: FavoriteClickListener) {
        this.mFavoriteClickListener = mFavoriteClickListener
    }
}