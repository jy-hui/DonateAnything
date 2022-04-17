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

class AdminFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_admin, container, false)

        val homeBtn: Button = view.findViewById(R.id.homeBtn)
        homeBtn.setOnClickListener(){
            //Navigation.findNavController(it).navigate(R.id.action_adminFragment_to_homeFragment)
            val fragmentHome = HomeFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.container_fragment,fragmentHome)?.commit()

        }

        val btnRes: ImageView =view.findViewById(R.id.imgRes)

        btnRes.setOnClickListener(){
            //Navigation.findNavController(it).navigate(R.id.action_adminFragment_to_request_list_Fragment)
            val fragment = RequestListFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.container_fragment,fragment)?.commit()

        }
        val btnDonate: ImageView =view.findViewById(R.id.imgDonate)

        btnDonate.setOnClickListener(){
            //Navigation.findNavController(it).navigate(R.id.action_adminFragment_to_admin_donate_list_Fragment)
            val fragment = DonateListFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.container_fragment,fragment)?.commit()

        }

        return view
    }

}