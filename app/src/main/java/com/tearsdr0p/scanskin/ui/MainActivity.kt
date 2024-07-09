package com.tearsdr0p.scanskin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.tearsdr0p.scanskin.R
import com.tearsdr0p.scanskin.databinding.ActivityMainBinding
import com.tearsdr0p.scanskin.ui.check.CheckActivity

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