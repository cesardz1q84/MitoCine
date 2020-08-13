package com.mitocode.mitocine.registro.dominio.casodeuso

import com.mitocode.mitocine.datos.registro.RegistroRepositorio
import com.mitocode.mitocine.datos.registro.source.remote.EstadoRegistroResponse
import com.mitocode.mitocine.registro.dominio.modelo.UsuarioRegistroRequest

class RegistrarUsuarioCasoDeUso(private val registroRepositorio: RegistroRepositorio) {

    suspend operator fun invoke(correo: String, password: String, nombre: String
    ): EstadoRegistroResponse {
        val usuarioRegistroRequest =
            UsuarioRegistroRequest(correo = correo, password = password, nombre = nombre)
        return registroRepositorio.registrarUsuario(usuarioRegistroRequest)
    }
}