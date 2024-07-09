package com.tearsdr0p.scanskin.ui.result

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.tearsdr0p.scanskin.R
import com.tearsdr0p.scanskin.databinding.ActivityResultBinding
import com.tearsdr0p.scanskin.ui.MainActivity
import com.tearsdr0p.scanskin.ui.consultation.ConsultActivity

@Suppress("DEPRECATION")
class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var i = 0
    private var resultText: String? = null
    private var resultDetail : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        imageUri?.let {
            Log.d("Image URI", "showImage: $it")
//            binding.resultImage.setImageURI(it)
        }

        resultText = intent.getStringExtra(EXTRA_RESULT)
        val threshold = intent.getIntExtra(EXTRA_THRESHOLD, 0)
        resultDetail = "$resultText $threshold%"
        Log.d("Result Detail", "onCreate: $resultDetail")

        if (resultText!!.contains("Non")) {
            binding.textDesc.text = getString(R.string.non_cancer_desc)
        } else {
            binding.textDesc.text = getString(R.string.cancer_desc)
        }

        binding.apply {
            imgBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
                finish()
            }
            homeButton.setOnClickListener {
                val intent = Intent(this@ResultActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            consultButton.setOnClickListener {
                val intent = Intent(this@ResultActivity, ConsultActivity::class.java)
                startActivity(intent)
            }
        }

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (i <= threshold) {
                    binding.progressNumber.text = "$i%"
                    binding.progressNumber.setTextColor(getProgressColor(i))
                    binding.progressText.text = resultText
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
            resultText!!.contains("Non") || progress in 0..9 -> resources.getColor(R.color.green) // Green for non-cancer
            progress in 10..49 -> resources.getColor(R.color.main_color)
            progress in 50..80 -> resources.getColor(R.color.orange)
            progress in 81..100 -> resources.getColor(R.color.red)
            else -> resources.getColor(R.color.main_color, theme) // Default color
        }
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT = "extra_result"
        const val EXTRA_THRESHOLD = "extra_threshold"
    }
}