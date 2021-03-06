package com.example.donateanything

import android.content.Context
import android.content.Intent
import android.graphics.Color.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_view.*
import kotlinx.coroutines.processNextEventInCurrentThread

class MyAdapter(private val infoList: ArrayList<HistoryList>,
                private val listener: OnItemClickListener
):


    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{
        var tvDate: TextView = view.findViewById(R.id.tvDate)
        var tvStatus: TextView = view.findViewById(R.id.tvStatus)
        var tvDType: TextView = view.findViewById(R.id.tvDonateType)
        var tvDDetails: TextView = view.findViewById(R.id.tvDonateDetails)
        var tvPGain: TextView = view.findViewById(R.id.tvPointsGain)
        var btnCheck: Button = view.findViewById(R.id.btnNext)

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
            .inflate(R.layout.item_view, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRec = infoList[position]
        var details =""
        var gainPoints =""

        holder.tvDate.text = currentRec.Date
        holder.tvStatus.text = currentRec.status
        holder.tvDType.text = currentRec.ItemType
        if(currentRec.Item != null || currentRec.Value != null){
            details = currentRec.Value + " x " + currentRec.Item
        }
        if(currentRec.Bank != null || currentRec.Payment != null){
            details = currentRec.Bank + " : RM " + currentRec.Payment
        }
        holder.tvDDetails.text = details
        gainPoints = currentRec.Point + " points gain"
        holder.tvPGain.text = gainPoints
        holder.btnCheck.text = currentRec.btnCheck

        if(currentRec.btnCheck == "View"){
            holder.btnCheck.setBackgroundColor(BLUE)
        }
        else{
            holder.btnCheck.setBackgroundColor(MAGENTA)
        }
    }

    override fun getItemCount(): Int {
        return infoList.size
    }

}