package com.mitocode.mitocine.datos.ajustes.source.remote

import com.mitocode.mitocine.ajustes.dominio.modelo.NuevoNombreRequest
import com.mitocode.mitocine.datos.remoto.MitoCineApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class AjustesRemotoDataSource(private val mitoCineApi: MitoCineApi) {

    suspend fun cambiarNombre(
        id: String,
        token: String,
        nuevoNombreRequest: NuevoNombreRequest
    ): EstadoNombreResponse =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val response = mitoCineApi.cambiarNombre(
                    id,
                    token,
                    nuevoNombreRequest
                )
                if (response.isSuccessful) {
                    if (response != null) {
                        EstadoNombreResponse.Success(response.body()!!)
                    } else {
                        EstadoNombreResponse.InvalidData
                    }
                } else {
                    when (response.code()) {
                        else -> EstadoNombreResponse.Error(response.message())
                    }
                }
            } catch (e: IOException) {
                EstadoNombreResponse.NetworkException(e.message!!)
            }
        }
}