package com.example.donateanything.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.donateanything.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_request.*
import kotlinx.android.synthetic.main.fragment_request_type.*


class RequestTypeFragment : Fragment() {
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun detailAllFill(): Boolean {
        //var isFill = true
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_request_type, container, false)

        val supply=view.findViewById<CheckBox>(R.id.dailySupply)
        val foodDrink=view.findViewById<CheckBox>(R.id.foodDrink)
        val money=view.findViewById<CheckBox>(R.id.money)
        val other=view.findViewById<CheckBox>(R.id.other)
        val specific=view.findViewById<TextView>(R.id.reason2)

        var isSupply = ""
        var isFoodDrink = ""
        var isMoney = ""
        var isOther = ""

        val args = this.arguments
        val firstName = args?.get("firstName")
        val lastName = args?.get("lastName")
        val icNo = args?.get("icNo")
        val phoneNo = args?.get("phoneNo")
        val houseAddress = args?.get("houseAddress")
        val email = args?.get("email")
        val reason = args?.get("reason")

        val submitBtn: Button =view.findViewById(R.id.submitBtn)
        val backBtn2: ImageView = view.findViewById(R.id.backBtn2)

        db= FirebaseFirestore.getInstance()

        submitBtn.setOnClickListener{
            if(supply.isChecked){
                isSupply = "Daily Supply"
            }
            if(foodDrink.isChecked){
                isFoodDrink = "Food, Drink"
            }
            if(money.isChecked){
                isMoney = "Money"
            }
            if(other.isChecked){
                isOther = "Other"
            }

            if(detailAllFill()){
                val requestForm = hashMapOf(
                    "FirstName" to firstName,//.text.toString(),
                    "LastName" to lastName,//.text.toString(),
                    "ICNo" to icNo,//.text.toString(),
                    "Phone" to phoneNo,//.text.toString(),
                    "HouseAddress" to houseAddress,//.text.toString(),
                    "Email" to email,//.text.toString(),
                    "Reason" to reason,//.text.toString()
                    "DailySupply" to isSupply,
                    "FoodDrink" to isFoodDrink,
                    "Money" to isMoney,
                    "Other" to isOther,
                    "Specific" to specific.text.toString()
                )

                db.collection("RequestForm")
                    .add(requestForm)
                    .addOnSuccessListener { documentReference ->
                       // Toast.makeText(this, "Sign Up successfully!", Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(it).navigate(R.id.action_requestTypeFragment_to_homeFragment)
                            //Toast.makeText(getActivity(),)
                    }
                    .addOnFailureListener { e ->
                        //Log.w(TAG, "Error adding document", e)
                        //Toast.makeText(this,error.toString(), Toast.LENGTH_SHORT).show()
                    }
            }
        }

        backBtn2.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_requestTypeFragment_to_requestFragment)
        }
        return view
    }


}