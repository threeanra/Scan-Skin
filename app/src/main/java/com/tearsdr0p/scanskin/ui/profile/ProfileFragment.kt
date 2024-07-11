package com.tearsdr0p.scanskin.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.tearsdr0p.scanskin.R
import com.tearsdr0p.scanskin.databinding.FragmentHomeBinding
import com.tearsdr0p.scanskin.databinding.FragmentProfileBinding
import com.tearsdr0p.scanskin.factory.ViewModelFactory
import com.tearsdr0p.scanskin.ui.aboutus.AboutUsActivity
import com.tearsdr0p.scanskin.ui.login.LoginActivity
import com.tearsdr0p.scanskin.ui.onboarding.AuthViewModel

class ProfileFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels<AuthViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.apply {
            btnLogout.setOnClickListener {
                showLogoutDialog()
            }
            viewProfile.setOnClickListener {
                Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show()
            }
            viewAboutUs.setOnClickListener {
                val intent = Intent(requireContext(), AboutUsActivity::class.java)
                startActivity(intent)
            }
        }

        viewModel.getUser().observe(viewLifecycleOwner) { user ->
            binding.emailProfile.text = user.email
        }

        setStatusBarColor(R.color.main_color)

        return binding.root
    }

    private fun setStatusBarColor(colorResId: Int) {
        activity?.window?.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = ContextCompat.getColor(requireContext(), colorResId)
        }
    }

    private fun showLogoutDialog() {
        val dialogView = LayoutInflater.from(requireActivity()).inflate(R.layout.alert_dialog, null)

        val builder = AlertDialog.Builder(requireActivity())
            .setView(dialogView)
            .setCancelable(false)

        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent) //to make alert dialog rounded corner

        dialogView.findViewById<Button>(R.id.btnYes).setOnClickListener {
            viewModel.logout()
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finish()
            alertDialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.btnNo).setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}