package com.example.donateanything.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import com.example.donateanything.R

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_home, container, false)
        val btnNews: ImageView =view.findViewById(R.id.imgNews)

        btnNews.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_newsFragment)
        }
        val btnEvent: ImageView =view.findViewById(R.id.imgEvent)

        btnEvent.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_eventFragment)
        }
        return view
    }

}