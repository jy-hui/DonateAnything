package com.example.donateanything

import android.content.ContentValues
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController= findNavController(R.id.container_fragment)
        val bottomNavigationView= findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.setupWithNavController(navController)
        firebaseAuth= FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()

        val emailAdmin = firebaseAuth.currentUser!!.email.toString()
        var isAdmin: String? = null

        db.collection("USERS")
            .whereEqualTo("Email",emailAdmin)
            .get()
            .addOnSuccessListener { result ->

                for (document in result){
                    isAdmin = document.getString("token")
                }
                if(isAdmin.equals("Admin")) {
                    //adminBtn.visibility = View.VISIBLE
                }else{
                    //adminBtn.visibility = View.GONE
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }
   }


    override fun onBackPressed() {
        val builder : AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Quit the Application")
        builder.setMessage("Are you sure to close the application?")
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener(){ dialog, which ->
                moveTaskToBack(true)
                android.os.Process.killProcess(android.os.Process.myPid())
                finish()
                exitProcess(0)
            })
            .setNegativeButton("No",DialogInterface.OnClickListener(){dialog, which -> dialog.cancel() })

        val alert : AlertDialog = builder.create()
        alert.show()
        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.black))
        alert.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.black))
    }
}