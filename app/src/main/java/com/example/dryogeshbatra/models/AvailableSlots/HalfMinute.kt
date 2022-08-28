package com.example.dryogeshbatra.models.AvailableSlots

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HalfMinute(
    var isBooked: Boolean = false,
    var halfMinute: ArrayList<Int> = arrayListOf()
    ): Parcelable
