package com.feelae.feelae.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Specialization(var name: String, var imageUri: String) : Parcelable
