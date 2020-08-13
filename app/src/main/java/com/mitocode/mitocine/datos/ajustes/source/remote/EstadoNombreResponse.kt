package com.mitocode.mitocine.datos.ajustes.source.remote

import com.mitocode.mitocine.ajustes.dominio.modelo.NuevoNombreResponse

sealed class EstadoNombreResponse {
    data class Success(val data: NuevoNombreResponse) : EstadoNombreResponse()
    object InvalidData : EstadoNombreResponse()
    data class Error(val error: String) : EstadoNombreResponse()
    data class NetworkException(val error: String) : EstadoNombreResponse()
    sealed class HttpErrors : EstadoNombreResponse() {
    }
}