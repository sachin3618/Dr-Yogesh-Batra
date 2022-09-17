package com.example.dryogeshbatra.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dryogeshbatra.R
import com.example.dryogeshbatra.models.AvailableSlots.Hour
import kotlinx.android.synthetic.main.appointment_time_list.view.*


class TimeSlotAdapter(
    private val context: Context,
    val onClickListener: OnClickListener,
) : RecyclerView.Adapter<TimeSlotAdapter.MyViewHolder>() {
    var list: ArrayList<Hour> = arrayListOf()

    fun updateList(list: ArrayList<Hour>){
        this.list.clear();
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }

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
      //  val hours = getHours(list, year, month, date)
      //  Log.i("hours", hours.toString())
        if(list[position].minute == 0){
            holder.itemView.tv_time.text = "${list[position].hour}:${list[position].minute}" + "0" + " " + "PM"
        }else{
            holder.itemView.tv_time.text = "${list[position].hour}:${list[position].minute}" + " " + "PM"
        }

        holder.itemView.setOnClickListener {
            fun View.getBackgroundColor() = (background as? ColorDrawable?)?.color ?: Color.TRANSPARENT
            val color = holder.itemView.getBackgroundColor()

            if (color == Color.BLUE){
                holder.itemView.setBackgroundColor(Color.TRANSPARENT)
                onClickListener.onClick(position)
            } else{
                holder.itemView.setBackgroundColor(Color.BLUE)
                onClickListener.onClick(position)
            }
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
        return list.size
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }

    /*fun getHours(list: ArrayList<Year>, year: Int, month: Int, date: Int): ArrayList<Hour> {
        var hours: java.util.ArrayList<Hour> = arrayListOf()
        for (i in list) {
            if (i.year == year) {

                for (k in i.month) {
                    if (k.month == month) {
                        for (l in k.date) {
                            if (l.date == date) {
                                hours = l.time

                                *//*if (!l.time[position].isBooked){
                                   // hours = l.time[position].
                                    hours = l.time
                                    holder.itemView.tv_time.text = "${l.time[position].hour}:${l.time[position].minute}"


                                }*//*
                            }
                        }
                    }
                }
            }
        }
        return hours
    }*/
}






