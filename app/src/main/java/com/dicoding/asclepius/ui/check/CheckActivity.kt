package com.dicoding.asclepius.ui.check

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityCheckBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.helper.getImageUri
import com.dicoding.asclepius.ui.result.ResultActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tensorflow.lite.task.vision.classifier.Classifications

class CheckActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckBinding

    private var currentImageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.apply {
            imgBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
                finish()
            }
            galleryButton.setOnClickListener { startGallery() }
            cameraButton.setOnClickListener { startCamera() }
            resetButton.setOnClickListener { reset() }
            analyzeButton.setOnClickListener{
                binding.analyzeButton.apply {
                    isEnabled = false
                    text = "Please wait..."
                }
                lifecycleScope.launch(Dispatchers.IO) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        currentImageUri?.let {
                            analyzeImage(it)
                        }
                    }, 3000)
                }
            }
        }

    }

    private fun reset() {
        currentImageUri = null
        binding.apply {
            previewImageView.setImageResource(R.drawable.image_preview)
            analyzeButton.text = "Analyze"
            resetButton.isEnabled = false
            analyzeButton.isEnabled = false
            cameraButton.isEnabled = true
            galleryButton.isEnabled = true
        }
    }
    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun startGallery() {
        launcherGallery.launch(arrayOf("image/*"))
    }
    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        // TODO: Menampilkan gambar sesuai Gallery yang dipilih.
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            if (currentImageUri != null) {
                binding.previewImageView.setImageURI(it)
                binding.analyzeButton.isEnabled = true
                binding.resetButton.isEnabled = true
                binding.cameraButton.isEnabled = false
                binding.galleryButton.isEnabled = false
            }
        }
    }

    private fun analyzeImage(image: Uri) {
        // TODO: Menganalisa gambar yang berhasil ditampilkan.
        val imageHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    showToast(error)
                }

                override fun onResults(results: List<Classifications>?) {
                    val resultString = results?.joinToString("\n") {
//                        val threshold = (it.categories[0].score * 100).toInt()
                        "${it.categories[0].label}"
                    }

                    val threshold = (results!![0].categories[0].score * 100).toInt()
                    Log.d("Threshold", "onResults: $threshold")

                    if (resultString != null) {
                        lifecycleScope.launch(Dispatchers.IO) {
                            this@CheckActivity.runOnUiThread {
                                moveToResult(image, resultString, threshold)
                            }
                        }
                    }
                }
            }
        )
        imageHelper.classifyStaticImage(image)
        binding.analyzeButton.text = "Done"
    }

    private fun moveToResult(image:Uri, result: String, threshold: Int){
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(ResultActivity.EXTRA_IMAGE_URI, image.toString())
        intent.putExtra(ResultActivity.EXTRA_RESULT, result)
        intent.putExtra(ResultActivity.EXTRA_THRESHOLD, threshold)
        startActivity(intent)
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE
    }
}