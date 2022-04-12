package com.example.donateanything.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.donateanything.R
import com.google.firebase.firestore.FirebaseFirestore

class ReceiptFragment : Fragment(){
    private lateinit var db : FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_receipt, container, false)
        val tvName : TextView = view.findViewById(R.id.tvRName)
        val tvIC : TextView = view.findViewById(R.id.tvRIC)
        val tvEmail : TextView = view.findViewById(R.id.tvREmail)
        val tvPhone : TextView = view.findViewById(R.id.tvRPhone)
        val tvAddress : TextView = view.findViewById(R.id.tvRAddress)
        val tvDate : TextView = view.findViewById(R.id.tvRDate)
        //val tvIC : TextView = view.findViewById(R.id.tvRName)
        //val tvIC : TextView = view.findViewById(R.id.tvRName)

        return view
    }
}