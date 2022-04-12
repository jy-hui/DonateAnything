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


class News2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_news2, container, false)
        val btnBack: ImageView =view.findViewById(R.id.back)

        btnBack.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_news2Fragment_to_newsFragment)

        }
        val donate2: Button =view.findViewById(R.id.donate2)

        donate2.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_news2Fragment_to_donateFragment)
            val bundle = Bundle()
            bundle.putString("newTitle",getString(R.string.newsTitle2))
            bundle.putBoolean("isFoodItem",true)
            bundle.putBoolean("isSupplyItem",false)
            bundle.putBoolean("isMoneyItem",true)
            val fragmentItem = DonateFragment()
            fragmentItem.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.container_fragment,fragmentItem)?.commit()

        }


        return view
    }

}