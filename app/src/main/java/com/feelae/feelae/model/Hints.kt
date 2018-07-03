package com.feelae.feelae.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hints(
        var id: Int,
        var title: String,
        var body: String,
        var imageUri: String
) : Parcelable