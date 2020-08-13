package com.mitocode.mitocine.registro

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitocode.mitocine.datos.registro.source.remote.EstadoRegistroResponse
import com.mitocode.mitocine.registro.dominio.casodeuso.RegistrarUsuarioCasoDeUso
import com.mitocode.mitocine.registro.dominio.modelo.UsuarioRegistroResponse
import kotlinx.coroutines.launch

class RegistroViewModel(
    private val registrarUsuarioCasoDeUso: RegistrarUsuarioCasoDeUso,
    private val preferences: SharedPreferences
) : ViewModel() {

    private val _nuevoUsuario = MutableLiveData<UsuarioRegistroResponse>()
    val nuevoUsuario: LiveData<UsuarioRegistroResponse>
        get() = _nuevoUsuario


    private val _mostrarProgresoEnvio = MutableLiveData<Boolean>()
    val mostrarProgresoEnvio: LiveData<Boolean>
        get() = _mostrarProgresoEnvio

    private val _mensajeError = MutableLiveData<String>()
    val mensajeError: LiveData<String>
        get() = _mensajeError

    fun registrarUsuario(correo: String, password: String, nombre: String) =
        viewModelScope.launch {
            _mostrarProgresoEnvio.value = true

            val responseRegistro = registrarUsuarioCasoDeUso(correo, password, nombre)

            when (responseRegistro) {
                is EstadoRegistroResponse.Success -> {
                    _nuevoUsuario.value = responseRegistro.data
                    preferences.edit {
                        putString("correo", responseRegistro.data.correo)
                    }
                }
                is EstadoRegistroResponse.HttpErrors.IncorrectData ->
                    _mensajeError.value = responseRegistro.error
                is EstadoRegistroResponse.InvalidData ->
                    _mensajeError.value = "Datos inválidos"
                is EstadoRegistroResponse.Error ->
                    _mensajeError.value = responseRegistro.error
                is EstadoRegistroResponse.NetworkException ->
                    _mensajeError.value = "Error de red"
                is EstadoRegistroResponse.HttpErrors.ConflictData ->
                    _mensajeError.value = responseRegistro.error
            }
            _mostrarProgresoEnvio.value = false
        }

    fun cerrarSesion(correo: String, password: String, nombre: String) =
        viewModelScope.launch {

        }
}