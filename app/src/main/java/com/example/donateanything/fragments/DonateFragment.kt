package com.example.donateanything.fragments
import android.app.ProgressDialog
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.donateanything.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_donate.*
import java.util.*


class DonateFragment : Fragment(){
    private lateinit var db: FirebaseFirestore

    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    private lateinit var icNo:EditText
    private lateinit var campusSpinner:Spinner
    private lateinit var date:EditText
    private lateinit var itemDonate:Spinner

    private val IC_NUMBER = "^[0-9]{12}\$"

    var i = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_donate, container, false)
        val btnBack: ImageView =view.findViewById(R.id.back2)

        btnBack.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_donateFragment_to_newsFragment)
        }


        icNo= view.findViewById(R.id.icNo);
        campusSpinner= view.findViewById(R.id.campus_spinner);
        date= view.findViewById(R.id.date_Tran);
        itemDonate= view.findViewById(R.id.itemDonate_spinner);

        val campus = arrayOf(
            "Choose campus",
            "KL",
            "Penang",
            "Sarawak"
        )
        val campus_arrayAdapter = ArrayAdapter(requireActivity().applicationContext,android.R.layout.simple_spinner_dropdown_item,campus)
        campusSpinner.adapter = campus_arrayAdapter
        campusSpinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                when(position){
                    0 ->{ }
                    else ->{

                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        val item = arrayOf(
            "Choose type of item donate",
            "Food",
            "Daily Supply",
            "Money"
        )

        val item_arrayAdapter = ArrayAdapter(requireActivity().applicationContext,android.R.layout.simple_spinner_dropdown_item,item)
        itemDonate.adapter = item_arrayAdapter
        itemDonate.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                when(position){
                    0 ->{ i = 0;}
                    1 ->{ i = 1;
                        Toast.makeText(requireActivity().applicationContext,"You selected "+ item_arrayAdapter.getItem(position),Toast.LENGTH_SHORT).show()
                    }
                    2 ->{ i = 1;
                        Toast.makeText(requireActivity().applicationContext,"You selected "+ item_arrayAdapter.getItem(position),Toast.LENGTH_SHORT).show()
                    }
                    3 ->{ i = 2;
                        Toast.makeText(requireActivity().applicationContext,"You selected "+ item_arrayAdapter.getItem(position),Toast.LENGTH_SHORT).show()
                    }
                    else ->{
                        i = 0;
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }



        val btnNext: Button =view.findViewById(R.id.nextBtn)
        btnNext.setOnClickListener(){

            when(i){
                0 -> {Toast.makeText(requireActivity().applicationContext,"Please type of item donate",Toast.LENGTH_SHORT).show()}
                1 -> {Navigation.findNavController(it).navigate(R.id.action_donateFragment_to_donateForm1Fragment)}
                2 -> {Navigation.findNavController(it).navigate(R.id.action_donateFragment_to_donateForm2Fragment)}
                else ->{

                }
            }

        }


        // Inflate the layout for this fragment
        return view
    }




}