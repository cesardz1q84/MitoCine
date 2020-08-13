package com.mitocode.mitocine.comentarios.dominio.casodeuso

import com.mitocode.mitocine.comentarios.dominio.modelo.Comentario
import com.mitocode.mitocine.datos.comentario.ComentarioRepositorio

class ObtenerComentariosCasoDeUso(private val comentarioRepositorio: ComentarioRepositorio) {

    suspend operator fun invoke(peliculaId: String, forzarActualizacion: Boolean): List<Comentario> {
        comentarioRepositorio.comentariosLocalInvalido = forzarActualizacion
        return comentarioRepositorio.obtenerComentarios(peliculaId)
    }
}