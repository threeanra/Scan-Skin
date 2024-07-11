package com.tearsdr0p.scanskin.ui.consultation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.tearsdr0p.scanskin.R
import com.tearsdr0p.scanskin.adapter.DoctorAdapter
import com.tearsdr0p.scanskin.data.local.model.DoctorList
import com.tearsdr0p.scanskin.databinding.ActivityConsultBinding

class ConsultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConsultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        setRecyclerView()
    }

    private fun setRecyclerView () {
        val layoutManager = LinearLayoutManager(this)
        binding.rvDoctor.layoutManager = layoutManager
        val adapter = DoctorAdapter(DoctorList.doctor)
        binding.rvDoctor.adapter = adapter
    }
}