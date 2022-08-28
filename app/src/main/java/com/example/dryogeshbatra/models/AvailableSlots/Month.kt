package com.example.dryogeshbatra.models.AvailableSlots

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Month(
    var month : Int = 0,
    var date: ArrayList<Date> = arrayListOf()
): Parcelable
