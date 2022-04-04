package com.example.donateanything

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController= findNavController(R.id.container_fragment)
        val bottomNavigationView= findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.setupWithNavController(navController)

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