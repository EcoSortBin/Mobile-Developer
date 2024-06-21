package com.capstone.ecosortbin.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdditionalInfo(
    val jenis: String,
    val deskripsi: String,
    val manfaat: String
) : Parcelable
