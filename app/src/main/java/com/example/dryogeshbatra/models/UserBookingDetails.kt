package com.example.dryogeshbatra.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Time
import java.util.*

@Parcelize
data class UserBookingDetails(
    var firstName : String = "",
    var lastName: String = "",
    var userNumber: Int = -1,
    var userId: String = "",
    var userBooking : DateSlot = DateSlot(),
    var paymentStatus : Boolean = false,
    var paymentTransId: String = "",
    var phoneNumber: String = ""
) : Parcelable
