package com.example.dryogeshbatra.models.AvailableSlots

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hour(
    var hour : Int = 0,
    var isBooked: Boolean =  false,
    var userId: String = "",
    var minute: Int = 0
) : Parcelable
