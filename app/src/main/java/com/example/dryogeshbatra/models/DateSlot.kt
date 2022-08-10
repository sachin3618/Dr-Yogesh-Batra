package com.example.dryogeshbatra.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class DateSlot(
    var date: Long = 0L,
    var timing: UserSlots = UserSlots()
): Parcelable
