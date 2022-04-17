package com.example.donateanything.fragments

import android.content.ContentValues
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import com.example.donateanything.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_donate.*
import kotlinx.android.synthetic.main.fragment_request.*


class RequestFragment : Fragment() {

    private val PHONE_REGEX = "^(01)[0-9]-[0-9]{7,8}\$"

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun detailAllFill():Boolean{
        var isFill = true
        if(firstName.text.toString().isEmpty()&&lastName.text.toString().isEmpty()
            &&houseAddress.text.toString().isEmpty()&&email.text.toString().isEmpty()&&reason.text.toString().isEmpty()
            &&mobileNo.text.toString().isEmpty()&&ICno.text.toString().isEmpty()
        ){
            firstName.error="Please enter your first name"
            lastName.error="Please enter your last name"
            ICno.error="Please enter your IC No"
            mobileNo.error="Please enter your phone number"
            houseAddress.error="Please enter your house address"
            email.error="Please enter your email"
            reason.error="Please enter your reason"
            Toast.makeText(requireActivity().applicationContext,"Fields cannot be empty, please fill up the form", Toast.LENGTH_SHORT).show()
            isFill = false
        }
        if (firstName.text.toString().isEmpty()){
            firstName.error="Please enter your first name"
            firstName.requestFocus()
            isFill=false
        }
        if (lastName.text.toString().isEmpty()){
            lastName.error="Please enter your last name"
            lastName.requestFocus()
            isFill=false

        }
        if (ICno.text.toString().isEmpty()){
            ICno.error="Please enter your IC No"
            ICno.requestFocus()
            isFill=false
        }
        if (mobileNo.text.toString().isEmpty()){
            mobileNo.error="Please enter your phone number"
            mobileNo.requestFocus()
            isFill=false
        }else  if(!PHONE_REGEX.toRegex().matches(mobileNo?.text.toString())){
            mobileNo.error = "Invalid phone number! format: 011-11111111"
            isFill = false
        }
        if (houseAddress.text.toString().isEmpty()){
            houseAddress.error="Please enter your house address"
            houseAddress.requestFocus()
            isFill=false
        }
        if (email.text.toString().isEmpty()){
            email.error="Please enter your email"
            email.requestFocus()
            isFill=false
        }
        if(!(Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches())){
            email.error="Invalid email"
            email.requestFocus()
            isFill=false
        }
        if (reason.text.toString().isEmpty()){
            reason.error="Please enter your reason"
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

        firebaseAuth= FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()

        val firstName=view.findViewById<TextView>(R.id.firstName)
        val lastName=view.findViewById<TextView>(R.id.lastName)
        val ICno=view.findViewById<TextView>(R.id.ICno)
        val mobileNo=view.findViewById<TextView>(R.id.mobileNo)
        val houseAddress=view.findViewById<TextView>(R.id.houseAddress)
        val email=view.findViewById<TextView>(R.id.email)
        val reason=view.findViewById<TextView>(R.id.reason)

        val emailUser = firebaseAuth.currentUser!!.email.toString()
        email.text = emailUser.toString()

        nextBtn.setOnClickListener{


            if(detailAllFill()){

                val bundle = Bundle()

                bundle.putString("firstName", firstName.text.toString())
                bundle.putString("lastName", lastName.text.toString())
                bundle.putString("icNo", ICno.text.toString())
                bundle.putString("phoneNo", mobileNo.text.toString())
                bundle.putString("houseAddress", houseAddress.text.toString())
                bundle.putString("email", email.text.toString())
                bundle.putString("reason", reason.text.toString())

                val fragment = RequestTypeFragment()
                fragment.setArguments(bundle)

                fragmentManager?.beginTransaction()?.replace(R.id.container_fragment,fragment)?.commit()
                Navigation.findNavController(it).navigate(R.id.action_requestFragment_to_requestTypeFragment)

            }
        }

        backBtn.setOnClickListener(){
            //Navigation.findNavController(it).navigate(R.id.action_requestFragment_to_homeFragment)
            val fragment = HomeFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.container_fragment,fragment)?.commit()

        }

        return view
    }


}