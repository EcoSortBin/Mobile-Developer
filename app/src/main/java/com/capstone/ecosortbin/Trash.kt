package com.capstone.ecosortbin

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Trash(
    val dataType: String,
    val description: String,
    val image: Int
) : Parcelable
