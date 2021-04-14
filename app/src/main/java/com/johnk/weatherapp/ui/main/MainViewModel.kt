package com.johnk.weatherapp.ui.main

import androidx.lifecycle.ViewModel


class MainViewModel : ViewModel() {

    private var wth: String = ""

    fun setWeather(weather: String)
    {
        wth = weather
    }

    fun getWeather(): String?
    {
        return wth
    }




}

