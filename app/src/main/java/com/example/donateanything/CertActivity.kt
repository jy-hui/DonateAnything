package com.example.donateanything

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CertActivity: AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cert)

        firebaseAuth= FirebaseAuth.getInstance()

        val name : TextView = findViewById(R.id.showName)
        val donate : TextView = findViewById(R.id.showDonation)
        val date : TextView = findViewById(R.id.showDate)

        db= FirebaseFirestore.getInstance()
        db.collection("UserInfo")
            .get()
            .addOnSuccessListener { result ->
                for (document in result){
                    Log.d(ContentValues.TAG, "DocumentSnapshot data: ${document.getString("Name")}")
                    name.text = document.getString("Name")
                }

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }

        val details = intent.getStringExtra("Details")
        donate.text = details

        val dd = intent.getStringExtra("Date")
        date.text = dd

        val imgBackPage: ImageView = findViewById(R.id.imgBack)

        imgBackPage.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }
}