package com.example.dryogeshbatra.models.UserData

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Time

@Parcelize
data class UserSlots(
    var isBooked: Boolean = false,
    var bookingType: String = "",
    var bookingMode: String = "",
    var time: Long = 0L
): Parcelable
