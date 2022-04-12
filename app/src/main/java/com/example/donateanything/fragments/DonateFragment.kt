package com.example.donateanything.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.donateanything.R
import kotlin.collections.ArrayList


class DonateFragment : Fragment(){
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
        val argsFrom = this.arguments
        val title = argsFrom?.getString("newTitle")
        val isFood = argsFrom?.getBoolean("isFoodItem")
        val isSupply = argsFrom?.getBoolean("isSupplyItem")
        val isMoney = argsFrom?.getBoolean("isMoneyItem")


        val icNo: EditText = view.findViewById(R.id.icNo)
        val date: EditText = view.findViewById(R.id.date_Tran)

        btnBack.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.action_donateFragment_to_newsFragment)
        }
        val titleNewsText= view.findViewById<TextView>(R.id.newsTitle);
        val itemDonate:Spinner = view.findViewById(R.id.itemDonate_spinner);

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

                itemType = item_arrayAdapter.getItem(position) + ""
                if(itemType.equals("Food",true)){
                    i = 1;
                    //Toast.makeText(requireActivity().applicationContext, "1",Toast.LENGTH_SHORT).show()
                }else if(itemType.equals("Daily Supply",true)){
                    i = 1;
                    //Toast.makeText(requireActivity().applicationContext, "1",Toast.LENGTH_SHORT).show()
                }else if(itemType.equals("Money",true)){
                    i = 2;
                    //Toast.makeText(requireActivity().applicationContext, "2",Toast.LENGTH_SHORT).show()
                }else{
                    i =0;
                    //Toast.makeText(requireActivity().applicationContext, "0",Toast.LENGTH_SHORT).show()
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
                    //Navigation.findNavController(it).navigate(R.id.action_donateFragment_to_donateForm1Fragment)
                    val bundle = Bundle()
                    bundle.putString("itemType",itemType)
                    bundle.putString("icNo",icNo.text.toString())
                    bundle.putString("date",date.text.toString())
                    val fragment1 = DonateForm1Fragment()
                    fragment1.arguments = bundle
                    fragmentManager?.beginTransaction()?.replace(R.id.container_fragment,fragment1)?.commit()
                }
                2 -> {
                    //Navigation.findNavController(it).navigate(R.id.action_donateFragment_to_donateForm2Fragment)
                    val bundle = Bundle()
                    bundle.putString("itemType",itemType)
                    bundle.putString("icNo",icNo.text.toString())
                    bundle.putString("date",date.text.toString())
                    val fragment2 = DonateForm2Fragment()
                    fragment2.arguments = bundle
                    fragmentManager?.beginTransaction()?.replace(R.id.container_fragment,fragment2)?.commit()
                }

                else ->{

                }
            }

        }


        // Inflate the layout for this fragment
        return view
    }




}