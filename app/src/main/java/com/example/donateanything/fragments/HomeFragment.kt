package com.example.donateanything.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.Navigation
import com.example.donateanything.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_home, container, false)
        firebaseAuth= FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()

        val emailAdmin = firebaseAuth.currentUser!!.email.toString()
        var isAdmin: String? = null
        val adminBtn: Button = view.findViewById(R.id.adminBtn)
        adminBtn.visibility = View.GONE
        db.collection("USERS")
            .whereEqualTo("Email",emailAdmin)
            .get()
            .addOnSuccessListener { result ->

                for (document in result){
                    isAdmin = document.getString("token")
                }
                if(isAdmin.equals("Admin")) {
                    adminBtn.visibility = View.VISIBLE
                }else{
                    adminBtn.visibility = View.GONE
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }

        adminBtn.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_adminFragment)
        }
        val btnNews: ImageView =view.findViewById(R.id.imgNews)

        btnNews.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_newsFragment)
        }
        val btnEvent: ImageView =view.findViewById(R.id.imgEvent)

        btnEvent.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_eventFragment)
        }
        return view
    }

}