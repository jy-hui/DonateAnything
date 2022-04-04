package com.example.donateanything.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.Navigation
import com.example.donateanything.R


class NewsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_news, container, false)
        val btnBack: ImageView =view.findViewById(R.id.back)

        btnBack.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_newsFragment_to_homeFragment)
        }
        val donate1: Button =view.findViewById(R.id.donate1)

        donate1.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_newsFragment_to_donateFragment)
        }

        val donate2: Button =view.findViewById(R.id.donate2)

        donate2.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_newsFragment_to_donateFragment)
        }
        return view
    }

}