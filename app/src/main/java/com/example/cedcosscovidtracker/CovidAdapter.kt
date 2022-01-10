package com.example.cedcosscovidtracker

import android.content.Context
import android.view.ContentInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CovidAdapter(val context: Context,val data:MutableList<MyCustomModel>):RecyclerView.Adapter<CovidAdapter.Inner>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Inner {
       val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.myrow,parent,false)
        return Inner(view)
    }

    override fun onBindViewHolder(holder: Inner, position: Int) {
       val d=data[position]
        holder.txtState.text=d.state
        holder.txtCasualties.text=d.deceased
        holder.txtrecovered.text=d.recovered
        holder.txtActive.text=d.active
    }

    override fun getItemCount(): Int {
        return  data.size
    }



    class Inner(view: View):RecyclerView.ViewHolder(view)
    {
       var txtState: TextView =view.findViewById(R.id.statename)
        var txtCasualties:TextView=view.findViewById(R.id.casualty)
        var txtrecovered:TextView=view.findViewById(R.id.recovered)
        var txtActive:TextView=view.findViewById(R.id.active)
    }

}