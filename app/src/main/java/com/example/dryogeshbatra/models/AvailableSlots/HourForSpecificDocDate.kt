package com.example.dryogeshbatra.models.AvailableSlots

import android.os.Parcelable
import com.example.dryogeshbatra.models.test.AllAvailableSlots.AvailableSlotTiming
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HourForSpecificDocDate(
    var hour : Int = 0,
    var minute: Int = 0,
) : Parcelable