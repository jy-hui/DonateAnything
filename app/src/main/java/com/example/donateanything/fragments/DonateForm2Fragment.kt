package com.example.donateanything.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import com.example.donateanything.R


class DonateForm2Fragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_donateform2, container, false)
        val btnBack: ImageView =view.findViewById(R.id.back4)
        val argsFrom = this.arguments
        val title = argsFrom?.getString("itemType")
        val icNo = argsFrom?.getString("icNo")
        val date = argsFrom?.getString("date")

        val titleText= view.findViewById<TextView>(R.id.donateItem);
        titleText.setText(title)
        val bankSpinner : Spinner = view.findViewById(R.id.bank_spinner)
        val accountNo : EditText = view.findViewById(R.id.accountNo)
        val payment : EditText = view.findViewById(R.id.payment)
        val pacNo : EditText = view.findViewById(R.id.pacNo)
        val requestBtn : Button = view.findViewById(R.id.requestBtn)
        btnBack.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_donateFragment_to_newsFragment)
        }

        val bank = arrayListOf("Bank", "May Bank", "HSBC Bank","Public Bank")

        val bank_arrayAdapter = ArrayAdapter(requireActivity().applicationContext,android.R.layout.simple_spinner_dropdown_item,bank)
        bankSpinner.adapter = bank_arrayAdapter
        bankSpinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val btnSubmit: Button =view.findViewById(R.id.submitBtn)

        btnSubmit.setOnClickListener(){


            //Navigation.findNavController(it).navigate(R.id.action_donateFragment_to_donateForm1Fragment)


        }

        // Inflate the layout for this fragment
        return view
    }


}