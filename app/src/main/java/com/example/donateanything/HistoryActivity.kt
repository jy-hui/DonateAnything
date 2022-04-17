package com.example.donateanything

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.donateanything.databinding.ActivityHistoryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class HistoryActivity : AppCompatActivity() , MyAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewRequest: RecyclerView
    private lateinit var db : FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var adapter: MyAdapter
    private lateinit var adapterRequest: RequestAdapter
    private lateinit var hList: ArrayList<HistoryList>
    private lateinit var rList: ArrayList<RequestList>
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val historyButton: Button =findViewById(R.id.historyBtn)
        val requestButton: Button =findViewById(R.id.requestListBtn)

        recyclerView = findViewById(R.id.infoRV)
        recyclerView.visibility = View.VISIBLE
        recyclerViewRequest = findViewById(R.id.requestRV)
        recyclerViewRequest.visibility = View.GONE

            recyclerView.layoutManager = LinearLayoutManager(applicationContext)
            recyclerView.setHasFixedSize(true)

            hList = arrayListOf()

            adapter = MyAdapter(hList, this)
            recyclerView.adapter = adapter

            recyclerViewRequest.layoutManager = LinearLayoutManager(applicationContext)
            recyclerViewRequest.setHasFixedSize(true)

            rList = arrayListOf()

            adapterRequest = RequestAdapter(rList, this)
            recyclerViewRequest.adapter = adapterRequest


        //val myAdapter = MyAdapter(infoList, this)


        //binding.infoRV.adapter = MyAdapter(hList, this)
        //binding.infoRV.layoutManager = LinearLayoutManager(applicationContext)
        //binding.infoRV.setHasFixedSize(true)

        EventChangeListener()

        val imgBackPage: ImageView = findViewById(R.id.imgBack)

        historyButton.setOnClickListener {
            recyclerView.visibility = View.VISIBLE
            recyclerViewRequest.visibility = View.GONE
        }
        requestButton.setOnClickListener{
            recyclerView.visibility = View.GONE
            recyclerViewRequest.visibility = View.VISIBLE
        }

        imgBackPage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun itemClick(position: Int) {
        val selectedInfo = hList[position]

        Toast.makeText(
            applicationContext,
            selectedInfo.btnCheck, Toast.LENGTH_SHORT
        ).show()

        if (selectedInfo.btnCheck.contentEquals("View")) {
            val intentA = Intent(this, CertActivity::class.java)
                if(selectedInfo.Value != null || selectedInfo.Item != null) {
                    intentA.putExtra("Details",selectedInfo.Value + " x "+ selectedInfo.Item)
                }
                else if (selectedInfo.Bank != null || selectedInfo.Payment != null){
                    intentA.putExtra("Details",selectedInfo.Bank + " : RM "+ selectedInfo.Payment)
                }
                intentA.putExtra("Date",selectedInfo.Date)
            startActivity(intentA)
        } else if (selectedInfo.btnCheck.contentEquals("Track")) {
            val intentB = Intent(this, TrackActivity::class.java)
            startActivity(intentB)
        }
    }

//    fun itemClickRequest(position: Int){
//        val selectedRequest = rList[position]
//
//        if(selectedRequest.Status.contentEquals("pending")){
//            val builder: AlertDialog.Builder = AlertDialog.Builder(applicationContext)
//            builder.setCancelable(true)
//            builder.setTitle("Delete")
//            builder.setMessage("Are you sure want to delete ?")
//            builder.setPositiveButton("Confirm",
//                DialogInterface.OnClickListener { dialog, which ->
//                    db.collection("RequestForm").document(selectedRequest.ID.toString()).delete()
//                        .addOnSuccessListener {
//                            Toast.makeText(applicationContext.applicationContext, "Deleted", Toast.LENGTH_SHORT).show()
//                        }.addOnFailureListener { e ->
//                            Toast.makeText(applicationContext.applicationContext,e.toString(), Toast.LENGTH_SHORT).show()
//                        }
//                })
//            builder.setNegativeButton(android.R.string.cancel,
//                DialogInterface.OnClickListener { dialog, which -> })
//
//            val dialog: AlertDialog = builder.create()
//            dialog.show()
//        }
//    }

    private fun EventChangeListener() {
        firebaseAuth= FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()
        val email = firebaseAuth.currentUser!!.email.toString()
        db.collection("DONATION").whereEqualTo("Email", email)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    for(dc : DocumentChange in value?.documentChanges!!){
                        if(dc.type == DocumentChange.Type.ADDED){
                            hList.add(dc.document.toObject(HistoryList::class.java))
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

            })

        db.collection("RequestForm").whereEqualTo("Email", email)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    for(dc : DocumentChange in value?.documentChanges!!){
                        if(dc.type == DocumentChange.Type.ADDED){
                            rList.add(dc.document.toObject(RequestList::class.java))
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

            })
    }

}
