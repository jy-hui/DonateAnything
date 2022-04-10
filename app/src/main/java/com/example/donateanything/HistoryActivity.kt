package com.example.donateanything

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.donateanything.databinding.ActivityHistoryBinding
import kotlinx.android.synthetic.main.item_view.*

class HistoryActivity : AppCompatActivity() , MyAdapter.OnItemClickListener {

    private lateinit var binding: ActivityHistoryBinding

        private val infoList = listOf(
            Info(
                "1 Jan 2022",
                "[Completed]",
                "Daily Supply",
                "10 x Beds",
                "100 points gain",
                "View"
            ),
            Info(
                "14 Feb 2022",
                "[Completed]",
                "Money",
                "Total: RM 100.00",
                "10 points gain",
                "View"
            ),
            Info(
                "1 April 2022", "[Process]", "Daily Supply", "50 x Tooth Brush\n" +
                        "50 x T-Shirt\n" + "50 x Pant", "1500 points gain", "Track"
            )
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        //setContentView(R.layout.activity_history)
        var binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myAdapter = MyAdapter(infoList, this)

        binding.infoRV.adapter = myAdapter
        binding.infoRV.layoutManager = LinearLayoutManager(applicationContext)
        binding.infoRV.setHasFixedSize(true)

        val imgBackPage: ImageView = findViewById(R.id.imgBack)

        imgBackPage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun itemClick(position: Int) {
        val selectedInfo = infoList[position]
        Toast.makeText(
            applicationContext,
            selectedInfo.btnCheck, Toast.LENGTH_SHORT
        ).show()

        if (selectedInfo.btnCheck.contentEquals("View")) {
            val intentA = Intent(this, MainActivity::class.java)
            startActivity(intentA)
        } else if (selectedInfo.btnCheck.contentEquals("Track")) {
            val intentB = Intent(this, HistoryActivity::class.java)
            startActivity(intentB)
        }
    }
}
