package com.example.donateanything.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.donateanything.*
import com.example.donateanything.R
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.fragment_admin_donate_list.*

class DonateDetailFragment : Fragment() {


    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_admin_donate_detail, container, false)
        val argsFrom = this.arguments
        val donateID = argsFrom?.getString("ID")
        val backBtn: ImageView = view.findViewById(R.id.backBtn)

        backBtn.setOnClickListener(){
            val fragmentBack = DonateListFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.container_fragment,fragmentBack)?.commit()
        }

        val tvName : TextView = view.findViewById(R.id.tvRName3)
        val tvIC : TextView = view.findViewById(R.id.tvRIC3)
        val tvEmail : TextView = view.findViewById(R.id.tvREmail3)
        val tvPhone : TextView = view.findViewById(R.id.tvRPhone3)
        val tvDate : TextView = view.findViewById(R.id.tvRDate3)
        val tvTitle : TextView = view.findViewById(R.id.tvRTitle3)
        val tvType : TextView = view.findViewById(R.id.tvRType3)
        val tvDetails : TextView = view.findViewById(R.id.tvRDonateDetails3)
        db = FirebaseFirestore.getInstance()
        db.collection("DONATION").document(donateID.toString()).get()
            .addOnSuccessListener { result ->
                //for (document in result) {
                tvIC.setText("No IC : "+result.getString("NoIC"))
                tvEmail.setText("Email : "+result.getString("Email"))
                tvDate.setText("Date : "+result.getString("Date"))
                tvTitle.setText("Title : "+result.getString("Title"))
                tvType.setText("Donate Type : "+result.getString("ItemType"))
                if(result.getString("ItemType")=="Food"){
                    tvDetails.setText("Item : "+
                            result.getString("Item")+" = "+
                            result.getString("Value")+ " "+
                            result.getString("Unit")+ "\nTransportation : "+
                            result.getString("Transportation")+ "\nAddress : "+
                            result.getString("Address")
                    )
                }else if(result.getString("ItemType")=="Daily Supply"){
                    tvDetails.setText("Item : "+
                            result.getString("Item")+" = "+
                            result.getString("Value")+ " "+
                            result.getString("Unit")+ "\nTransportation : "+
                            result.getString("Transportation")+ "\nAddress : "+
                            result.getString("Address")
                    )
                }else if(result.getString("ItemType")=="Money"){
                    tvDetails.setText("Bank : "+
                            result.getString("Bank")+"\nAccount No : "+
                            result.getString("AccountNo")+"\nPayment : RM"+
                            result.getString("Payment"))
                }

                //}
                val emailR =result.getString("Email")
                db.collection("USERS").document(emailR.toString()).get()
                    .addOnSuccessListener { result ->
                        //for (document in result) {
                        tvName.setText("ID : "+donateID.toString()+"\nName :"+result.getString("Username"))
                        tvPhone.setText("Phone :"+ result.getString("Phone"))
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