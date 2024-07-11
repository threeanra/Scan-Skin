package com.tearsdr0p.scanskin.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.lifecycle.lifecycleScope
import com.tearsdr0p.scanskin.R
import com.tearsdr0p.scanskin.data.local.model.User
import com.tearsdr0p.scanskin.data.pref.UserPreferences
import com.tearsdr0p.scanskin.data.pref.dataStore
import com.tearsdr0p.scanskin.databinding.ActivityLoginBinding
import com.tearsdr0p.scanskin.ui.MainActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var pref: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = UserPreferences.getInstance(dataStore)
        showDialog()

        binding.loginGoogle.setOnClickListener {
            showDialog()
        }

        binding.login.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edPasswordEmail.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    if (email == "test@gmail.com" && password == "test123") {
                        pref.saveUser(User(
                            id = "user_id",
                            name = "name",
                            email = email,
                            token = "token_user"
                        ))
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Invalid email or password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                showLoading(false)
                Toast.makeText(
                    this@LoginActivity,
                    "Please fill in all fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.alert_dialog_login, null)

        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)

        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent) //to make alert dialog rounded corner

        dialogView.findViewById<Button>(R.id.btnClose).setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.login.isInvisible = isLoading
    }
}