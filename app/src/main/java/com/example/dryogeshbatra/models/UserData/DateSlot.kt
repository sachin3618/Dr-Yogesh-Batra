package com.example.dryogeshbatra.models.UserData

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DateSlot(
    var date: Int = 0,
    var month: Int = 0,
    var year: Int = 0,
    var timing: UserSlots = UserSlots()
): Parcelable
