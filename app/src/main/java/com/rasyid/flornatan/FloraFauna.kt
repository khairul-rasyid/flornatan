package com.rasyid.flornatan

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FloraFauna(
    val name: String,
    val desc: String,
    val photo: Int
) : Parcelable
