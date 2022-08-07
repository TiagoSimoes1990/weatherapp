package com.tjsimoes.weatherappw


import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class WeatherDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)

        //Get Coordinates from previous activity
        val lat : String = intent.getStringExtra("geoLatitude").toString()
        val lon : String = intent.getStringExtra("geoLongitude").toString()

        weatherRequest(lat, lon).execute()

    }

    inner class weatherRequest(var reqLat : String, var reqLon : String): AsyncTask<String, Void,String> () {

        //TODO: Information to user that something is being processed

        override fun doInBackground(vararg p0: String?): String {

            val apiURL = "https://api.openweathermap.org/data/2.5/weather?lat=" + reqLat + "&lon=" + reqLon +"&appid=" + getString(R.string.api_key) + "&units=metric"
            //TODO: Handle the exception if there is time

            val response : String = URL(apiURL).readText(Charsets.UTF_8)

            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            //Getting weather info from JSON
            val jsonObject = JSONObject(result)

            //Weather parameters
            val weather = jsonObject.getJSONArray("weather").getJSONObject(0)
            val description = weather.getString("description")

            //Main parameters
            val main = jsonObject.getJSONObject("main")
            val temp = main.getString("temp") + " ºC"
            val tempMin = "Min: " + main.getString("temp_min") + " ºC"
            val tempMax = "Max: " + main.getString("temp_max") + " ºC"
            val pressure = main.getString("pressure") + " mb"
            val humidity = main.getString("humidity") +  "%"

            //Wind Parameters
            val wind = jsonObject.getJSONObject("wind")
            val windSpeed = wind.getString("speed") + " m/s"

            //Last time when data was updated
            val lastUpdateAt = jsonObject.getLong("dt")
            //Multiply by 1000 to get the correct date and time
            val lastUpdateAtTxt = "Last Update: " + SimpleDateFormat("dd/mm/yyyy, HH:mm:ss", Locale.ENGLISH).format(
                Date(lastUpdateAt*1000))

            //"SYS" parameters
            val sys = jsonObject.getJSONObject("sys")
            val sunrise : Long = sys.getLong("sunrise")
            val sunset : Long = sys.getLong("sunset")
            val country = sys.getString("country")
            val address = jsonObject.getString("name") + ", " + country

            //Attaching data to the correspondent views
            findViewById<TextView>(R.id.tv_location_name_id).text = address
            findViewById<TextView>(R.id.tv_last_update_id).text = lastUpdateAtTxt
            findViewById<TextView>(R.id.tv_status_id).text = description
            findViewById<TextView>(R.id.tv_temp_id).text = temp
            findViewById<TextView>(R.id.tv_temp_min_id).text = tempMin
            findViewById<TextView>(R.id.tv_temp_max_id).text = tempMax
            findViewById<TextView>(R.id.tv_sunrise_id).text = SimpleDateFormat("HH:mm", Locale.ENGLISH).format(Date(sunrise*1000))
            findViewById<TextView>(R.id.tv_sunset_id).text = SimpleDateFormat("HH:mm", Locale.ENGLISH).format(Date(sunset*1000))
            findViewById<TextView>(R.id.tv_wind_id).text = windSpeed
            findViewById<TextView>(R.id.tv_pressure_id).text = pressure
            findViewById<TextView>(R.id.tv_humidity_id).text = humidity

            //Set the view of the main container


        }

    }


}