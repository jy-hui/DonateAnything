package com.example.donateanything

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    //SharedPreferences
    private lateinit var sharePref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        firebaseAuth= FirebaseAuth.getInstance()
        sharePref = getSharedPreferences("rememberMe", MODE_PRIVATE)

        btnLogout.setOnClickListener {
            with(sharePref.edit()){
                clear()
                apply()
            }
            firebaseAuth.signOut()
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            Toast.makeText(this,"Logout...", Toast.LENGTH_SHORT).show()
            finish()
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
                exitProcess(0)
            })
            .setNegativeButton("No",DialogInterface.OnClickListener(){dialog, which -> dialog.cancel() })

        val alert : AlertDialog = builder.create()
        alert.show()
        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.black))
        alert.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.black))
    }
}