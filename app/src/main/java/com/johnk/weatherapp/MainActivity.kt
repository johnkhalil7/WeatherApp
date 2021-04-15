package com.johnk.weatherapp


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.johnk.weatherapp.ui.main.MainFragment
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class MainActivity : FragmentActivity(), MainFragment.ButtonListener {
    lateinit var requestQueue: RequestQueue
    var output: String = ""
    lateinit var weather: JSONArray
    lateinit var currentWeather: JSONObject

    var description: String = ""
    lateinit var main: JSONObject
    var humidity: Int = 0
    var temperature: Int = 0

    var windSpeed: Double = 0.0
    var tempStr: String = ""
    var sevenday: String = ""
    var time: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        //instantiate the request queue
        requestQueue = Volley.newRequestQueue(this)
        //create object request
        val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, "https://api.openweathermap.org/data/2.5/onecall?lat=42.3314&lon=-83.04575&appid= /* fill with your own api key */", null, 
                Response.Listener { response ->
                    try {
                        var theList: JSONArray = response.getJSONArray("daily") //grabs the daily array object in the JSON list

                        var counter: Int = 0

                        while(counter < 7) //7 days of the week
                        {
                            val Day: JSONObject = theList.getJSONObject(counter) //corresponds to the day of the week
                            weather = Day.getJSONArray("weather") //grabs weather array
                            currentWeather = weather.getJSONObject(0) //grabs the index of the weather array


                            description = currentWeather.getString("description") //grabs description of the weather

                            main = Day.getJSONObject("temp") //grabs the temp object
                            temperature = (main.getDouble("max") - 273.15).toInt() //gets the high temp of the day and converts it to celsius
                            humidity = Day.getInt("humidity") //grabs the humidity percentage

                            windSpeed = Day.getDouble("wind_speed") //grabs wind speed in meters/sec

                            val unixDt: Long = Day.getLong("dt") //grabs the date in unix timestamp
                            time = java.time.format.DateTimeFormatter.ISO_INSTANT.format(java.time.Instant.ofEpochSecond(unixDt)) //converts unix timestamp to real time

                            if(counter == 0) //special case to store today's weather
                            {
                                output = "Detroit, MI" + "\nDay of the Week: " + time + "\nDescription: " + description + "\nTemp: " + temperature + "°C\nHumidity: " + humidity + "%\nWind Speed: " + windSpeed + " meters/sec\n" + "\n"
                            }
                            else{
                                tempStr +="Detroit, MI" + "\nDay of the Week: " + time + "\nDescription: " + description + "\nTemp: " + temperature + "°C\nHumidity: " + humidity + "%\nWind Speed: " + windSpeed + " meters/sec\n" + "\n"
                            } //stores the weather of the remaining 6 days
                            counter++
                        }
                        sevenday = output + tempStr //concatenates today's output and the remaining 6 days in one long string
                    } catch (ex: JSONException) {
                        Log.e("JSON Error", ex.message!!)
                    }

                },
                Response.ErrorListener { })
        //end of JSON object request

        requestQueue.add(jsonObjectRequest)
    } //end onCreate

    override fun getData(): String {

        return output //returns output to fragment
    }

    override fun getSevenData(): String {


        return sevenday //returns output to fragment
    }
}


