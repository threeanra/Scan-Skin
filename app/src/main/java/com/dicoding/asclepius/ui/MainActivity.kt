package com.dicoding.asclepius.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.adapter.ArticleAdapter
import com.dicoding.asclepius.data.ResultState
import com.dicoding.asclepius.data.network.response.ArticlesItem
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.ui.check.CheckActivity
import com.dicoding.asclepius.viewmodel.ArticleViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationMain.setupWithNavController(navController)

        binding.bottomNavigationMain.apply {
            menu.getItem(2).isEnabled = false
        }

        binding.fab.setOnClickListener {
            val intent = Intent(this@MainActivity, CheckActivity::class.java)
            startActivity(intent)
        }

    }
}