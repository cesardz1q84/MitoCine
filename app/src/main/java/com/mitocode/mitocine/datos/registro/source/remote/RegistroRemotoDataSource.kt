package com.mitocode.mitocine.datos.registro.source.remote

import com.mitocode.mitocine.datos.remoto.MitoCineApi
import com.mitocode.mitocine.registro.dominio.modelo.UsuarioRegistroRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class RegistroRemotoDataSource(private val mitoCineApi: MitoCineApi) {

    suspend fun registrarUsuario(usuarioRegistroRequest: UsuarioRegistroRequest): EstadoRegistroResponse =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val response = mitoCineApi.registrarUsuario(
                    UsuarioRegistroRequest(
                        usuarioRegistroRequest.correo,
                        usuarioRegistroRequest.password,
                        usuarioRegistroRequest.nombre
                    )
                )
                if (response.isSuccessful) {
                    if (response != null) {
                        EstadoRegistroResponse.Success(response.body()!!)
                    } else {
                        EstadoRegistroResponse.InvalidData
                    }
                } else {
                    when (response.code()) {
                        401 -> EstadoRegistroResponse.HttpErrors.IncorrectData("Datos incorrectos")
                        400 -> EstadoRegistroResponse.InvalidData
                        409 -> EstadoRegistroResponse.HttpErrors.ConflictData("Este correo ya se encuentra registrado")
                        else -> EstadoRegistroResponse.Error(response.message())
                    }
                }
            } catch (e: IOException) {
                EstadoRegistroResponse.NetworkException(e.message!!)
            }
        }
}