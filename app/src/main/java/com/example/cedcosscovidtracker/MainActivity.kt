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
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<Any>, response: Response<Any>)
            {
                Log.i("firstError"," we are here ")

                val responseData=Gson().toJson(response.body())

                val jsonObj=JSONObject(responseData)

                Log.i("secondError","We are here bro second line ")
                for (key in jsonObj.keys())
                {
                    if(!key.equals("State Unassigned"))
                    myStates.add(key)

                }
                Log.i("thirdError","We are now in third step ")

                for (i in 0..jsonObj.length())
                {
                        val state=jsonObj.getString(myStates[i])
                        val actives=jsonObj.getString("active").toString()
                        val deceased=jsonObj.getString("deceased").toString()
                        val recovered=jsonObj.getString("recovered").toString()
                        val modelData=MyCustomModel(state,deceased,actives,recovered)
                Log.i("fourthError","We are inside the loop and we are getting all the data")
                        allData.add(modelData)
                }

                Log.i("fifth","We are in the fifth logcat and we are finding the fift error ")
                adapter= CovidAdapter(this@MainActivity,allData)
                recyler.adapter=adapter
                recyler.layoutManager= LinearLayoutManager(this@MainActivity)
                adapter.notifyDataSetChanged()
                Log.i("sixth","we are in rhe sixth error ")
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {



            }

        })

    }

}

