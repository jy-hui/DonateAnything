package com.example.donateanything.fragments


import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.example.donateanything.R
import com.google.firebase.firestore.*
import org.w3c.dom.Text


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
        val btnApprove : Button = view.findViewById(R.id.approveBtn)
        val pointGain : EditText = view.findViewById(R.id.pointGainTxt)
        var email = ""
        var point = 0
        btnApprove.visibility = View.GONE
        pointGain.visibility = View.GONE

        db = FirebaseFirestore.getInstance()
        db.collection("DONATION").document(donateID.toString()).get()
            .addOnSuccessListener { result ->
                //for (document in result) {
                tvIC.setText("No IC : "+result.getString("NoIC"))
                tvEmail.setText("Email : "+result.getString("Email"))
                email = result.getString("Email").toString()
                tvDate.setText("Date : "+result.getString("Date")+"\n")
                tvTitle.setText("Title : "+result.getString("Title")+"\n")
                tvType.setText("Donate Type: "+result.getString("ItemType"))
                if(result.getString("ItemType")=="Food"){
                    tvDetails.setText("Item : "+
                            result.getString("Item")+" = "+
                            result.getString("Value")+ " "+
                            result.getString("Unit")+ "\nTransportation : "+
                            result.getString("Transportation")+ "\nAddress : "+
                            result.getString("Address")+"\n\nStatus : "+
                            result.getString("Status")+"\nPoint Gain: "+
                            result.getString("Point")
                    )
                }else if(result.getString("ItemType")=="Daily Supply"){
                    tvDetails.setText("Item : "+
                            result.getString("Item")+" = "+
                            result.getString("Value")+ " "+
                            result.getString("Unit")+ "\nTransportation : "+
                            result.getString("Transportation")+ "\nAddress : "+
                            result.getString("Address")+"\n\nStatus : "+
                            result.getString("Status")+"\nPoint Gain: "+
                            result.getString("Point")
                    )
                }else if(result.getString("ItemType")=="Money"){
                    tvDetails.setText("Bank : "+
                            result.getString("Bank")+"\nAccount No : "+
                            result.getString("AccountNo")+"\nPayment : RM"+
                            result.getString("Payment")+"\n\nStatus : "+
                            result.getString("Status")+"\nPoint Gain: "+
                            result.getString("Point"))
                }

                //}
                val emailR =result.getString("Email")

                db.collection("USERS").document(emailR.toString()).get()
                    .addOnSuccessListener { task ->
                        //for (document in result) {
                        tvName.setText("ID : "+donateID.toString()+"\n\nName :"+task.getString("Username"))
                        tvPhone.setText("Phone :"+ task.getString("Phone"))

                        point = task.getString("Point").toString().toInt()

                        Log.d(ContentValues.TAG, "Points: "+point)
                        //}
                    }
                    .addOnFailureListener { exception ->
                        Log.d(ContentValues.TAG, "get failed with ", exception)
                    }

                if(result.getString("Status").equals("approve")) {
                    btnApprove.visibility = View.GONE
                    pointGain.visibility = View.GONE
                }else{
                    btnApprove.visibility = View.VISIBLE
                    pointGain.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }
        val donateFormDb = db.collection("DONATION")
        val usersDb = db.collection("USERS")
        btnApprove.setOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setCancelable(true)
            builder.setTitle("Approve")
            builder.setMessage("Confirm approve ?")
            builder.setPositiveButton("Confirm",
                DialogInterface.OnClickListener { dialog, which ->
                    donateFormDb.document(donateID.toString()).update("Status", "approve")
                        .addOnSuccessListener {
                            Toast.makeText(requireActivity().applicationContext, "This request is Approved", Toast.LENGTH_SHORT).show()
                            point += pointGain.text.toString().toInt()
                            donateFormDb.document(donateID.toString()).update("Point", pointGain.text.toString())
                                .addOnSuccessListener {
                                    usersDb.document(email).update("Point", point.toString())
                                        .addOnSuccessListener {
                                            val fragmentBack = DonateListFragment()
                                            fragmentManager?.beginTransaction()?.replace(R.id.container_fragment, fragmentBack)?.commit()
                                        }.addOnFailureListener { e ->
                                            Toast.makeText(requireActivity().applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
                                        }
                                }.addOnFailureListener { e ->
                                    Toast.makeText(requireActivity().applicationContext,e.toString(), Toast.LENGTH_SHORT).show()
                                }
                        }.addOnFailureListener { e ->
                            Toast.makeText(requireActivity().applicationContext,e.toString(), Toast.LENGTH_SHORT).show()
                        }

                })
            builder.setNegativeButton(android.R.string.cancel,
                DialogInterface.OnClickListener { dialog, which -> })

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        return view
    }

}