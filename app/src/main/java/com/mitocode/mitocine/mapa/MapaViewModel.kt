package com.mitocode.mitocine.mapa

import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MapaViewModel(private val geocoder: Geocoder) : ViewModel() {

    private val _direccion = MutableLiveData<String>()
    val direccion: LiveData<String>
        get() = _direccion

    private val _mostrarProgreso = MutableLiveData<Boolean>()
    val mostrarProgreso: LiveData<Boolean>
        get() = _mostrarProgreso

    fun obtenerDireccion(longitud: Double, latitud: Double) = viewModelScope.launch {
        _mostrarProgreso.value = true

        val direcciones= geocoder.getFromLocation(latitud, longitud, 1)
        val direccion = direcciones[0]
        val linea1 = direccion.getAddressLine(0)

        _direccion.value = linea1
        _mostrarProgreso.value = false
    }
}