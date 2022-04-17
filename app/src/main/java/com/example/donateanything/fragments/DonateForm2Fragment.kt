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
import java.text.SimpleDateFormat
import java.util.*


class DonateForm2Fragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var bankR : String
    private lateinit var accountNo : EditText
    private lateinit var payment : EditText
    private lateinit var pacNo : EditText

    lateinit var calendar:Calendar
    lateinit var simpleDateFormat: SimpleDateFormat


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_donateform2, container, false)
        val btnBack: ImageView =view.findViewById(R.id.back4)
        val argsFrom = this.arguments
        val title = argsFrom?.getString("title")
        val itemType = argsFrom?.getString("itemType")
        val icNo = argsFrom?.getString("icNo")
        val date = argsFrom?.getString("date")
        firebaseAuth= FirebaseAuth.getInstance()
        val email = firebaseAuth.currentUser!!.email.toString()

        //var donateID : String = ""
        //var d_ID_F : String = ""
        //var d_ID : Int = 0
        val titleText= view.findViewById<TextView>(R.id.donateItem);
        titleText.setText(itemType)
        val bankSpinner : Spinner = view.findViewById(R.id.bank_spinner)
        bankR = ""
        accountNo = view.findViewById(R.id.accountNo)
        payment = view.findViewById(R.id.payment)
        pacNo = view.findViewById(R.id.pacNo)
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
            Toast.makeText(requireActivity().applicationContext, "PAC No : 12345",Toast.LENGTH_SHORT).show()
        }


        val btnSubmit: Button =view.findViewById(R.id.submitBtn)
        db= FirebaseFirestore.getInstance()

        btnSubmit.setOnClickListener(){

            if(isCheck()){
                calendar = Calendar.getInstance()
                simpleDateFormat = SimpleDateFormat("yyyyMMddHHmmssSSS")
                val id = simpleDateFormat.format(calendar.time)
                val donation = hashMapOf(
                    "ID" to id,
                    "Email" to email,
                    "NoIC" to icNo,
                    "Date" to date,
                    "Title" to title,
                    "ItemType" to itemType,
                    "AccountNo" to accountNo.text.toString(),
                    "Bank" to bankR,
                    "Payment" to payment.text.toString(),
                    "Point" to "pending",
                    "Status" to "pending"
                )

                val Donation = db.collection("DONATION")
                val query = Donation.whereEqualTo("ID",id).get().addOnSuccessListener { result ->
                    if(result.isEmpty) {
                        Donation.document(id).set(donation)
                        val bundle = Bundle()
                        bundle.putString("ID", id)
                        val fragment = ReceiptFragment()
                        fragment.arguments = bundle
                        fragmentManager?.beginTransaction()
                            ?.replace(R.id.container_fragment, fragment)?.commit()
                    }
                }.addOnFailureListener { e ->
                    //Log.w(TAG, "Error adding document", e)
                    //Toast.makeText(this,error.toString(), Toast.LENGTH_SHORT).show()
                }

            }





        }

        return view
    }
//    private fun getShareIntent() : Intent {
//        val shareIntent = Intent(Intent.ACTION_SENDTO)
//        shareIntent.setType("text/plain")
//            .putExtra(Intent.EXTRA_TEXT, "Pac No: 12345")
//        return shareIntent
//    }
    private fun isCheck(): Boolean {
        var isFill = true
        if(bankR.equals("Bank")&&accountNo.text.toString().isEmpty()&&payment.text.toString().isEmpty()&&pacNo.text.toString().isEmpty()){
            Toast.makeText(requireActivity().applicationContext,"Please choose one of Bank",Toast.LENGTH_SHORT).show()
            accountNo.error = "Please enter your account number "
            payment.error = "Please enter your payment"
            pacNo.error = "Please enter pac number from the request button"
            isFill = false
        }
        if(bankR.equals("Bank")){
            Toast.makeText(requireActivity().applicationContext,"Please choose one of Bank",Toast.LENGTH_SHORT).show()
            isFill = false
        }
        if(accountNo.equals("Bank")){
            accountNo.error = "Please enter your account number "
            isFill = false
        }


        if(payment.text.toString().isEmpty()){
            payment.error = "Please enter your payment"
            isFill = false
        }

        if(pacNo.text.toString().isEmpty()){
            pacNo.error = "Please enter pac number from the request button"
            isFill = false
        }else if(!pacNo.text.toString().toInt().equals(12345)){
            pacNo.error = "Pac No Invalid"
            isFill = false
        }

        return isFill
    }

}