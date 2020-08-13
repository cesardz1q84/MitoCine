package com.mitocode.mitocine.comentarios.dominio.casodeuso

import com.mitocode.mitocine.comentarios.dominio.modelo.Comentario
import com.mitocode.mitocine.datos.comentario.ComentarioRepositorio

class EnviarComentarioCasoDeUso(private val comentarioRepositorio: ComentarioRepositorio) {

    suspend operator fun invoke(usuario: String, detalle: String, peliculaId: String): Comentario {
        val comentario = Comentario(usuario = usuario, detalle = detalle, peliculaId = peliculaId)
        return comentarioRepositorio.guardarComentario(comentario)
    }
}