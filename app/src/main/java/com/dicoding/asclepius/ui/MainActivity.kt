package com.dicoding.asclepius.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.adapter.ArticleAdapter
import com.dicoding.asclepius.data.network.response.ArticlesItem
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.viewmodel.ArticleViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: ArticleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            checkButton.setOnClickListener {
                val intent = Intent(this@MainActivity, CheckActivity::class.java)
                startActivity(intent)
            }
            historyButton.setOnClickListener {
                showToast("Result History not implemented yet!")
            }
        }

        //adapter for RecycleView
        val layoutManager = LinearLayoutManager(this)
        binding.rvArticle.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvArticle.addItemDecoration(itemDecoration)

        viewModel.isLoading.observe(this) { load ->
            showLoading(load)
        }

        // setArticle to RecycleView
        viewModel.articles.observe(this) { response ->
            setArticle(response)
        }
    }

    private fun setArticle(items: List<ArticlesItem>) {
        val adapter = ArticleAdapter()
        adapter.submitList(items)
        binding.rvArticle.adapter = adapter
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}