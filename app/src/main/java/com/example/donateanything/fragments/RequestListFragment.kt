package com.example.donateanything.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.donateanything.Adapter
import com.example.donateanything.R
import com.example.donateanything.RequestList
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.fragment_request_list.*

class RequestListFragment : Fragment() {
    //private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: Adapter
    private lateinit var request_list: ArrayList<RequestList>
    var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_request_list, container, false)
        val backBtn: ImageView = view.findViewById(R.id.backBtnRequest)

        recyclerView = view.findViewById(R.id.requestListRecycler)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        //recyclerView.setHasFixedSize(true)

        request_list = arrayListOf()

        adapter = Adapter(request_list)

        recyclerView.adapter = adapter


        EventChangeListener()

        backBtn.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_requestListFragment_to_requestFragment)
        }

        return view
    }

    private fun EventChangeListener() {
        db= FirebaseFirestore.getInstance()

        db.collection("RequestForm")
            .addSnapshotListener(object : EventListener<QuerySnapshot>{
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if(error != null){
                        //Log.e("Error")
                        return
                    }
                    for(dc : DocumentChange in value?.documentChanges!!){
                        if(dc.type == DocumentChange.Type.ADDED){
                            request_list.add(dc.document.toObject(RequestList::class.java))
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

            })
    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        requestListRecycler.apply {

        }
    }
}