package com.example.donateanything

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.donateanything.databinding.ActivityTrackBinding

class TrackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track)

        val imgBack: ImageView = findViewById(R.id.imgBack)

        imgBack.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        val btnFind: Button = findViewById(R.id.btnFind)

        btnFind.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("geo: 3.215118, 101.728345")
            startActivity(intent)

        }
    }
}