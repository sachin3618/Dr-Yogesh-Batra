package com.example.dryogeshbatra.models.AvailableSlots

import android.os.Parcelable
import com.example.dryogeshbatra.models.test.AllAvailableSlots.AvailableSlotTiming
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hour(
    var hour : Int = 0,
    var minute: Int = 0,
    var isBooked: Boolean =  false,
    var user_id: String = "",
    var appointment_id: String = "",
) : Parcelable
