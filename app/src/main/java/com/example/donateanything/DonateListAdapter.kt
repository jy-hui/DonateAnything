package com.example.donateanything

import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*

class DonateListAdapter (private val donate_list: ArrayList<DonateList>, private val listener: OnItemClickListener):


    RecyclerView.Adapter<DonateListAdapter.viewHolder>() {

    inner class viewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
        private lateinit var db: FirebaseFirestore

        var Date: TextView = view.findViewById(R.id.date)
        var Name: TextView = view.findViewById(R.id.name)
        var Title: TextView = view.findViewById(R.id.title)
        var ItemType: TextView = view.findViewById(R.id.itemType)
        var Detail: TextView = view.findViewById(R.id.donateDetail)



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
            .inflate(R.layout.donate_list,parent,false)

        return viewHolder(view)
    }
    private lateinit var db : FirebaseFirestore
    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentRec = donate_list[position]
        holder.Date.text = currentRec.Date
        db = FirebaseFirestore.getInstance()
        db.collection("USERS").document(currentRec.Email.toString()).get()
            .addOnSuccessListener { result ->
                holder.Name.text = result.getString("Username")
            }

        holder.Title.text = currentRec.Title
        holder.ItemType.text = currentRec.ItemType

        if(currentRec.Value != null){
            holder.Detail.text = currentRec.Value +" "+ currentRec.Unit + " x " + currentRec.Item
        }else if(currentRec.Payment != null){
            holder.Detail.text = "RM "+ currentRec.Payment
        }else{
            holder.Detail.text = ""
        }


    }

    override fun getItemCount(): Int {
        return donate_list.size
    }
}