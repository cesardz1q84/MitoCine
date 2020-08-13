package com.mitocode.mitocine.datos.login.source.remote

import com.mitocode.mitocine.login.dominio.modelo.UsuarioLoginResponse

sealed class EstadoLoginResponse {
    data class Success(val data : UsuarioLoginResponse) : EstadoLoginResponse()
    object InvalidData : EstadoLoginResponse()
    data class Error(val error : String) : EstadoLoginResponse()
    data class NetworkException(val error : String) : EstadoLoginResponse()
    sealed class HttpErrors : EstadoLoginResponse() {
        data class IncorrectData(val error: String) : HttpErrors()
    }
}