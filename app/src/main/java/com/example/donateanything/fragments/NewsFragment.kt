package com.example.donateanything.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.donateanything.R


class NewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_news, container, false)
        val btnBack: ImageView =view.findViewById(R.id.back)

        btnBack.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_newsFragment_to_homeFragment)

        }
        val news1: ImageView =view.findViewById(R.id.news1)

        news1.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_newsFragment_to_news1Fragment)

        }

        val news2: ImageView =view.findViewById(R.id.news2)
        news2.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_newsFragment_to_news2Fragment)
        }
        return view
    }

}