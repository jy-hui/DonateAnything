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
import kotlin.collections.ArrayList


class DonateFragment : Fragment(){
    private lateinit var db: FirebaseFirestore

    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    private lateinit var titleNews:EditText

    private lateinit var icNo:EditText
    private lateinit var date:EditText
    private lateinit var itemDonate:Spinner

    private val IC_NUMBER = "^[0-9]{12}\$"

    var i = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    var title:String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_donate, container, false)
        val btnBack: ImageView =view.findViewById(R.id.back2)
        val argsFrom = this.arguments

        title = argsFrom?.getString("newTitle")

        val isFood = argsFrom?.getBoolean("isFoodItem")
        val isSupply = argsFrom?.getBoolean("isSupplyItem")
        val isMoney = argsFrom?.getBoolean("isMoneyItem")
        btnBack.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_donateFragment_to_newsFragment)
        }
        val titleNewsText= view.findViewById<TextView>(R.id.newsTitle);
        itemDonate= view.findViewById(R.id.itemDonate_spinner);

        titleNewsText.setText(title);
        var itemType = ""
        val item = ArrayList<String>()
        item.add( "Choose type of item donate");
        if(isFood == true){
            item.add( "Food");
            if(isSupply == true){
                item.add( "Daily Supply");
                if(isMoney == true){
                    item.add( "Money");
                }
            }else{
                if(isMoney == true){
                    item.add( "Money");
                }
            }
        }else{
            if(isSupply == true){
                item.add( "Daily Supply");
                if(isMoney == true){
                    item.add( "Money");
                }
            }else{
                if(isMoney == true){
                    item.add( "Money");
                }
            }
        }

        val item_arrayAdapter = ArrayAdapter(requireActivity().applicationContext,android.R.layout.simple_spinner_dropdown_item,item)
        itemDonate.adapter = item_arrayAdapter
        itemDonate.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                /*if(isFood == true){
                    if(isSupply == true){
                        if(isMoney == true){
                            when(position){//F=true,S=true,M=true
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
                        }else{
                            when(position){//F=true,S=true,M=false
                                0 ->{ i = 0;}
                                1 ->{ i = 1;
                                    Toast.makeText(requireActivity().applicationContext,"You selected "+ item_arrayAdapter.getItem(position),Toast.LENGTH_SHORT).show()
                                }
                                2 ->{ i = 1;
                                    Toast.makeText(requireActivity().applicationContext,"You selected "+ item_arrayAdapter.getItem(position),Toast.LENGTH_SHORT).show()
                                }
                                else ->{
                                    i = 0;
                                }
                            }
                        }
                    }else{
                        if(isMoney == true){
                            when(position){//F=true,S=false,M=true
                                0 ->{ i = 0;}
                                1 ->{ i = 1;
                                    Toast.makeText(requireActivity().applicationContext,"You selected "+ item_arrayAdapter.getItem(position),Toast.LENGTH_SHORT).show()
                                }
                                2 ->{ i = 2;
                                    Toast.makeText(requireActivity().applicationContext,"You selected "+ item_arrayAdapter.getItem(position),Toast.LENGTH_SHORT).show()
                                }
                                else ->{
                                    i = 0;
                                }
                            }
                        }else{
                            when(position){//F=true,S=false,M=false
                                0 ->{ i = 0;}
                                1 ->{ i = 1;
                                    Toast.makeText(requireActivity().applicationContext,"You selected "+ item_arrayAdapter.getItem(position),Toast.LENGTH_SHORT).show()
                                }
                                else ->{
                                    i = 0;
                                }
                            }
                        }
                    }
                }else{
                    if(isSupply == true){
                        if(isMoney == true){
                            when(position){//F=false,S=true,M=true
                                0 ->{ i = 0;}
                                1 ->{ i = 1;
                                    Toast.makeText(requireActivity().applicationContext,"You selected "+ item_arrayAdapter.getItem(position),Toast.LENGTH_SHORT).show()
                                }
                                2 ->{ i = 2;
                                    Toast.makeText(requireActivity().applicationContext,"You selected "+ item_arrayAdapter.getItem(position),Toast.LENGTH_SHORT).show()
                                }
                                else ->{
                                    i = 0;
                                }
                            }
                        }else{
                            when(position){//F=false,S=true,M=false
                                0 ->{ i = 0;}
                                1 ->{ i = 1;
                                    Toast.makeText(requireActivity().applicationContext,"You selected "+ item_arrayAdapter.getItem(position),Toast.LENGTH_SHORT).show()
                                }
                                else ->{
                                    i = 0;
                                }
                            }
                        }
                    }else{
                        if(isMoney == true){
                            when(position){//F=false,S=false,M=true
                                0 ->{ i = 0;}
                                1 ->{ i = 2;
                                    Toast.makeText(requireActivity().applicationContext,"You selected "+ item_arrayAdapter.getItem(position),Toast.LENGTH_SHORT).show()
                                }
                                else ->{
                                    i = 0;
                                }
                            }
                        }else{
                            when(position){//F=false,S=false,M=false
                                0 ->{ i = 0;}
                                else ->{
                                    i = 0;
                                }
                            }
                        }
                    }
                }*/
                itemType = item_arrayAdapter.getItem(position) + ""
                if(itemType.equals("Food",true)){
                    i = 1;
                    Toast.makeText(requireActivity().applicationContext, "1",Toast.LENGTH_SHORT).show()
                }else if(itemType.equals("Daily Supply",true)){
                    i = 1;
                    Toast.makeText(requireActivity().applicationContext, "1",Toast.LENGTH_SHORT).show()
                }else if(itemType.equals("Money",true)){
                    i = 2;
                    Toast.makeText(requireActivity().applicationContext, "2",Toast.LENGTH_SHORT).show()
                }else{
                    i =0;
                    Toast.makeText(requireActivity().applicationContext, "0",Toast.LENGTH_SHORT).show()
                }


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }



        val btnNext: Button =view.findViewById(R.id.nextBtn)
        btnNext.setOnClickListener(){

            when(i){
                0 -> {Toast.makeText(requireActivity().applicationContext,"Please type of item donate",Toast.LENGTH_SHORT).show()}
                1 -> {
                    Navigation.findNavController(it).navigate(R.id.action_donateFragment_to_donateForm1Fragment)
                    val bundle = Bundle()
                    bundle.putString("itemType",itemType)
                    //bundle.putString("icNo",view.findViewById(R.id.icNo))
                    //bundle.putString("date",view.findViewById(R.id.date_Tran))
                    val fragment = DonateFragment()
                    fragment.arguments = bundle
                    fragmentManager?.beginTransaction()?.replace(R.id.container_fragment,fragment)?.commit()
                }
                2 -> {Navigation.findNavController(it).navigate(R.id.action_donateFragment_to_donateForm2Fragment)}
                else ->{

                }
            }

        }


        // Inflate the layout for this fragment
        return view
    }




}