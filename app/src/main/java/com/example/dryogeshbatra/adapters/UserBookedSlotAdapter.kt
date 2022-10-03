package com.example.dryogeshbatra.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dryogeshbatra.R
import com.example.dryogeshbatra.models.UserData.UserBookingDetails
import kotlinx.android.synthetic.main.user_booked_slot_list.view.*

class UserBookedSlotAdapter(
    private val context: Context,
    val onClickListener: OnClickListener,
    val videoCallClicked: OnVideoCallClicked
) : RecyclerView.Adapter<UserBookedSlotAdapter.MyViewHolder>() {
    private var list: ArrayList<UserBookingDetails> = arrayListOf()

    fun updateList(list: ArrayList<UserBookingDetails>){
        this.list.clear()
        this.list.addAll(list)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.user_booked_slot_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Log.i("userbookedSlotAdapter", list[position].toString())
        fun paymentStatus(): String {
            if (list[position].paymentStatus) {
                return "Confirmed"
            } else {
                return "Pending"
            }
        }
        fun getMinute() : String{
            if (list[position].appointmentMinute == 0){
                return "00 PM"
            }else if (list[position].appointmentMinute == 30){
                return "30 PM"
            }else return ""
        }

        holder.itemView.tv_name.text = list[position].firstName + " " + list[position].lastName
        holder.itemView.tv_gender.text = list[position].gender
        holder.itemView.tv_appointment_date.text =
            list[position].appointmentDay.toString() + "/" + list[position].appointmentMonth.toString() + "/" + list[position].appointmentYear.toString() + ", " + list[position].appointmentHour.toString() + ":" + getMinute()
        holder.itemView.tv_appointment_type.text =
            "Appointment Type: " + list[position].appointmentType
        holder.itemView.tv_appointment_mode.text =
            "Appointment Mode: " + list[position].appointmentMode
        holder.itemView.tv_payment_status.text = "Payment Status: ${paymentStatus()}"

        holder.itemView.ib_video_call_clicked.setOnClickListener {
            videoCallClicked.videoCallClicked(position)
        }
        onClickListener.onClick(position)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }

    interface OnVideoCallClicked{
        fun videoCallClicked(position: Int)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

}


