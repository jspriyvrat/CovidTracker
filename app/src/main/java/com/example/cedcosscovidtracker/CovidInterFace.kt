package com.example.cedcosscovidtracker

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL="https://data.covid19india.org/"


interface CovidInterFace {

        @GET("state_district_wise.json")
        fun getCovidData(): Call<Any>

        object DataService{
            val newsInstance:CovidInterFace
            init {
                val retrofit= Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
                    GsonConverterFactory.create())
                    .build()
                newsInstance=retrofit.create(CovidInterFace::class.java)
            }
        }



}