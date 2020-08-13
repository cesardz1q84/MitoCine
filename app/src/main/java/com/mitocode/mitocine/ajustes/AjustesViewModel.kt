package com.mitocode.mitocine.ajustes

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitocode.mitocine.ajustes.dominio.casodeuso.CambiarNombreCasoDeUso
import com.mitocode.mitocine.ajustes.dominio.modelo.NuevoNombreResponse
import com.mitocode.mitocine.datos.ajustes.source.remote.EstadoNombreResponse
import kotlinx.coroutines.launch

class AjustesViewModel(
    private val cambiarNombreCasoDeUso: CambiarNombreCasoDeUso,
    private val preferences: SharedPreferences
) : ViewModel() {

    private val _nuevoNombre = MutableLiveData<NuevoNombreResponse>()
    val nuevoNombre: LiveData<NuevoNombreResponse>
        get() = _nuevoNombre


    private val _mostrarProgresoEnvio = MutableLiveData<Boolean>()
    val mostrarProgresoEnvio: LiveData<Boolean>
        get() = _mostrarProgresoEnvio

    private val _mensajeError = MutableLiveData<String>()
    val mensajeError: LiveData<String>
        get() = _mensajeError

    private val _estadoLogin = MutableLiveData<Boolean>()
    val estadoLogin: LiveData<Boolean>
        get() = _estadoLogin


    fun cambiarNombre(id: String, token: String, nombre: String) =
        viewModelScope.launch {
            _mostrarProgresoEnvio.value = true

            val nuevoNombre = cambiarNombreCasoDeUso(id, token, nombre)

            when (nuevoNombre) {
                is EstadoNombreResponse.Success -> {
                    _nuevoNombre.value = nuevoNombre.data
                    preferences.edit {
                        putString("nombreUsuario", nuevoNombre.data.nombre)
                    }
                }
                is EstadoNombreResponse.InvalidData ->
                    _mensajeError.value = "Datos inválidos"
                is EstadoNombreResponse.Error ->
                    _mensajeError.value = nuevoNombre.error
                is EstadoNombreResponse.NetworkException ->
                    _mensajeError.value = "Error de red"
            }
            _mostrarProgresoEnvio.value = false
        }

    fun cerrarSesion() =
        viewModelScope.launch {
            _mostrarProgresoEnvio.value = true
            preferences.edit {
                putString("nombreUsuario", "")
                putString("token", "")
                putString("id", "")
                putString("correo", "")
                putBoolean("login", false)
            }
            _estadoLogin.value = false
            _mostrarProgresoEnvio.value = false

        }

}