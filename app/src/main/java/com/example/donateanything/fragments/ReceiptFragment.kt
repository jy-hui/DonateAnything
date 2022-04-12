package com.example.donateanything.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
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
        val argsFrom = this.arguments
        val donateID = argsFrom?.getString("ID")


        val tvName : TextView = view.findViewById(R.id.tvRName)
        val tvIC : TextView = view.findViewById(R.id.tvRIC)
        val tvEmail : TextView = view.findViewById(R.id.tvREmail)
        val tvPhone : TextView = view.findViewById(R.id.tvRPhone)
        val tvDate : TextView = view.findViewById(R.id.tvRDate)
        val tvAddress : TextView = view.findViewById(R.id.tvRAddress)
        val tvType : TextView = view.findViewById(R.id.tvRType)
        val tvDetails : TextView = view.findViewById(R.id.tvRDonateDetails)
        val tvTotal : TextView = view.findViewById(R.id.tvRTotal)

        db = FirebaseFirestore.getInstance()

        Log.d(ContentValues.TAG, "ID1 : "+ donateID.toString())
        db.collection("DONATION").document(donateID.toString()).get()
            .addOnSuccessListener { result ->
                //for (document in result) {
                    Log.d(ContentValues.TAG, "ID : "+ donateID)
                    tvIC.setText("No IC : "+result.getString("NoIC"))
                    tvEmail.setText("Email : "+result.getString("Email"))
                    tvDate.setText("Date : "+result.getString("Date"))
                    tvType.setText("Donate Type : "+result.getString("Item Type"))
                if(result.getString("Item Type")=="Food"){
                    tvDetails.setText("Item : "+
                            result.getString("Item")+" = "+
                            result.getString("Value")+ " "+
                            result.getString("Unit")+ "\nTransportation : "+
                            result.getString("Transportation")+ "\nAddress : "+
                            result.getString("Address")
                    )
                }else if(result.getString("Item Type")=="Supply"){
                    tvDetails.setText("Item : "+
                            result.getString("Item")+" = "+
                            result.getString("Value")+ " "+
                            result.getString("Unit")+ "\nTransportation : "+
                            result.getString("Transportation")+ "\nAddress : "+
                            result.getString("Address")
                    )
                }else if(result.getString("Item Type")=="Money"){
                    tvDetails.setText("Bank : "+
                            result.getString("Bank")+"\nAccount No : "+
                            result.getString("AccountNo")+"\nPayment No : "+
                            result.getString("Payment"))
                }

                //}
                val emailR =result.getString("Email")
                db.collection("USERS").document(emailR.toString()).get()
                    .addOnSuccessListener { result ->
                        //for (document in result) {
                        tvName.setText(result.getString("Username"))
                        tvPhone.setText(result.getString("Phone"))
                        //}
                    }
                    .addOnFailureListener { exception ->
                        Log.d(ContentValues.TAG, "get failed with ", exception)
                    }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }

        return view
    }
}