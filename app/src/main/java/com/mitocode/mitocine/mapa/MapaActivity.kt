package com.mitocode.mitocine.mapa

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.mitocode.mitocine.R
import kotlinx.android.synthetic.main.activity_mapa.*
import org.koin.androidx.viewmodel.ext.android.viewModel

const val REQUEST_CODE_PERMISO_POSICION = 100

class MapaActivity : AppCompatActivity() {

    private val mapaViewModel by viewModel<MapaViewModel>()

    private lateinit var googleMap: GoogleMap
    private val locationRequest = LocationRequest().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 5000
        fastestInterval = 3000
        maxWaitTime = 7000
        smallestDisplacement = 10f
        numUpdates = 1
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_guardar -> {
                    setResult(Activity.RESULT_OK, Intent().apply {
                        putExtra("direccion", txtDireccion.text.toString())
                    })
                    finish()
                }
            }
            true
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }

        mapaViewModel.direccion.observe(this, androidx.lifecycle.Observer { direccion ->
            txtDireccion.text = direccion
        })

        mapaViewModel.mostrarProgreso.observe(this, androidx.lifecycle.Observer {
            progress.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    override fun onStart() {
        super.onStart()
        verificarPermisosPosicion()
    }

    override fun onStop() {
        super.onStop()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun mostrarMapa() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map)
        if (mapFragment != null) {
            (mapFragment as SupportMapFragment).getMapAsync { googleMap ->
                this.googleMap = googleMap
                googleMap.uiSettings.apply {
                    isZoomControlsEnabled = true
                    isZoomGesturesEnabled = true
                    isCompassEnabled = true
                    isMapToolbarEnabled = true
                }
            }
        }
    }

    private fun verificarPermisosPosicion() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                Snackbar.make(
                    contenedorMapa,
                    "Se necesita permisos para acceder a la galeria y poder obtener fotos",
                    Snackbar.LENGTH_LONG
                ).setAction("Permitir") {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_CODE_PERMISO_POSICION
                    )
                }.show()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_CODE_PERMISO_POSICION
                )
            }
        } else {
            obtenerPosicion()
        }
    }

    private fun obtenerPosicion() {
        mostrarMapa()

        val locationSettings = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val settingsClient: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> =
            settingsClient.checkLocationSettings(locationSettings.build())
        task.addOnSuccessListener {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
        }
        task.addOnFailureListener {
            if (it is ResolvableApiException) {
                try {
                    it.startResolutionForResult(this, 99)
                } catch (e: IntentSender.SendIntentException) {
                }
            }
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult?.let {
                Log.d(
                    "Mi posicion",
                    "latitud: ${it.lastLocation.latitude} longitud: ${it.lastLocation.longitude}"
                )
                val latlng = LatLng(
                    it.lastLocation.latitude,
                    it.lastLocation.longitude
                )

                googleMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        latlng,
                        18f
                    )
                )
                googleMap.addMarker(
                    MarkerOptions().position(latlng)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_editar_posicion))
                        .draggable(true)
                )
                googleMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
                    override fun onMarkerDragEnd(marker: Marker?) {
                        marker?.position?.apply {
                            obtenerDireccion(latitude, longitude)
                        }
                    }

                    override fun onMarkerDragStart(p0: Marker?) {

                    }

                    override fun onMarkerDrag(p0: Marker?) {

                    }
                })

                obtenerDireccion(latlng.latitude, latlng.longitude)
            }
        }
    }

    private fun obtenerDireccion(lat: Double, lng: Double) {
        mapaViewModel.obtenerDireccion(lng, lat)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_PERMISO_POSICION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    obtenerPosicion()
                }
            }
        }
    }
}