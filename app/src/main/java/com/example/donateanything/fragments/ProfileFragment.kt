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
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.donateanything.HistoryActivity
import com.example.donateanything.LoginActivity
import com.example.donateanything.R
import com.example.donateanything.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    //SharedPreferences
    private lateinit var sharePref : SharedPreferences
    private var db : FirebaseFirestore? =  null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_profile, container, false)

        firebaseAuth= FirebaseAuth.getInstance()
        val sharePref = requireActivity().applicationContext.getSharedPreferences("rememberMe", Context.MODE_PRIVATE)
        val btnLogout: Button = view.findViewById(R.id.btnLogout)
        val btnHist : Button = view.findViewById(R.id.btnHistory)
        val btnBrowse: Button = view.findViewById(R.id.btnBrowse)
        val showName: TextView = view.findViewById(R.id.tvName)
        val showingEmail: TextView = view.findViewById(R.id.showingEmail)
        val showingPhone: TextView = view.findViewById(R.id.showingPhone)

        //Not sure
        db?.collection("USERS")?.get()

        val userN = db?.collection("USERS")?.document("email")
        userN?.get()
            ?.addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            ?.addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }


        //Got error, only show email
        val n = firebaseAuth.currentUser?.displayName.toString()
        showName.text = n
        val email = firebaseAuth.currentUser!!.email.toString()
        showingEmail.text = email
        val phone = firebaseAuth.currentUser?.phoneNumber.toString()
        showingPhone.text = phone

        btnBrowse.setOnClickListener(){
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
}