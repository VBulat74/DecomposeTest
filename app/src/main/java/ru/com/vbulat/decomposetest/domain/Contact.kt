package ru.com.vbulat.decomposetest.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val id : Int = -1,
    val username : String,
    val phone : String,
) : Parcelable
