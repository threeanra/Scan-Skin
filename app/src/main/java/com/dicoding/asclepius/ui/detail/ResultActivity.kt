package com.dicoding.asclepius.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.ui.check.CheckActivity
import com.dicoding.asclepius.ui.MainActivity

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.
        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        imageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.resultImage.setImageURI(it)
        }

        val resultText = intent.getStringExtra(EXTRA_RESULT)
        binding.resultText.text = resultText

        binding.apply {
            checkAgainButton.setOnClickListener {
                val intent = Intent(this@ResultActivity, CheckActivity::class.java)
                startActivity(intent)
            }
            homeButton.setOnClickListener {
                val intent = Intent(this@ResultActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT = "extra_result"
    }
}