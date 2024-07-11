package com.tearsdr0p.scanskin.data.local.model

import com.tearsdr0p.scanskin.R

data class Doctor (
    val name: String,
    val image : Int,
    val hospital : String,
    val speciality : String,
    val schedule : String,
)

object DoctorList {

    val doctor = listOf(
        Doctor(
            name = "Dr. John Doe",
            image = R.drawable.doctor2,
            hospital = "Brien Hospital",
            speciality = "Dermatologist",
            schedule = "9:00 AM - 6:00 PM"
        ),
        Doctor(
            name = "Dr. Jane Doe",
            image = R.drawable.doctor3,
            hospital = "Brien Hospital",
            speciality = "Dermatologist",
            schedule = "9:00 AM - 6:00 PM"
        ),
        Doctor(
            name = "Dr. John Doe",
            image = R.drawable.doctor2,
            hospital = "Brien Hospital",
            speciality = "Dermatologist",
            schedule = "9:00 AM - 6:00 PM"
        ),
        Doctor(
            name = "Dr. Jane Doe",
            image = R.drawable.doctor3,
            hospital = "Brien Hospital",
            speciality = "Dermatologist",
            schedule = "9:00 AM - 6:00 PM"
        ),
        Doctor(
            name = "Dr. John Doe",
            image = R.drawable.doctor2,
            hospital = "Brien Hospital",
            speciality = "Dermatologist",
            schedule = "9:00 AM - 6:00 PM"
        ),
        Doctor(
            name = "Dr. Jane Doe",
            image = R.drawable.doctor3,
            hospital = "Brien Hospital",
            speciality = "Dermatologist",
            schedule = "9:00 AM - 6:00 PM"
        ),
        Doctor(
            name = "Dr. John Doe",
            image = R.drawable.doctor2,
            hospital = "Brien Hospital",
            speciality = "Dermatologist",
            schedule = "9:00 AM - 6:00 PM"
        ),
        Doctor(
            name = "Dr. Jane Doe",
            image = R.drawable.doctor3,
            hospital = "Brien Hospital",
            speciality = "Dermatologist",
            schedule = "9:00 AM - 6:00 PM"
        ),Doctor(
            name = "Dr. John Doe",
            image = R.drawable.doctor2,
            hospital = "Brien Hospital",
            speciality = "Dermatologist",
            schedule = "9:00 AM - 6:00 PM"
        ),
        Doctor(
            name = "Dr. Jane Doe",
            image = R.drawable.doctor3,
            hospital = "Brien Hospital",
            speciality = "Dermatologist",
            schedule = "9:00 AM - 6:00 PM"
        )
    )
}