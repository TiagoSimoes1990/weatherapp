package com.tjsimoes.weatherappw

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

interface CoordinatesListener {

    fun onCoordinatesUpdate(locationCoordinates: LocationCoordinates)
}

data class LocationCoordinates(var latitude: Double, var longitude: Double)

class GetLocation(var activity: Activity) {

    private val fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(activity)

    fun coordinates(listener: CoordinatesListener) {

        //Check for permissions and request them if not enabled
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
        }

        fusedLocationProviderClient.lastLocation.addOnSuccessListener(
            activity
        ) { location: Location? ->
            if (location != null) {
                listener.onCoordinatesUpdate(
                    LocationCoordinates(
                        location.latitude,
                        location.longitude
                    )
                )
            }
        }
    }
}