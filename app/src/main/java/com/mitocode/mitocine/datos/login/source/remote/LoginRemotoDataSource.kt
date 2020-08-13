package com.mitocode.mitocine.datos.login.source.remote

import com.mitocode.mitocine.datos.remoto.MitoCineApi
import com.mitocode.mitocine.login.dominio.modelo.UsuarioLoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class LoginRemotoDataSource(private val mitoCineApi: MitoCineApi) {

    suspend fun logearUsuario(usuarioLoginRequest: UsuarioLoginRequest): EstadoLoginResponse =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val response = mitoCineApi.logearUsuario(
                    UsuarioLoginRequest(
                        usuarioLoginRequest.correo,
                        usuarioLoginRequest.password
                    )
                )
                if (response.isSuccessful) {
                    if (response != null) {
                        EstadoLoginResponse.Success(response.body()!!)
                    } else {
                        EstadoLoginResponse.InvalidData
                    }
                } else {
                    when (response.code()) {
                        401 -> EstadoLoginResponse.HttpErrors.IncorrectData("Datos incorrectos")
                        else -> EstadoLoginResponse.Error(response.message())
                    }
                }
            } catch (e: IOException) {
                EstadoLoginResponse.NetworkException(e.message!!)
            }
        }
}