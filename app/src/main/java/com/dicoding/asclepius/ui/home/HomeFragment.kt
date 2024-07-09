package com.dicoding.asclepius.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.adapter.ArticleAdapter
import com.dicoding.asclepius.data.ResultState
import com.dicoding.asclepius.data.network.response.ArticlesItem
import com.dicoding.asclepius.databinding.FragmentHomeBinding
import com.dicoding.asclepius.ui.check.CheckActivity
import com.dicoding.asclepius.ui.consultation.ConsultActivity
import com.dicoding.asclepius.viewmodel.ArticleViewModel
import java.util.Calendar

class HomeFragment : Fragment() {

    private val viewModel: ArticleViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.apply {
            tvGreeting.text = getGreeting()
            cardConsultation.setOnClickListener {
                startActivity(Intent(requireContext(), ConsultActivity::class.java))
            }
            notification.setOnClickListener {
                showToast("Coming Soon")
            }
            cardOther.setOnClickListener {
                showToast("Coming Soon")
            }
            checkNowButton.setOnClickListener {
                startActivity(Intent(requireContext(), CheckActivity::class.java))
            }
        }

        viewModel.getTopArticles()
        viewModel.articles.observe(this) { response ->
            when (response) {
                is ResultState.Loading -> {
//                  showLoading(true)
                }

                is ResultState.Error -> {
//                    showLoading(false)
                    showToast(response.error)
                }

                is ResultState.Success -> {
//                    showLoading(false)
                    setArticle(response.data)
                }
            }
        }

        return binding.root
    }

    private fun getGreeting(): String {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        return when (hour) {
            in 0..11 -> "Good Morning"
            in 12..17 -> "Good Afternoon"
            else -> "Good Evening"
        }
    }

    private fun setArticle(items: List<ArticlesItem>) {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvArticle.layoutManager = layoutManager
        val adapter = ArticleAdapter(requireContext())
        adapter.submitList(items)
        binding.rvArticle.adapter = adapter
    }

//    private fun showLoading(isLoading: Boolean) {
//        if (isLoading) {
//            binding.progressBar.visibility = View.VISIBLE
//        } else {
//            binding.progressBar.visibility = View.GONE
//        }
//    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}