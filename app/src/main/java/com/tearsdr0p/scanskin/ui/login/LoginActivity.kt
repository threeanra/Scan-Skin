package com.tearsdr0p.scanskin.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tearsdr0p.scanskin.data.local.model.User
import com.tearsdr0p.scanskin.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edPasswordEmail.text.toString()

            if (email == "test@gmail.com" && password == "test123") {
                 User(
                    id = "user_id",
                    name = "name",
                    email = email,
                    token = "token_user"
                )
            } else {
                Toast.makeText(
                    this@LoginActivity,
                    "Isi email dan password terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}