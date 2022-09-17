package com.example.dryogeshbatra.realtimeDatabase

import com.example.dryogeshbatra.models.UserData.UserBookingDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
private const val globalSlotsAvailablity: String = "global_slots"
object FirebaseDatabase {
    val database = Firebase.database("https://dr-batra-e6203-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

   // val myRef = database.getReference("motu")

    fun getAppointmentDate(myRef : String){

    }

    fun uploadAppointmentDetails(UserId: String, userBookingDetails: UserBookingDetails){
        /* val userSlot = UserSlots(false, "normal", "online")
         val DateSlot  = DateSlot(userSlot)*/
        /* data class User(val username: String? = null, val email: String? = null) {
             // Null default values create a no-argument default constructor, which is needed
             // for deserialization from a DataSnapshot.
         }*/




        database.child("users").child(UserId).setValue(userBookingDetails)
    }





}