package com.tearsdr0p.scanskin.ui.article

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tearsdr0p.scanskin.R
import com.tearsdr0p.scanskin.adapter.ArticlesAdapter
import com.tearsdr0p.scanskin.data.ResultState
import com.tearsdr0p.scanskin.data.network.response.ArticlesItem
import com.tearsdr0p.scanskin.databinding.FragmentArticleBinding
import com.tearsdr0p.scanskin.viewmodel.ArticleViewModel

class ArticleFragment : Fragment() {

    private val viewModel: ArticleViewModel by viewModels()
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)

        viewModel.getArticles()
        viewModel.articles2.observe(this) { response ->
            when (response) {
                is ResultState.Loading -> {
                  showLoading(true)
                }

                is ResultState.Error -> {
                    showLoading(false)
                    Toast.makeText(
                        requireContext(),
                        response.error,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ResultState.Success -> {
                    showLoading(false)
                    setArticle(response.data)
                }
            }
        }

        setTransparentStatusBar()
        return binding.root
    }

    private fun setTransparentStatusBar() {
        activity?.window?.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            statusBarColor = ContextCompat.getColor(requireContext(), R.color.background_color)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            } else {
                @Suppress("DEPRECATION")
                decorView.systemUiVisibility = decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loading.visibility = View.VISIBLE
        } else {
            binding.loading.visibility = View.GONE
        }
    }

    private fun setArticle(items: List<ArticlesItem>) {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvArticles.layoutManager = layoutManager
        val adapter = ArticlesAdapter()
        adapter.submitList(items)
        binding.rvArticles.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}