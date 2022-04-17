package com.example.donateanything

import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.donateanything.fragments.RequestListFragment

class Adapter(private val request_list: ArrayList<RequestList>, private val listener: OnItemClickListener):
    RecyclerView.Adapter<Adapter.viewHolder>() {

    inner class viewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
        var FirstName: TextView = view.findViewById(R.id.firstNameInput)
        var LastName: TextView = view.findViewById(R.id.lastNameInput)
        var Specific: TextView = view.findViewById(R.id.requestSpecific)
        var Reason: TextView = view.findViewById(R.id.reasonRequest)
        var DailySupply: TextView = view.findViewById(R.id.supplyInput)
        var FoodDrink: TextView = view.findViewById(R.id.foodInput)
        var Money: TextView = view.findViewById(R.id.moneyInput)
        var Other: TextView = view.findViewById(R.id.otherInput)

        init{
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.itemClick(position)
            }
        }
    }

    interface OnItemClickListener{
        fun itemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.request_list,parent,false)

        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentRec = request_list[position]

        holder.FirstName.text = currentRec.FirstName
        holder.LastName.text = currentRec.LastName
        holder.Specific.text = currentRec.Specific
        holder.Reason.text = currentRec.Reason
        holder.DailySupply.text = currentRec.DailySupply
        holder.FoodDrink.text = currentRec.FoodDrink
        holder.Money.text = currentRec.Money
        holder.Other.text = currentRec.Other

    }

    override fun getItemCount(): Int {
        return request_list.size
    }
}