package com.mitocode.mitocine.datos.ajustes

import com.mitocode.mitocine.ajustes.dominio.modelo.NuevoNombreRequest
import com.mitocode.mitocine.ajustes.dominio.modelo.NuevoNombreResponse
import com.mitocode.mitocine.datos.ajustes.source.remote.AjustesRemotoDataSource
import com.mitocode.mitocine.datos.ajustes.source.remote.EstadoNombreResponse

class AjustesRepositorio (
    private val ajustesRemotoDataSource: AjustesRemotoDataSource
) {

    suspend fun cambiarNombre(id: String, token: String, nuevoNombreRequest: NuevoNombreRequest): EstadoNombreResponse {
        val nuevoNombre = ajustesRemotoDataSource.cambiarNombre(id, token, nuevoNombreRequest)
        return nuevoNombre
    }

}