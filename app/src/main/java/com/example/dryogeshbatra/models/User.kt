package com.example.shopiz.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var password: String = "",
    var image: String = "",
    var mobile: Long = 0,
    var gender: String = "",
    var profileCompleted: Int = 0): Parcelable