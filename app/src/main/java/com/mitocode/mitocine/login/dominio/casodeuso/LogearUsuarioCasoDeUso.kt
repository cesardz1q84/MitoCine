package com.mitocode.mitocine.login.dominio.casodeuso

import com.mitocode.mitocine.datos.login.LoginRepositorio
import com.mitocode.mitocine.datos.login.source.remote.EstadoLoginResponse
import com.mitocode.mitocine.login.dominio.modelo.UsuarioLoginRequest

class LogearUsuarioCasoDeUso(private val loginRepositorio: LoginRepositorio) {

    suspend operator fun invoke(correo: String, password: String): EstadoLoginResponse {
        val usuarioLoginRequest = UsuarioLoginRequest(correo = correo, password = password)
        return loginRepositorio.logearUsuario(usuarioLoginRequest)
    }
}