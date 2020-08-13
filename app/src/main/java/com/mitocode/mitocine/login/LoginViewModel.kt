package com.mitocode.mitocine.login

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitocode.mitocine.datos.login.source.remote.EstadoLoginResponse
import com.mitocode.mitocine.login.dominio.casodeuso.LogearUsuarioCasoDeUso
import com.mitocode.mitocine.login.dominio.modelo.UsuarioLoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(
    private val logearUsuarioCasoDeUso: LogearUsuarioCasoDeUso,
    private val preferences: SharedPreferences
) : ViewModel() {

    private val _nuevoLogin = MutableLiveData<UsuarioLoginResponse>()
    val nuevoLogin: LiveData<UsuarioLoginResponse>
        get() = _nuevoLogin

    private val _mostrarProgresoEnvio = MutableLiveData<Boolean>()
    val mostrarProgresoEnvio: LiveData<Boolean>
        get() = _mostrarProgresoEnvio

    private val _mensajeError = MutableLiveData<String>()
    val mensajeError: LiveData<String>
        get() = _mensajeError

    fun logearUsuario(correo: String, password: String) =
        viewModelScope.launch {
            _mostrarProgresoEnvio.value = true

            val responseLogin = logearUsuarioCasoDeUso(correo, password)

            when (responseLogin) {
                is EstadoLoginResponse.Success -> {
                    preferences.edit {
                        putString("nombreUsuario", responseLogin.data.nombre)
                        putString("token", responseLogin.data.token)
                        putString("id", responseLogin.data.id)
                        putString("correo", responseLogin.data.correo)
                        putBoolean("login", true)
                    }
                    _nuevoLogin.value = responseLogin.data
                }
                is EstadoLoginResponse.HttpErrors.IncorrectData ->
                    _mensajeError.value = responseLogin.error
                is EstadoLoginResponse.InvalidData ->
                    _mensajeError.value = "Datos inválidos"
                is EstadoLoginResponse.Error ->
                    _mensajeError.value = responseLogin.error
                is EstadoLoginResponse.NetworkException ->
                    _mensajeError.value = "Error de red"
            }
            _mostrarProgresoEnvio.value = false
        }


}


