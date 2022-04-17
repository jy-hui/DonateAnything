package com.example.donateanything.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import com.example.donateanything.R


class EventFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_event, container, false)
        val btnBack: ImageView =view.findViewById(R.id.back)

        btnBack.setOnClickListener(){
            //Navigation.findNavController(it).navigate(R.id.action_eventFragment_to_homeFragment)
            val fragment = HomeFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.container_fragment,fragment)?.commit()

        }
        return view
    }


}