package com.example.cedcosscovidtracker


object MyColors{
    val color= arrayOf("#ff9400","#ffc000","#003da6","#01e1ec","#9620a4","#79eb00","#01e1ec")
    var colorindex=1
    fun getColor():String
    {
        return  color[colorindex++ % color.size]
    }
}