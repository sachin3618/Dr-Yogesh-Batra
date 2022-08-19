package com.example.dryogeshbatra.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class DateSlot(
    var date: Int = 0,
    var month: Int = 0,
    var year: Int = 0,
    var timing: UserSlots = UserSlots()
): Parcelable
