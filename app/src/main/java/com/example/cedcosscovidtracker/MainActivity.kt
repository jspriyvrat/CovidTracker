package com.example.cedcosscovidtracker

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cedcosscovidtracker.CovidInterFace.DataService.newsInstance
import com.google.gson.Gson
import com.littlemango.stacklayoutmanager.StackLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var state:String
    lateinit var actives:String
    lateinit var deceased:String
    lateinit var recovered:String
    lateinit var district:String


     var allData= mutableListOf<MyCustomModel>()
    lateinit var adapter: CovidAdapter
    var myStates= mutableListOf<String>()

    lateinit var recyler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyler=findViewById(R.id.myRecycler)

        getData()
    }

    private fun getData() {
        val  data =newsInstance.getCovidData()
        data.enqueue(object:Callback<Any>{
           override fun onResponse(call: Call<Any>, response: Response<Any>)
            {
                val responseData=Gson().toJson(response.body())
                val jsonObj=JSONObject(responseData)
                var  myData=jsonObj.getJSONObject("Uttar Pradesh")
                myData=myData.getJSONObject("districtData")
                for (key in myData.keys())
                {
                        state="Uttar_Pradesh"
                        actives= myData.getJSONObject(key).getString("active")
                        recovered=myData.getJSONObject(key).getString("recovered")
                        deceased=myData.getJSONObject(key).getString("deceased")
                        district=key.toString()

                    allData.add(MyCustomModel(state,deceased,actives,recovered,district))
                }
                adapter= CovidAdapter(this@MainActivity,allData)
                recyler.adapter=adapter
                val stackLayoutManager=StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
                stackLayoutManager.setPagerMode(true)
                stackLayoutManager.setPagerFlingVelocity(3000)
                stackLayoutManager.setItemChangedListener(object :StackLayoutManager.ItemChangedListener{
                    override fun onItemChanged(position: Int) {
                        container.setBackgroundColor(Color.parseColor(MyColors.getColor()))
                    }
                })
                recyler.layoutManager=stackLayoutManager
                adapter.notifyDataSetChanged()

            }

            override fun onFailure(call: Call<Any>, t: Throwable) {

            }

        })

    }

}

