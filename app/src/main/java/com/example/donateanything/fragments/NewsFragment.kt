package com.example.donateanything.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
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
            val bundle = Bundle()
            bundle.putString("newTitle",getString(R.string.newsTitle1))
            bundle.putBoolean("isFoodItem",false)
            bundle.putBoolean("isSupplyItem",true)
            bundle.putBoolean("isMoneyItem",true)
            val fragmentItem = DonateFragment()
            fragmentItem.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.container_fragment,fragmentItem)?.commit()


        }

        val donate2: Button =view.findViewById(R.id.donate2)

        donate2.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_newsFragment_to_donateFragment)
            val bundle = Bundle()
            bundle.putString("newTitle",getString(R.string.newsTitle2))
            bundle.putBoolean("isFoodItem",true)
            bundle.putBoolean("isSupplyItem",true)
            bundle.putBoolean("isMoneyItem",false)
            val fragmentItem = DonateFragment()
            fragmentItem.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.container_fragment,fragmentItem)?.commit()

        }
        return view
    }

}