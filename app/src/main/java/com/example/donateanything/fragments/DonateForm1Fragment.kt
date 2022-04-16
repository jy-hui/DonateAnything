package com.example.donateanything.fragments

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.donateanything.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.fragment_request.*


class DonateForm1Fragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var itemName : EditText
    private lateinit var valueNo: EditText
    private lateinit var addressText: EditText

    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_donateform1, container, false)
        val btnBack: ImageView =view.findViewById(R.id.back3)
        val argsFrom = this.arguments
        val title = argsFrom?.getString("title")
        val itemType = argsFrom?.getString("itemType")
        val icNo = argsFrom?.getString("icNo")
        val date = argsFrom?.getString("date")
        firebaseAuth= FirebaseAuth.getInstance()
        val email = firebaseAuth.currentUser!!.email.toString()


        val itemTypeText= view.findViewById<TextView>(R.id.donateItem)
        itemTypeText.text = itemType
        itemName = view.findViewById(R.id.itemName)
        valueNo = view.findViewById(R.id.valueNo)
        val unitSpinner: Spinner = view.findViewById(R.id.unit_spinner)
        val transSwitch: Switch = view.findViewById(R.id.transportion_s)
        var onTrans : Boolean = false
        addressText = view.findViewById(R.id.address)
        addressText.visibility = View.INVISIBLE
        //var donateID : String = ""
        //var d_ID_F : String = ""
        //var d_ID : Int = 0
        var unitR = ""
        val btnSubmit: Button =view.findViewById(R.id.submitBtn)

        db= FirebaseFirestore.getInstance()

        btnBack.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_donateForm1Fragment_to_newsFragment)
        }

        val unit = arrayListOf("unit","bottle","bag", "kg","g", "ml","l")

        val unit_arrayAdapter = ArrayAdapter(requireActivity().applicationContext,android.R.layout.simple_spinner_dropdown_item,unit)
        unitSpinner.adapter = unit_arrayAdapter
        unitSpinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                unitR = unit_arrayAdapter.getItem(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        transSwitch.setOnCheckedChangeListener { compoundButton, onSwitch ->
            if(onSwitch){
                addressText.visibility = View.VISIBLE
                onTrans = true
                addressText.setText("")
            }else{
                addressText.visibility = View.INVISIBLE
                onTrans = false
                addressText.setText("-")
            }
        }
//        db.collection("DONATION").get().addOnSuccessListener {
//            if(it.isEmpty){
//                donateID = "0"
//            }
//        }
//            .addOnFailureListener { exception ->
//                Log.d(ContentValues.TAG, "get failed with ", exception)
//            }
//        db.collection("DONATION").get().addOnSuccessListener { result ->
//            for (document in result) {
//                donateID = document.id
//
//            }
//        }
//            .addOnFailureListener { exception ->
//                Log.d(ContentValues.TAG, "get failed with ", exception)
//            }
        btnSubmit.setOnClickListener {
            if(isCheck()) {

                //Log.w(TAG, "Donation :"+ donateID )
                //d_ID = donateID.toInt()
                //d_ID++
                //d_ID_F = d_ID.toString()
                val donation = hashMapOf(
                    //"ID" to d_ID,
                    "Email" to email,
                    "NoIC" to icNo,
                    "Date" to date,
                    "Title" to title,
                    "ItemType" to itemType,
                    "Item" to itemName.text.toString(),
                    "Value" to valueNo.text.toString(),
                    "Unit" to unitR,
                    "Transportation" to onTrans.toString(),
                    "Address" to addressText.text.toString()
                )

                val Donation = db.collection("DONATION")
                val query = Donation.add(donation)
                    .addOnSuccessListener { documentReference ->
                        //Donation.document(d_ID_F).set(donation)
                        val id = documentReference.id
                        Donation.document(id).update("ID", id).addOnSuccessListener {
                            //Navigation.findNavController(it).navigate(R.id.action_donateForm1Fragment_to_receiptFragment)
                            val bundle = Bundle()
                            bundle.putString("ID", id)
                            val fragment = ReceiptFragment()
                            fragment.arguments = bundle
                            fragmentManager?.beginTransaction()
                                ?.replace(R.id.container_fragment, fragment)?.commit()
                        }
                    }
                    .addOnFailureListener { e ->
                        //Log.w(TAG, "Error adding document", e)
                        //Toast.makeText(this,error.toString(), Toast.LENGTH_SHORT).show()
                    }

            }
        }


        // Inflate the layout for this fragment
        return view
    }
    private fun isCheck(): Boolean {
        var isFill = true
        if(itemName.text.toString().isEmpty()&&valueNo.text.toString().isEmpty()){
            itemName.error = "Please enter name of item"
            valueNo.error = "Please enter the value of item"
            //addressText.error = "Please enter your address"
            isFill = false
        }
        if(itemName.text.toString().isEmpty()){
            itemName.error = "Please enter name of item"
            isFill = false
        }
        if(valueNo.text.toString().isEmpty()){
            valueNo.error = "Please enter the value of item"
            isFill = false
        }

        return isFill
    }


}