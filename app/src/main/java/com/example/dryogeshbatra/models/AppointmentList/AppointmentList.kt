package com.example.dryogeshbatra.models.AppointmentList

import android.os.Parcelable
import com.example.dryogeshbatra.models.AvailableSlots.Year
import com.example.dryogeshbatra.models.UserDataForDoctor.UserDataForDoc
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AppointmentList(
    var subDateAppointmentListar: ArrayList<Year> = arrayListOf(),
    var userData: UserDataForDoc = UserDataForDoc()
) : Parcelable
