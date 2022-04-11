package com.example.donateanything.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.donateanything.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_request.*


class RequestFragment : Fragment() {
//    private lateinit var firstName: EditText
//    private lateinit var lastName: EditText
//    private lateinit var icNo: EditText
//    private lateinit var phoneNo: EditText
//    private lateinit var houseAddress: EditText
//    private lateinit var email: EditText
//    private lateinit var reason: EditText
    //  private lateinit var db: FirebaseFirestore

    private val PHONE_REGEX = "^(01)[0-9]-[0-9]{7,8}\$"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        firstName = findViewById(R.id.firstName)
//        lastName= findViewById(R.id.lastName)
//        icNo= findViewById(R.id.ICno)
//        phoneNo=findViewById(R.id.mobileNo)
//        houseAddress=findViewById(R.id.houseAddress)
//        email=findViewById(R.id.email)
//        reason=findViewById(R.id.reason)
    }

    private fun detailAllFill():Boolean{
        var isFill = true
        //if(firstName.text.toString().isEmpty()&&lastName.text.toString().isEmpty()&&icNo.text.toString().isEmpty()&&)
        if (firstName.text.toString().isEmpty()){
            firstName.error="Please enter your email!"
            firstName.requestFocus()
            isFill=false
        }
        if (lastName.text.toString().isEmpty()){
            lastName.error="Please enter your email!"
            lastName.requestFocus()
            isFill=false

        }
//        if (icNo.text.toString().isEmpty()){
//            icNo.error="Please enter your email!"
//            icNo.requestFocus()
//            isFill=false
//        }
//        if (phoneNo.text.toString().isEmpty()){
//            phoneNo.error="Please enter your email!"
//            phoneNo.requestFocus()
//            isFill=false
//        }else  if(!PHONE_REGEX.toRegex().matches(phoneNo?.text.toString())){
//            phoneNo.error = "Invalid phone number! format: 011-11111111"
//            isFill = false
//        }
        if (houseAddress.text.toString().isEmpty()){
            houseAddress.error="Please enter your email!"
            houseAddress.requestFocus()
            isFill=false
        }
        if (email.text.toString().isEmpty()){
            email.error="Please enter your email!"
            email.requestFocus()
            isFill=false
        }
        if(!(android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches())){
            email.error="Invalid email"
            email.requestFocus()
            isFill=false
        }
        if (reason.text.toString().isEmpty()){
            reason.error="Please enter your email!"
            reason.requestFocus()
            isFill=false
        }
        return isFill
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_request, container, false)
        val backBtn: ImageView = view.findViewById(R.id.backBtn1)
        val nextBtn: Button =view.findViewById(R.id.nextBtn)
        val adminBtn: Button =view.findViewById(R.id.adminBtn)
        //db= FirebaseFirestore.getInstance()

        val firstName=view.findViewById<TextView>(R.id.firstName)
        val lastName=view.findViewById<TextView>(R.id.lastName)
        val icNo=view.findViewById<TextView>(R.id.ICno)
        val phoneNo=view.findViewById<TextView>(R.id.mobileNo)
        val houseAddress=view.findViewById<TextView>(R.id.houseAddress)
        val email=view.findViewById<TextView>(R.id.email)
        val reason=view.findViewById<TextView>(R.id.reason)

        nextBtn.setOnClickListener{


            if(detailAllFill()){

                val bundle = Bundle()

                bundle.putString("firstName", firstName.text.toString())
                bundle.putString("lastName", lastName.text.toString())
                bundle.putString("icNo", icNo.text.toString())
                bundle.putString("phoneNo", phoneNo.text.toString())
                bundle.putString("houseAddress", houseAddress.text.toString())
                bundle.putString("email", email.text.toString())
                bundle.putString("reason", reason.text.toString())

                val fragment = RequestTypeFragment()
                fragment.setArguments(bundle)

                fragmentManager?.beginTransaction()?.replace(R.id.container_fragment,fragment)?.commit()

                //Navigation.findNavController(it).navigate(R.id.action_requestFragment_to_requestTypeFragment)

                //val Users=db.collection("RequestForm")

            }
        }

        backBtn.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_requestFragment_to_homeFragment)
        }

        adminBtn.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_requestFragment_to_requestListFragment)
        }
        return view
    }


}