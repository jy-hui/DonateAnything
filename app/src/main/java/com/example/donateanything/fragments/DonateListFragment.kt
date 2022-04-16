package com.example.donateanything.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.donateanything.*
import com.example.donateanything.R
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.fragment_admin_donate_list.*

class DonateListFragment : Fragment(), DonateListAdapter.OnItemClickListener {
    //private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: DonateListAdapter
    private lateinit var donate_list: ArrayList<DonateList>
    var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_admin_donate_list, container, false)
        val backBtn: ImageView = view.findViewById(R.id.backBtn)

        recyclerView = view.findViewById(R.id.donateListRecycler)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        //recyclerView.setHasFixedSize(true)

        donate_list = arrayListOf()

        adapter = DonateListAdapter(donate_list, this)

        recyclerView.adapter = adapter


        EventChangeListener()

        backBtn.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_admin_donate_list_Fragment_to_adminFragment)
        }

        return view
    }

    private fun EventChangeListener() {
        db= FirebaseFirestore.getInstance()


        db.collection("DONATION")
            .addSnapshotListener(object : EventListener<QuerySnapshot>{
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    for(dc : DocumentChange in value?.documentChanges!!){
                        if(dc.type == DocumentChange.Type.ADDED){
                            donate_list.add(dc.document.toObject(DonateList::class.java))
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

            })


    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        donateListRecycler.apply {

        }
    }

    override fun itemClick(position: Int) {
        val selectedDonate = donate_list[position]

        val bundle = Bundle()

        bundle.putString("ID", selectedDonate.ID)


        val fragment = DonateDetailFragment()
        fragment.setArguments(bundle)

        fragmentManager?.beginTransaction()?.replace(R.id.container_fragment,fragment)?.commit()

    }
}