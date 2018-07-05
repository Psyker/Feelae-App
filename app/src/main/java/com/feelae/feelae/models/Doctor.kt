package com.feelae.feelae.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Doctor(
        var id: Int,
        var firstname: String,
        var lastname: String,
        var gender: Boolean,
        var age: Int
) : Parcelable