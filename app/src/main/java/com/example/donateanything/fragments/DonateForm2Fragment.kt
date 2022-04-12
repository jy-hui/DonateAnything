package com.example.donateanything.fragments

import android.app.PendingIntent
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import com.example.donateanything.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class DonateForm2Fragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_donateform2, container, false)
        val btnBack: ImageView =view.findViewById(R.id.back4)
        val argsFrom = this.arguments
        val title = argsFrom?.getString("itemType")
        val icNo = argsFrom?.getString("icNo")
        val date = argsFrom?.getString("date")

        var donateID : String = ""
        var d_ID_F : String = ""
        var d_ID : Int = 0
        val titleText= view.findViewById<TextView>(R.id.donateItem);
        titleText.setText(title)
        val bankSpinner : Spinner = view.findViewById(R.id.bank_spinner)
        var bankR = ""
        val accountNo : EditText = view.findViewById(R.id.accountNo)
        val payment : EditText = view.findViewById(R.id.payment)
        val pacNo : EditText = view.findViewById(R.id.pacNo)
        val requestBtn : Button = view.findViewById(R.id.requestBtn)
        btnBack.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_donateForm2Fragment_to_newsFragment)
        }

        val bank = arrayListOf("Bank", "May Bank", "HSBC Bank","Public Bank")
        val bank_arrayAdapter = ArrayAdapter(requireActivity().applicationContext,android.R.layout.simple_spinner_dropdown_item,bank)
        bankSpinner.adapter = bank_arrayAdapter
        bankSpinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                bankR = bank_arrayAdapter.getItem(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        requestBtn.setOnClickListener() {
            //com.example.donateanything.fragments.init(System.getenv("Acount"),System.getenv("Acount_Token"))
        }


        val btnSubmit: Button =view.findViewById(R.id.submitBtn)
        db= FirebaseFirestore.getInstance()
        db.collection("DONATION").get().addOnSuccessListener {
            if(it.isEmpty){
                donateID = "0"
            }
        }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }
        db.collection("DONATION").get().addOnSuccessListener { result ->
            for (document in result) {
                donateID = document.id

            }
        }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }
        btnSubmit.setOnClickListener(){
            var pacPassword : Int = 12345
            pacPassword = pacNo.text.toString().toInt()
            if(pacPassword == 12345){
                Log.w(ContentValues.TAG, "Donation :"+ donateID )
                d_ID = donateID.toInt()
                d_ID++
                d_ID_F = d_ID.toString()
                val donation = hashMapOf(
                    "ID" to d_ID,
                    "Email" to bankR,
                    "NoIC" to icNo,
                    "Date" to date,
                    "AccountNo" to accountNo.text.toString(),
                    "Payment" to payment.text.toString()
                )

                val Donation = db.collection("DONATION")
                val query = Donation.get()
                    .addOnSuccessListener { documentReference ->
                        Donation.document(d_ID_F).set(donation)
                        Navigation.findNavController(it).navigate(R.id.action_donateForm2Fragment_to_receiptFragment)
                    }
                    .addOnFailureListener { e ->

                    }
            }else{
                Toast.makeText(requireActivity().applicationContext,"Pac No Invalid",Toast.LENGTH_SHORT).show()
            }





        }

        return view
    }
    private fun getShareIntent() : Intent {
        val shareIntent = Intent(Intent.ACTION_SENDTO)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, "Pac No: 12345")
        return shareIntent
    }

}