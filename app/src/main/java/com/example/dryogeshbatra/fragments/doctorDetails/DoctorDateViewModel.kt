package com.example.dryogeshbatra.fragments.doctorDetails

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dryogeshbatra.models.AvailableSlots.Hour
import com.example.dryogeshbatra.models.AvailableSlots.HourForSpecificDocDate
import com.example.dryogeshbatra.models.UserData.UserBookingDetailsForDoc
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DoctorDateViewModel : ViewModel() {
    val database =
        Firebase.database("https://dr-batra-e6203-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
    val globalSlotsAvailablity: String = "global_slots"
    val specificDaySlotAvailablity: String = "specific_day_slot_availablity"
    val generalDoctorSlotTimining: String = "general_doctor_slot_timing"

    var date = MutableLiveData<ArrayList<Int>>()

    val listForAdapter = MutableLiveData<ArrayList<Hour>>()

    var generalDoctorSlotList = arrayListOf<Hour>()
    var specificDoctorSlotList = arrayListOf<HourForSpecificDocDate>()
    var slotAvailablityForSpecificDate = arrayListOf<UserBookingDetailsForDoc>()

/*
    var general = false
    var specific = false
    var available = false
*/

    fun updateListForAdapter(){
        Log.i("SpecificDoctorSlotList", specificDoctorSlotList.toString())

        if (specificDoctorSlotList.isNotEmpty()){
            val tempSpecificDoctorSlotList = arrayListOf<Hour>()
            val tempSlotAvailablityForSpecificDate = arrayListOf<Hour>()
            // val tempListForAdapter = arrayListOf<Hour>()
            // Log.i("tempSpecificDoctorSlotList", tempSpecificDoctorSlotList.toString())
            specificDoctorSlotList.forEachIndexed{ index, hourForSpecificDocDate ->
                tempSpecificDoctorSlotList.add(index, Hour(hourForSpecificDocDate.hour, hourForSpecificDocDate.minute))
            }

            if (slotAvailablityForSpecificDate.isNotEmpty()){
                slotAvailablityForSpecificDate.forEachIndexed{index, userBookingDetailsForDoc ->
                    tempSlotAvailablityForSpecificDate.add(index, Hour(userBookingDetailsForDoc.hour, userBookingDetailsForDoc.minute))
                }
            }



            if (tempSlotAvailablityForSpecificDate.isNotEmpty()) {
                /* tempSpecificDoctorSlotList.forEachIndexed { index, hour ->
                     slotAvailablityForSpecificDate.forEachIndexed { valueIndex, valueHour ->
                         if (tempSpecificDoctorSlotList[index].hour == slotAvailablityForSpecificDate[valueIndex].hour && tempSpecificDoctorSlotList[index].minute == slotAvailablityForSpecificDate[valueIndex].minute) {
                             tempListForAdapter.add(tempSpecificDoctorSlotList[valueIndex])
                         }
                     }
                 }*/
                tempSpecificDoctorSlotList.removeAll(tempSlotAvailablityForSpecificDate)
                listForAdapter.postValue(tempSpecificDoctorSlotList)
            } else{
                listForAdapter.postValue(tempSpecificDoctorSlotList)
            }

        } else{
            val tempGeneralDoctorSlot = arrayListOf<Hour>()
            val tempSpecificSlotAvailablityForSpecificDate = arrayListOf<Hour>()
            Log.i("tempGeneralDoctorSlot", tempGeneralDoctorSlot.toString())

            generalDoctorSlotList.forEachIndexed{ index, hourForSpecificDocDate ->
                tempGeneralDoctorSlot.add(index, Hour(hourForSpecificDocDate.hour, hourForSpecificDocDate.minute))
            }

            slotAvailablityForSpecificDate.forEachIndexed{index, userBookingDetailsForDoc ->
                tempSpecificSlotAvailablityForSpecificDate.add(index, Hour(userBookingDetailsForDoc.hour, userBookingDetailsForDoc.minute))
            }


            if (tempSpecificSlotAvailablityForSpecificDate.isNotEmpty()){
                /*tempGeneralDoctorSlot.forEachIndexed{ index , hour ->
                    slotAvailablityForSpecificDate.forEachIndexed{ valueIndex, valueHour ->
                        if (tempGeneralDoctorSlot[index].hour == slotAvailablityForSpecificDate[valueIndex].hour && tempGeneralDoctorSlot[index].minute == slotAvailablityForSpecificDate[valueIndex].minute) {
                            tempListForAdapter.add(tempGeneralDoctorSlot[valueIndex])
                        }
                    }
                }*/
               // Log.i("slotAvailablityForSpecificDate", slotAvailablityForSpecificDate.toString())
                tempGeneralDoctorSlot.removeAll(tempSpecificSlotAvailablityForSpecificDate)
                listForAdapter.postValue(tempGeneralDoctorSlot)
            }else{
                listForAdapter.postValue(tempGeneralDoctorSlot)
            }

        }


        /* if (specificDoctorSlotList.isNotEmpty()){
             val tempListForAdapter = arrayListOf<Hour>()
             var tempSpecificDoctorSlotList: ArrayList<Hour> = arrayListOf()
             specificDoctorSlotList.forEachIndexed { index, hourForSpecificDocDate ->
                 tempSpecificDoctorSlotList[index].hour = hourForSpecificDocDate.hour
                 tempSpecificDoctorSlotList[index].minute = hourForSpecificDocDate.minute
             }
             if (generalDoctorSlotList.isNotEmpty()){
                 if(tempSpecificDoctorSlotList.isNotEmpty()){
                     tempSpecificDoctorSlotList.forEachIndexed{ index, hour ->
                         generalDoctorSlotList.forEachIndexed{ valueIndex, valueHour ->
                             if (tempSpecificDoctorSlotList[index].hour == generalDoctorSlotList[valueIndex].hour && tempSpecificDoctorSlotList[index].minute == generalDoctorSlotList[valueIndex].minute) {
                                 tempListForAdapter.add(generalDoctorSlotList[valueIndex])
                             }
                         }
                     }
                 }
                 if (tempSpecificDoctorSlotList.isNotEmpty()){
                     tempSpecificDoctorSlotList.removeAll(tempListForAdapter)
                     listForAdapter.postValue(tempSpecificDoctorSlotList)
                 }
             }
         } else{
             val tempListForAdapter = arrayListOf<Hour>()
             if (generalDoctorSlotList.isNotEmpty()){
                     generalDoctorSlotList.forEachIndexed{ index, hour ->
                         generalDoctorSlotList.forEachIndexed{ valueIndex, valueHour ->
                             if (generalDoctorSlotList[index].hour == generalDoctorSlotList[valueIndex].hour && generalDoctorSlotList[index].minute == generalDoctorSlotList[valueIndex].minute) {
                                 tempListForAdapter.add(generalDoctorSlotList[valueIndex])
                             }
                         }
                     }
                 if (generalDoctorSlotList.isNotEmpty()){
                     generalDoctorSlotList.removeAll(tempListForAdapter)
                     listForAdapter.postValue(generalDoctorSlotList)
                 }
             }
         }*/
    }



/*
    var listForAdapterRemoval = arrayListOf<Hour>()
    var listForAdapter = arrayListOf<Hour>()
    var generalDoctorSlotList: ArrayList<Hour> = arrayListOf()
    var updateListForSpecificDate = arrayListOf<Hour>()
    var updateListForGeneralDate = arrayListOf<Hour>()
    var bookedSlotForSpecificDate = arrayListOf<Hour>()
    fun bookedSlotForSpecificDate(list: ArrayList<Hour>){
        bookedSlotForSpecificDate = list
    }
    fun updateListForSpecificDate(list: ArrayList<Hour>){
        updateListForSpecificDate = list
    }
    fun updateListForGeneralDate(list: ArrayList<Hour>){
        updateListForGeneralDate = list
    }
    fun getListForAdapter(date: Int, month: Int, year: Int): ArrayList<Hour> {
        *//*database.child(generalDoctorSlotTimining).get().addOnSuccessListener{
            val value = it.getValue<ArrayList<Hour>>()
            if (value != null) {
                generalDoctorSlotList = value
            }
        }*//*
        database.child(globalSlotsAvailablity).child(year.toString()).child(month.toString()).child(date.toString()).child(specificDaySlotAvailablity).get().addOnSuccessListener {
            val value = it.getValue<ArrayList<Hour>>()
            generalDoctorSlotList.clear()
            if (value != null) {
                generalDoctorSlotList.clear()
                generalDoctorSlotList = value
            }
        }
        //Start from here: attach second loop for specificSlotBookedByDoctor
        database.child(globalSlotsAvailablity).child(year.toString()).child(month.toString()).child(date.toString()).get().addOnSuccessListener {
            val value = it.getValue<ArrayList<Hour>>()
            if (value != null){
                listForAdapterRemoval = value
            }
           *//* if (value != null){
                if (!value[0].doctorChosenSlot.isEmpty()){
                    generalDoctorSlotList.clear()
                    value[0].doctorChosenSlot.forEachIndexed{ index, doctorChoosenHour ->
                        generalDoctorSlotList[index].hour = doctorChoosenHour.hour
                        generalDoctorSlotList[index].minute = doctorChoosenHour.minute
                    }
                }
            }*//*
            value?.forEachIndexed{ index, hour ->
                generalDoctorSlotList.forEachIndexed{ valueIndex, valueHour ->
                    if (value[index].hour == generalDoctorSlotList[valueIndex].hour && value[index].minute == generalDoctorSlotList[valueIndex].minute) {
                        listForAdapter.add(generalDoctorSlotList[valueIndex])
                    }
                }
            }
        }
        listForAdapterRemoval.removeAll(listForAdapter)
        return listForAdapterRemoval
    }*/


}