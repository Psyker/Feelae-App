package com.feelae.feelae.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Symptoms(
        var id: Int,
        var name: String
) : Parcelable