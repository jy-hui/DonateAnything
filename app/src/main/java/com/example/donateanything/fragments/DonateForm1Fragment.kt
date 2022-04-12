package com.example.donateanything.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import com.example.donateanything.R


class DonateForm1Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_donateform1, container, false)
        val btnBack: ImageView =view.findViewById(R.id.back3)
        val argsFrom = this.arguments
        val title = argsFrom?.getString("itemType")
        val icNo = argsFrom?.getString("icNo")
        val date = argsFrom?.getString("date")

        val titleText= view.findViewById<TextView>(R.id.donateItem)
        titleText.text = title
        val itemName : EditText = view.findViewById(R.id.itemName)
        val valueNo: EditText = view.findViewById(R.id.valueNo)
        val unitSpinner: Spinner = view.findViewById(R.id.unit_spinner)
        val transSwitch: Switch = view.findViewById(R.id.transportion_s)
        val addressText: EditText = view.findViewById(R.id.address)
        addressText.visibility = View.INVISIBLE
        val btnSubmit: Button =view.findViewById(R.id.submitBtn)


        btnBack.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_donateFragment_to_newsFragment)
        }

        val unit = arrayListOf("unit","bottle","bag", "kg","g", "ml","l")

        val unit_arrayAdapter = ArrayAdapter(requireActivity().applicationContext,android.R.layout.simple_spinner_dropdown_item,unit)
        unitSpinner.adapter = unit_arrayAdapter
        unitSpinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener {
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

        transSwitch.setOnCheckedChangeListener { compoundButton, onSwitch ->
            if(onSwitch){
                addressText.visibility = View.VISIBLE
            }else{
                addressText.visibility = View.INVISIBLE
            }
        }





        btnSubmit.setOnClickListener {


            //Navigation.findNavController(it).navigate(R.id.action_donateFragment_to_donateForm1Fragment)


        }

        // Inflate the layout for this fragment
        return view
    }


}