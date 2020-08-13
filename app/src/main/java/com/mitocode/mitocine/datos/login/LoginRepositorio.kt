package com.mitocode.mitocine.datos.login

import com.mitocode.mitocine.datos.login.source.remote.EstadoLoginResponse
import com.mitocode.mitocine.datos.login.source.remote.LoginRemotoDataSource
import com.mitocode.mitocine.login.dominio.modelo.UsuarioLoginRequest

class LoginRepositorio (
    private val loginRemotoDataSource: LoginRemotoDataSource
) {

    suspend fun logearUsuario(usuarioLoginRequest: UsuarioLoginRequest): EstadoLoginResponse {
        val nuevoLogin = loginRemotoDataSource.logearUsuario(usuarioLoginRequest)
        return nuevoLogin
    }

}