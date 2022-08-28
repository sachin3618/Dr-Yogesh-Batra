package com.example.dryogeshbatra.models.AvailableSlots

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Date(
    var date: Int = 0,
    var time: ArrayList<Hour> = arrayListOf()
): Parcelable
