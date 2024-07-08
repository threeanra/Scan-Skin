package com.dicoding.asclepius.ui.result

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.ui.check.CheckActivity
import com.dicoding.asclepius.ui.MainActivity

@Suppress("DEPRECATION")
class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var i = 0
    private var resultText: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.
        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        imageUri?.let {
            Log.d("Image URI", "showImage: $it")
//            binding.resultImage.setImageURI(it)
        }

        resultText = intent.getStringExtra(EXTRA_RESULT)
        val threshold = intent.getIntExtra(EXTRA_THRESHOLD, 0)
        Log.d("threshold", "onCreate: $threshold")
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

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (i <= threshold) {
                    binding.progressText.text = i.toString() + "%"
                    binding.progressText.setTextColor(getProgressColor(i))
                    binding.resultIndicator.progress = i
                    binding.resultIndicator.setIndicatorColor(getProgressColor(i))
                    i++
                    handler.postDelayed(this, 0)
                } else {
                    handler.removeCallbacks(this)
                }
            }
        }, 5)
    }

    private fun getProgressColor(progress: Int): Int {
        return when {
//            resultText!!.contains("Non") || progress in 0..9 -> Color.parseColor("#00FF00") // Green for non-cancer
            resultText!!.contains("Non") || progress in 0..9 -> resources.getColor(R.color.main_color) // Green for non-cancer
            progress in 10..49 -> Color.parseColor("#0000FF") // Blue
            progress in 50..80 -> Color.parseColor("#FFA500") // Orange
            progress in 81..100 -> resources.getColor(R.color.red, theme) // Red
            else -> resources.getColor(R.color.red, theme) // Default color
        }
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT = "extra_result"
        const val EXTRA_THRESHOLD = "extra_threshold"
    }
}