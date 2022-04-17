package com.example.donateanything

import android.graphics.Color.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_view.*

class RequestAdapter(private val infoList: ArrayList<RequestList>,
                     private val listener: HistoryActivity
):


    RecyclerView.Adapter<RequestAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{
        var donateDS: TextView = view.findViewById(R.id.donateDS)
        var donateFD: TextView = view.findViewById(R.id.donateFD)
        var donateMoney: TextView = view.findViewById(R.id.donateMoney)
        var donateO: TextView = view.findViewById(R.id.donateO)
        var reason_detail: TextView = view.findViewById(R.id.reason_detail)
        var request_status: TextView = view.findViewById(R.id.request_status)


        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.itemClick(position)
            }
        }
    }

    interface OnItemClickListener{
        fun itemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.request_user_list, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRec = infoList[position]

        holder.donateDS.text = currentRec.DailySupply
        holder.donateFD.text = currentRec.FoodDrink
        holder.donateMoney.text = currentRec.Money
        holder.donateO.text = currentRec.Other
        holder.request_status.text =  "[" + currentRec.Status + "]"
        holder.reason_detail.text = currentRec.Reason

    }

    override fun getItemCount(): Int {
        return infoList.size
    }

}