package com.example.donateanything.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.donateanything.R

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)

        val companyPhone : TextView = view.findViewById(R.id.showCContact)
        val companyEmail : TextView = view.findViewById(R.id.showCEmail)
        val partnerPhone : TextView = view.findViewById(R.id.showPContact)
        val partnerEmail : TextView = view.findViewById(R.id.showPEmail)

        companyPhone.setOnClickListener{
            val intentCP = Intent(Intent.ACTION_DIAL)
            intentCP.data = Uri.parse("tel: 0123456789")
            startActivity(intentCP)
        }

        companyEmail.setOnClickListener{
            composeEmail("serious4gamers@gmail.com","Hello!")
        }

        partnerPhone.setOnClickListener{
            val intentPP = Intent(Intent.ACTION_DIAL)
            intentPP.data = Uri.parse("tel: 0129876543")
            startActivity(intentPP)
        }

        partnerEmail.setOnClickListener{
            composeEmail("donationforeveryone@gmail.com","Hello!")
        }

        return view
    }

    private fun composeEmail(address: String, subject: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, address)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, "Send To $address")
        }
            startActivity(intent)
    }

}