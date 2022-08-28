package com.example.dryogeshbatra.adapters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dryogeshbatra.R
import com.example.dryogeshbatra.fragments.doctorDetails.DoctorDateFragment
import com.example.dryogeshbatra.models.AvailableSlots.AvailableSlots
import com.example.dryogeshbatra.models.AvailableSlots.Date
import com.example.dryogeshbatra.models.AvailableSlots.Hour
import com.example.dryogeshbatra.models.AvailableSlots.Year
import kotlinx.android.synthetic.main.appointment_time_list.view.*

class TimeSlotAdapter(
    private val context: Context,
    val onClickListener: OnClickListener,
    private val list: ArrayList<Year>,
    private val year: Int,
    private val month: Int,
    private val date: Int,
) : RecyclerView.Adapter<TimeSlotAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.appointment_time_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hours = getHours(list, year, month, date)
        Log.i("hours", hours.toString())
        holder.itemView.tv_time.text = "${hours[position].hour}:${hours[position].minute}"

        holder.itemView.setOnClickListener {
            holder.itemView.setBackgroundColor(Color.BLUE)
            onClickListener.onClick(position)

        }
    }


    /* holder.itemView.tv_time.text = "${model.time}:${model.min}"

     if(!model.isBooked){
         holder.itemView.setOnClickListener{
             holder.itemView.setBackgroundColor(Color.BLUE)
             onClickListener.onClick(position)

         }
     }*/

    override fun getItemCount(): Int {
        for (i in list) {
            if (i.year == year) {

                for (k in i.month) {
                    if (k.month == month) {
                        for (l in k.date) {
                            if (l.date == date) {
                               return l.time.size
                            }
                        }
                    }
                }
            }
        }
        return 0
    }

    interface OnClickListener {

        fun onClick(position: Int)
    }

    fun getHours(list: ArrayList<Year>, year: Int, month: Int, date: Int): ArrayList<Hour> {
        var hours: java.util.ArrayList<Hour> = arrayListOf()
        for (i in list) {
            if (i.year == year) {

                for (k in i.month) {
                    if (k.month == month) {
                        for (l in k.date) {
                            if (l.date == date) {
                                hours = l.time

                                /*if (!l.time[position].isBooked){
                                   // hours = l.time[position].
                                    hours = l.time
                                    holder.itemView.tv_time.text = "${l.time[position].hour}:${l.time[position].minute}"


                                }*/
                            }
                        }
                    }
                }
            }
        }
        return hours
    }
}






