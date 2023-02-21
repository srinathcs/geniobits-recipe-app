package com.geniobits.recipeapp.views

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.geniobits.recipeapp.R
import com.geniobits.recipeapp.databinding.DummyViewBinding
import com.geniobits.recipeapp.views.recipeBrowser.presentation.RecipeBrowserFragment

class DummyViewActivity : AppCompatActivity() {

    private lateinit var binding: DummyViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DummyViewBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        addFragmentToFragment(RecipeBrowserFragment.getInstance())
    }

    private fun addFragmentToFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.flContainer, fragment, "My Fragment")
        ft.commitAllowingStateLoss()
    }
}