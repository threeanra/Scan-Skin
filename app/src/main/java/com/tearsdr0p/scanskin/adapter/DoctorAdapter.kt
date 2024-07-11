package com.tearsdr0p.scanskin.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tearsdr0p.scanskin.data.local.model.Doctor
import com.tearsdr0p.scanskin.data.local.model.DoctorList
import com.tearsdr0p.scanskin.databinding.DoctorCardBinding

class DoctorAdapter(
    private val doctor: List<Doctor>
) : RecyclerView.Adapter<DoctorAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DoctorCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = doctor.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val doctor = doctor[position]
        holder.bind(doctor)
    }

    class MyViewHolder(private val binding: DoctorCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(doctor: Doctor) {
            binding.doctorName.text = doctor.name
            binding.nameHospital.text = doctor.hospital
            binding.speciality.text = doctor.speciality
            binding.schedule.text = doctor.schedule

            Glide.with(binding.doctorImage.context)
                .load(doctor.image)
                .into(binding.doctorImage)

            binding.root.setOnClickListener {
                Toast.makeText(binding.root.context, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
        }
    }
}