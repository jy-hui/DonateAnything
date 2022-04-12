package com.example.donateanything.fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.donateanything.HistoryActivity
import com.example.donateanything.LoginActivity
import com.example.donateanything.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*


class ProfileFragment : Fragment() {

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    //SharedPreferences
    private lateinit var sharePref : SharedPreferences
    private lateinit var db : FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        val sharePref = requireActivity().applicationContext.getSharedPreferences(
            "rememberMe",
            Context.MODE_PRIVATE
        )
        val btnLogout: Button = view.findViewById(R.id.btnLogout)
        val btnHist: Button = view.findViewById(R.id.btnHistory)
        val btnBrowse: Button = view.findViewById(R.id.btnBrowse)
        val btnChange: Button = view.findViewById(R.id.btnChange)
        val btnConfirm: Button = view.findViewById(R.id.btnConfirm)
        val showName: TextView = view.findViewById(R.id.tvName)
        val showingEmail: TextView = view.findViewById(R.id.showingEmail)
        val showingPhone: TextView = view.findViewById(R.id.showingPhone)
        val showEmail: EditText = view.findViewById(R.id.showEmail)
        val showPhone: EditText = view.findViewById(R.id.showPhone)
        val tvPoint: TextView = view.findViewById(R.id.tvCPoints)

        val email = firebaseAuth.currentUser!!.email.toString()

        db = FirebaseFirestore.getInstance()
        db.collection("UserInfo")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.getString("Point")}")
                    showName.text = document.getString("Name")
                    showingPhone.text = document.getString("Phone")
                    tvPoint.text = document.getString("Point")
                    showingEmail.text = document.getString("Email")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        btnChange.setOnClickListener{
            showingEmail.visibility = GONE
            showingPhone.visibility = GONE
            showEmail.visibility = VISIBLE
            showPhone.visibility = VISIBLE
            btnChange.visibility = GONE
            btnConfirm.visibility = VISIBLE
        }

        btnConfirm.setOnClickListener{
            showingEmail.visibility = VISIBLE
            showingPhone.visibility = VISIBLE
            showEmail.visibility = GONE
            showPhone.visibility = GONE
            btnChange.visibility = VISIBLE
            btnConfirm.visibility = GONE

            val newEmail = showEmail.text.toString()
            showingEmail.text = newEmail

            val phone = showPhone.text.toString()
            showingPhone.text = phone

            updateInfo(email, newEmail, phone)
        }

        btnBrowse.setOnClickListener {
            startForResult.launch("image/*")
        }

        btnLogout.setOnClickListener {
            with(sharePref.edit()){
                clear()
                putString("pref_rmbMe", "No")
                apply()
            }
            FirebaseAuth.getInstance().signOut()
            val intent = Intent (view.context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        btnHist.setOnClickListener {
            val intentH = Intent (view.context, HistoryActivity::class.java)
            startActivity(intentH)
        }
        return view
    }

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.GetContent()){ uri->
            imgProfile.setImageURI(uri)
    }

    private fun updateInfo (email:String, newEmail:String, phone:String){
        val refresh = db.collection("UserInfo").document(email)
        refresh.update("Email",newEmail)
        refresh.update("Phone",phone)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }

}

