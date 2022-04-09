package com.example.donateanything.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.donateanything.HistoryActivity
import com.example.donateanything.LoginActivity
import com.example.donateanything.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.*


class ProfileFragment : Fragment() {

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    //SharedPreferences
    private lateinit var sharePref : SharedPreferences

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


}