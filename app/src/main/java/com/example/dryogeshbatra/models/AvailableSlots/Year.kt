package com.example.dryogeshbatra.models.AvailableSlots

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Year(
   var year : Int = 0,
   var month: ArrayList<com.example.dryogeshbatra.models.AvailableSlots.Month> = arrayListOf()
): Parcelable
