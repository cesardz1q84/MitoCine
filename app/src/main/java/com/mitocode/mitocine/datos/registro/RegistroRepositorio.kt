package com.mitocode.mitocine.datos.registro

import com.mitocode.mitocine.datos.registro.source.remote.EstadoRegistroResponse
import com.mitocode.mitocine.datos.registro.source.remote.RegistroRemotoDataSource
import com.mitocode.mitocine.registro.dominio.modelo.UsuarioRegistroRequest

class RegistroRepositorio (
    private val registroRemoteDataSource: RegistroRemotoDataSource
) {

    suspend fun registrarUsuario(usuarioRegistroRequest: UsuarioRegistroRequest): EstadoRegistroResponse {
        val nuevoUsario = registroRemoteDataSource.registrarUsuario(usuarioRegistroRequest)
        return nuevoUsario
    }

}