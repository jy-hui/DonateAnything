package com.example.donateanything.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.donateanything.R
import com.google.firebase.firestore.FirebaseFirestore


class RequestListDetailFragment : Fragment() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_request_list_detail, container, false)

        db= FirebaseFirestore.getInstance()

        val firstNameDetail: TextView = view.findViewById(R.id.firstNameDetail)
        val lastNameDetail: TextView = view.findViewById(R.id.lastNameDetail)
        val icNoDetail: TextView = view.findViewById(R.id.icNoDetail)
        val phoneNoDetail: TextView = view.findViewById(R.id.phoneNoDetail)
        val houseAddressDetail: TextView = view.findViewById(R.id.houseAddressDetail)
        val emailDetail: TextView = view.findViewById(R.id.emailDetail)
        val reasonDetail: TextView = view.findViewById(R.id.reasonDetail)
        val specificDetail: TextView = view.findViewById(R.id.specificDetail)


        val args = this.arguments

        val firstName = args?.get("firstName")
        val lastName = args?.get("lastName")
        val icNo = args?.get("icNo")
        val phoneNo = args?.get("phoneNo")
        val houseAddress = args?.get("houseAddress")
        val email = args?.get("email")
        val reason = args?.get("reason")
        val specific = args?.get("specific")
        val document = args?.get("document")

        firstNameDetail.text = firstName.toString()
        lastNameDetail.text = lastName.toString()
        icNoDetail.text = icNo.toString()
        phoneNoDetail.text = phoneNo.toString()
        houseAddressDetail.text = houseAddress.toString()
        emailDetail.text = email.toString()
        reasonDetail.text = reason.toString()
        specificDetail.text = specific.toString()

        val backBtnRequest2: ImageView = view.findViewById(R.id.backBtnRequest2)
        val approveButton: Button = view.findViewById(R.id.approveBtn)
        val cancelButton: Button = view.findViewById(R.id.cancelBtn)

        val requestFormDb = db.collection("RequestForm")

        backBtnRequest2.setOnClickListener(){
            val fragmentBack = RequestListFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.container_fragment,fragmentBack)?.commit()
        }

        approveButton.setOnClickListener(){
            requestFormDb.document(document.toString()).update("Approve", true)
                .addOnSuccessListener {

                    Toast.makeText(requireActivity().applicationContext, "This request is Approved", Toast.LENGTH_SHORT).show()
                    val fragmentBack = RequestListFragment()
                    fragmentManager?.beginTransaction()?.replace(R.id.container_fragment,fragmentBack)?.commit()
                }.addOnFailureListener { e ->
                    Toast.makeText(requireActivity().applicationContext,e.toString(), Toast.LENGTH_SHORT).show()
                }
        }

        cancelButton.setOnClickListener(){
            requestFormDb.document(document.toString()).delete()
                .addOnSuccessListener {
                    Toast.makeText(requireActivity().applicationContext, "This request is canceled", Toast.LENGTH_SHORT).show()
                    val fragmentBack = RequestListFragment()
                fragmentManager?.beginTransaction()?.replace(R.id.container_fragment,fragmentBack)?.commit()
            }.addOnFailureListener { e ->
                    Toast.makeText(requireActivity().applicationContext,e.toString(), Toast.LENGTH_SHORT).show()
                }
        }
        return view
    }

}