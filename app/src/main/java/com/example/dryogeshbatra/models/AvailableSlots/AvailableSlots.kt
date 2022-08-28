package com.example.dryogeshbatra.models.AvailableSlots

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AvailableSlots(
    var year: ArrayList<Year> = arrayListOf()
) : Parcelable
