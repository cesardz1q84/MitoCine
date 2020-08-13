package com.mitocode.mitocine.ajustes.dominio.casodeuso

import com.mitocode.mitocine.ajustes.dominio.modelo.NuevoNombreResponse
import com.mitocode.mitocine.ajustes.dominio.modelo.NuevoNombreRequest
import com.mitocode.mitocine.datos.ajustes.AjustesRepositorio
import com.mitocode.mitocine.datos.ajustes.source.remote.EstadoNombreResponse

class CambiarNombreCasoDeUso(private val ajustesRepositorio: AjustesRepositorio) {

    suspend operator fun invoke(id: String, token: String, nombre: String): EstadoNombreResponse {
        val nuevoNombre = NuevoNombreRequest(nombreUsuario = nombre)
        return ajustesRepositorio.cambiarNombre(id, token, nuevoNombre)
    }
}