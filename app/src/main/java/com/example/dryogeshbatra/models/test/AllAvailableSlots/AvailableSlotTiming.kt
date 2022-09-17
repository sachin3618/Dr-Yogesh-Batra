package com.example.dryogeshbatra.models.test.AllAvailableSlots

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AvailableSlotTiming(
    var hour: Int = 0,
    var min: Int = 0
): Parcelable
