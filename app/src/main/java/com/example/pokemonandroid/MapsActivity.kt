package com.example.pokemonandroid

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        checkPermission()
        loadPockemons()
    }

    private final val ACCESS_LOCATION = 123 // requestCode

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Requesting For Permission
                // (String[] permissions, int requestCode)
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_LOCATION)
                return
            }
        }

        getUserLocation()
    }

    private fun getUserLocation() {
        Toast.makeText(this, "User Location Access ON", Toast.LENGTH_SHORT).show()

        val myLocation = MyLocationListener()
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // min time - 3 ms, min distance - 3 metres
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3, 3f, myLocation)
        val myThread = myThread()
        // start() method causes this thread to begin execution
        myThread.start()
    }

    // Override Request Permission
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            ACCESS_LOCATION -> {
                // If you have more than one request permission you have to access it by index
                // arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.OTHER)
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getUserLocation()
                } else {
                    Toast.makeText(this,"We cannot able to access your current location", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    var currLoc: Location ?= null
    // Get User Location
    inner class MyLocationListener: LocationListener {

        constructor() {
            currLoc = Location("start")
            currLoc!!.latitude = 0.0
            currLoc!!.longitude = 0.0
        }

        override fun onLocationChanged(location: Location) {
            TODO("Not yet implemented")
            currLoc = location
        }
    }

    private var oldLocation: Location ?= null

    inner class myThread: Thread {

        constructor(): super() {
            oldLocation = Location("start")
            oldLocation!!.latitude = 0.0
            oldLocation!!.longitude = 0.0
        }

        override fun run() {
            while (true) {
                try {

                    if (oldLocation.distanceTo(currLoc) == 0f) {
                        continue
                    }
                    oldLocation = currLoc

                    runOnUiThread {
                        mMap.clear()
                        // Show My Marker
                        val sydney = LatLng(currLoc!!.latitude, currLoc!!.longitude)
                        mMap.addMarker(MarkerOptions()
                                .position(sydney)
                                .title("Me")
                                .snippet("here is my location")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mario)))
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10f))

                        // Show pockemons
                        for (i in listOfPockemons.indices) {
                            val newPockemon = listOfPockemons[i]

                            if (newPockemon.isCatch == false) {
                                val pocLoc = LatLng(newPockemon.lat!!, newPockemon.log!!)
                                mMap.addMarker(MarkerOptions()
                                        .position(pocLoc)
                                        .title(newPockemon.name)
                                        .snippet(newPockemon.des)
                                        .icon(BitmapDescriptorFactory.fromResource(newPockemon.image!!)))
                            }
                        }
                    }
                    // Thread. sleep() method can be used to pause the execution of current thread for specified time in milliseconds.
                    Thread.sleep(1000) // block main thread for 1 second to keep JVM alive
                } catch (ex: Exception) {}
            }
        }
    }

    private val listOfPockemons = ArrayList<Pokemon>()

    private fun loadPockemons() {
        listOfPockemons.add(Pokemon(R.drawable.charmander, "Charmander", "Charmander is from Japan", 55.0, 37.7789994893035, -122.401846647263))
        listOfPockemons.add(Pokemon(R.drawable.bulbasaur, "Bulbasaur", "Bulbasaur is from China", 45.0, 37.7949568502666, -122.410494089127))
        listOfPockemons.add(Pokemon(R.drawable.squirtle, "Squirtle", "Squirtle is from India", 65.0, 37.7816621152613, -122.41225361824))
    }
}