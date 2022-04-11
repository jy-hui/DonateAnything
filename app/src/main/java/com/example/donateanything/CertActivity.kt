package com.example.donateanything

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CertActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cert)

        val name : TextView = findViewById(R.id.showName)
        val donate : TextView = findViewById(R.id.showDonation)
        val date : TextView = findViewById(R.id.showDate)

        val imgBackPage: ImageView = findViewById(R.id.imgBack)

        imgBackPage.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }
}