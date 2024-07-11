package com.tearsdr0p.scanskin.ui.history

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tearsdr0p.scanskin.R
import com.tearsdr0p.scanskin.adapter.HistoryAdapter
import com.tearsdr0p.scanskin.databinding.FragmentArticleBinding
import com.tearsdr0p.scanskin.databinding.FragmentHistoryBinding
import com.tearsdr0p.scanskin.factory.ViewModelFactory


class HistoryFragment : Fragment() {
    private val viewModel: HistoryViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity().application)
    }
    private lateinit var historyAdapter: HistoryAdapter
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        setTransparentStatusBar()
        setupViewModel()
        setupRecyclerView()

        return binding.root
    }

    private fun setupViewModel() {
        viewModel.historyList.observe(viewLifecycleOwner) { historyList ->
            historyAdapter.submitList(historyList)
            binding.rvEmptyHistory.isVisible = historyList.isEmpty()
        }
    }

    private fun setupRecyclerView() {
        historyAdapter = HistoryAdapter(viewModel)
        binding.rvHistory.apply {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
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
}