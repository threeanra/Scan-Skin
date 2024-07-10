package com.tearsdr0p.scanskin.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tearsdr0p.scanskin.R
import com.tearsdr0p.scanskin.factory.ViewModelFactory
import com.tearsdr0p.scanskin.ui.MainActivity
import com.tearsdr0p.scanskin.ui.onboarding.AuthViewModel
import com.tearsdr0p.scanskin.ui.onboarding.OnBoardingActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val viewModel: AuthViewModel by viewModels<AuthViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.getUser().observe(this) { user ->
                if (user != null && user.token.isNotEmpty()) {
                    goToMainActivity()
                } else {
                    startActivity(Intent(this, OnBoardingActivity::class.java))
                    finish()
                }
            }
        }, 3000L)

        //Full screen mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun goToMainActivity() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}