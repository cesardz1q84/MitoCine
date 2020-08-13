package com.mitocode.mitocine.datos.registro.source.remote

import com.mitocode.mitocine.registro.dominio.modelo.UsuarioRegistroResponse

sealed class EstadoRegistroResponse {
    data class Success(val data: UsuarioRegistroResponse) : EstadoRegistroResponse()
    object InvalidData : EstadoRegistroResponse()
    data class Error(val error: String) : EstadoRegistroResponse()
    data class NetworkException(val error: String) : EstadoRegistroResponse()
    sealed class HttpErrors : EstadoRegistroResponse() {
        data class IncorrectData(val error: String) : HttpErrors()
        data class ConflictData(val error: String) : HttpErrors()
    }
}