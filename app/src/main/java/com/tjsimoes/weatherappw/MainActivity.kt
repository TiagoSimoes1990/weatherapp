package com.tjsimoes.weatherappw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //region Information about cities and location
        //Latitude
        var latitude = arrayOf(
            "40.2056",//Coimbra Coordinates
            "55.6759",
            "53.344",
            "38.7167",
            "51.5085",
            "40.4165",
            "48.8534",
            "50.088",
            "41.8947",
            "48.2085",
            "52.5244"
        )

        //Longitude
        var longitude = arrayOf(
            "-8.4195",//Coimbra Coordinates
            "12.5655",
            "-6.2672",
            "-9.1333",
            "-0.1257",
            "-3.7026",
            "2.3488",
            "14.4208",
            "12.4839",
            "16.3721",
            "13.4105"
        )

        //Setting Names
        val locationName = arrayListOf(
            "Current Location",
            "Copenhagen",
            "Dublin",
            "Lisbon",
            "London",
            "Madrid",
            "Paris",
            "Prague",
            "Rome",
            "Vienna",
            "Berlin"
        )

        //Setting Images
        val locImageId = arrayListOf(
            R.drawable.current_location,
            R.drawable.copenhagen,
            R.drawable.dublin,
            R.drawable.lisbon,
            R.drawable.london,
            R.drawable.madrid,
            R.drawable.paris,
            R.drawable.prague,
            R.drawable.rome,
            R.drawable.vienna,
            R.drawable.berlin

        )
        //endregion
        val listView = findViewById<ListView>(R.id.citieslistview)
        val getLocation = GetLocation(this)

        listView.adapter = LocationListAdapter(this, locationName, locImageId)

        listView.setOnItemClickListener { parent, view, position, id ->

            val intent = Intent(this, WeatherDetailsActivity::class.java)

            if (position == 0) {
                getLocation.coordinates(object : CoordinatesListener {
                    override fun onCoordinatesUpdate(locationCoordinates: LocationCoordinates) {
                        latitude[0] = locationCoordinates.latitude.toString()
                        longitude[0] = locationCoordinates.longitude.toString()
                    }
                })
                Toast.makeText(applicationContext, latitude[0].toString(), Toast.LENGTH_SHORT).show()
            }

            //Send information to the Weather Details Activity
            intent.putExtra("geoLatitude", latitude[position])
            intent.putExtra("geoLongitude", longitude[position])

            startActivity(intent)
        }

    }

}