package com.geniobits.recipeapp.views.recipeDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geniobits.recipeapp.databinding.FragmentRecipeDetailsBinding
import com.geniobits.recipeapp.views.recipeBrowser.model.Recipe
import com.google.gson.Gson

class RecipeDetailsFragment : Fragment() {
    private lateinit var binding: FragmentRecipeDetailsBinding
    private lateinit var recipe: Recipe


    companion object {
        const val RECIPE_KEY = "RECIPE_KEY"
        fun getInstance(): RecipeDetailsFragment {
            return RecipeDetailsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)
        getIntentData()
        setUpDetail()
        return binding.root
    }

    private fun setUpDetail() {
        if (this::recipe.isInitialized) {
            val measurement1 = recipe.strMeasure1 ?: ""
            val measurement2 = recipe.strMeasure2 ?: ""
            val measurement3 = recipe.strMeasure3 ?: ""
            val measurement4 = recipe.strMeasure4 ?: ""
            val measurement5 = recipe.strMeasure5 ?: ""
            val measurement6 = recipe.strMeasure6 ?: ""
            val measurement7 = recipe.strMeasure7 ?: ""
            val measurement8 = recipe.strMeasure8 ?: ""
            val measurement9 = recipe.strMeasure9 ?: ""
            val measurement10 = recipe.strMeasure10 ?: ""
            val measurement11 = recipe.strMeasure11 ?: ""
            val measurement12 = recipe.strMeasure12 ?: ""
            val measurement13 = recipe.strMeasure13 ?: ""
            val measurement14 = recipe.strMeasure14 ?: ""
            val measurement15 = recipe.strMeasure15 ?: ""
            val measurement16 = recipe.strMeasure16 ?: ""
            val measurement17 = recipe.strMeasure17 ?: ""
            val measurement18 = recipe.strMeasure18 ?: ""
            val measurement19 = recipe.strMeasure19 ?: ""
            val measurement20 = recipe.strMeasure20 ?: ""
            val ingredient1 = recipe.strIngredient1 ?: ""
            val ingredient2 = recipe.strIngredient2 ?: ""
            val ingredient3 = recipe.strIngredient3 ?: ""
            val ingredient4 = recipe.strIngredient4 ?: ""
            val ingredient5 = recipe.strIngredient5 ?: ""
            val ingredient6 = recipe.strIngredient6 ?: ""
            val ingredient7 = recipe.strIngredient7 ?: ""
            val ingredient8 = recipe.strIngredient8 ?: ""
            val ingredient9 = recipe.strIngredient9 ?: ""
            val ingredient10 = recipe.strIngredient10 ?: ""
            val ingredient11 = recipe.strIngredient11 ?: ""
            val ingredient12 = recipe.strIngredient12 ?: ""
            val ingredient13 = recipe.strIngredient13 ?: ""
            val ingredient14 = recipe.strIngredient14 ?: ""
            val ingredient15 = recipe.strIngredient15 ?: ""
            val ingredient16 = recipe.strIngredient16 ?: ""
            val ingredient17 = recipe.strIngredient17 ?: ""
            val ingredient18 = recipe.strIngredient18 ?: ""
            val ingredient19 = recipe.strIngredient19 ?: ""
            val ingredient20 = recipe.strIngredient20 ?: ""

            if (measurement1.isNotEmpty() || ingredient1.isNotEmpty()) {
                binding.tvIngredient1.visibility = View.VISIBLE
                binding.tvIngredient1.text = getFinalIngredients(measurement1, ingredient1)
            } else {
                binding.tvIngredient1.visibility = View.GONE
            }

            if (measurement2.isNotEmpty() || ingredient2.isNotEmpty()) {
                binding.tvIngredient2.visibility = View.VISIBLE
                binding.tvIngredient1.text = getFinalIngredients(measurement2, ingredient2)
            } else {
                binding.tvIngredient2.visibility = View.GONE
            }

            if (measurement3.isNotEmpty() || ingredient3.isNotEmpty()) {
                binding.tvIngredient3.visibility = View.VISIBLE
                binding.tvIngredient1.text = getFinalIngredients(measurement3, ingredient3)
            } else {
                binding.tvIngredient3.visibility = View.GONE
            }

            if (measurement4.isNotEmpty() || ingredient4.isNotEmpty()) {
                binding.tvIngredient4.visibility = View.VISIBLE
                binding.tvIngredient1.text = getFinalIngredients(measurement4, ingredient4)
            } else {
                binding.tvIngredient4.visibility = View.GONE
            }

            if (measurement5.isNotEmpty() || ingredient5.isNotEmpty()) {
                binding.tvIngredient5.visibility = View.VISIBLE
                binding.tvIngredient1.text = getFinalIngredients(measurement5, ingredient5)
            } else {
                binding.tvIngredient5.visibility = View.GONE
            }

            if (measurement6.isNotEmpty() || ingredient6.isNotEmpty()) {
                binding.tvIngredient6.visibility = View.VISIBLE
                binding.tvIngredient1.text = getFinalIngredients(measurement6, ingredient6)
            } else {
                binding.tvIngredient6.visibility = View.GONE
            }

            if (measurement7.isNotEmpty() || ingredient7.isNotEmpty()) {
                binding.tvIngredient7.visibility = View.VISIBLE
                binding.tvIngredient1.text = getFinalIngredients(measurement7, ingredient7)
            } else {
                binding.tvIngredient7.visibility = View.GONE
            }

            if (measurement8.isNotEmpty() || ingredient8.isNotEmpty()) {
                binding.tvIngredient8.visibility = View.VISIBLE
                binding.tvIngredient1.text = getFinalIngredients(measurement8, ingredient8)
            } else {
                binding.tvIngredient8.visibility = View.GONE
            }

            if (measurement9.isNotEmpty() || ingredient9.isNotEmpty()) {
                binding.tvIngredient9.visibility = View.VISIBLE
                binding.tvIngredient1.text = getFinalIngredients(measurement9, ingredient9)
            } else {
                binding.tvIngredient9.visibility = View.GONE
            }

            if (measurement10.isNotEmpty() || ingredient10.isNotEmpty()) {
                binding.tvIngredient10.visibility = View.VISIBLE
                binding.tvIngredient1.text = getFinalIngredients(measurement10, ingredient10)
            } else {
                binding.tvIngredient10.visibility = View.GONE
            }

            if (measurement11.isNotEmpty() || ingredient11.isNotEmpty()) {
                binding.tvIngredient11.visibility = View.VISIBLE
                binding.tvIngredient1.text = getFinalIngredients(measurement11, ingredient11)
            } else {
                binding.tvIngredient11.visibility = View.GONE
            }

            if (measurement12.isNotEmpty() || ingredient12.isNotEmpty()) {
                binding.tvIngredient12.visibility = View.VISIBLE
                binding.tvIngredient1.text = getFinalIngredients(measurement12, ingredient12)
            } else {
                binding.tvIngredient12.visibility = View.GONE
            }

            if (measurement13.isNotEmpty() || ingredient13.isNotEmpty()) {
                binding.tvIngredient13.visibility = View.VISIBLE
                binding.tvIngredient1.text = getFinalIngredients(measurement13, ingredient13)
            } else {
                binding.tvIngredient13.visibility = View.GONE
            }

            if (measurement14.isNotEmpty() || ingredient14.isNotEmpty()) {
                binding.tvIngredient14.visibility = View.VISIBLE
                binding.tvIngredient1.text = getFinalIngredients(measurement14, ingredient14)
            } else {
                binding.tvIngredient14.visibility = View.GONE
            }

            if (measurement15.isNotEmpty() || ingredient15.isNotEmpty()) {
                binding.tvIngredient15.visibility = View.VISIBLE
                binding.tvIngredient1.text = getFinalIngredients(measurement15, ingredient15)
            } else {
                binding.tvIngredient15.visibility = View.GONE
            }

            if (measurement16.isNotEmpty() || ingredient16.isNotEmpty()) {
                binding.tvIngredient16.visibility = View.VISIBLE
                binding.tvIngredient1.text = getFinalIngredients(measurement16, ingredient16)
            } else {
                binding.tvIngredient16.visibility = View.GONE
            }

            if (measurement17.isNotEmpty() || ingredient17.isNotEmpty()) {
                binding.tvIngredient17.visibility = View.VISIBLE
                binding.tvIngredient1.text = getFinalIngredients(measurement17, ingredient17)
            } else {
                binding.tvIngredient17.visibility = View.GONE
            }

            if (measurement18.isNotEmpty() || ingredient18.isNotEmpty()) {
                binding.tvIngredient18.visibility = View.VISIBLE
                binding.tvIngredient1.text = getFinalIngredients(measurement18, ingredient18)
            } else {
                binding.tvIngredient18.visibility = View.GONE
            }

            if (measurement19.isNotEmpty() || ingredient19.isNotEmpty()) {
                binding.tvIngredient19.visibility = View.VISIBLE
                binding.tvIngredient1.text = getFinalIngredients(measurement19, ingredient19)
            } else {
                binding.tvIngredient19.visibility = View.GONE
            }

            if (measurement20.isNotEmpty() || ingredient20.isNotEmpty()) {
                binding.tvIngredient20.visibility = View.VISIBLE
                binding.tvIngredient1.text = getFinalIngredients(measurement20, ingredient20)
            } else {
                binding.tvIngredient20.visibility = View.GONE
            }
        }
    }

    private fun getFinalIngredients(measurement: String, ingredient: String): String {
        var mData = ""
        if (measurement.isNotEmpty()) {
            mData = measurement
        }
        if (ingredient.isNotEmpty()) {
            if (mData.isNotEmpty()) {
                mData.plus(" ").plus(ingredient)
            } else {
                mData = ingredient
            }
        }
        return mData
    }

    private fun getIntentData() {
        arguments?.let {
            val recipeJSONString = it.getString(RECIPE_KEY, "")
            val gson = Gson()
            recipe = gson.fromJson(recipeJSONString, Recipe::class.java)
        }
    }
}