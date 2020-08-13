package com.mitocode.mitocine.datos.comentario

import com.mitocode.mitocine.comentarios.dominio.modelo.Comentario
import com.mitocode.mitocine.datos.comentario.source.local.ComentarioLocalDataSource
import com.mitocode.mitocine.datos.comentario.source.remoto.ComentarioRemotoDataSource

class ComentarioRepositorio(
    private val comentarioLocalDataSource: ComentarioLocalDataSource,
    private val comentarioRemotoDataSource: ComentarioRemotoDataSource
) {

    var comentariosLocalInvalido = false

    suspend fun obtenerComentarios(peliculaId: String) : List<Comentario> {
        return if(comentariosLocalInvalido) {
            obtenerComentarioRemoto(peliculaId)
        } else {
            val comentariosLocal = comentarioLocalDataSource.obtenerComentarios(peliculaId)
            if (comentariosLocal.isNullOrEmpty()) {
                obtenerComentarioRemoto(peliculaId)
            } else {
                comentariosLocal
            }
        }
    }

    suspend fun guardarComentario(comentario: Comentario): Comentario {
        val nuevoComentario = comentarioRemotoDataSource.guardarComentario(comentario)
        comentarioLocalDataSource.guardarComentarios(nuevoComentario)
        return nuevoComentario
    }

    private suspend fun obtenerComentarioRemoto(peliculaId: String): List<Comentario> {
        val comentariosRemoto = comentarioRemotoDataSource.obtenerComentarios(peliculaId)
        comentarioLocalDataSource.eliminarComentarios(peliculaId)
        comentarioLocalDataSource.guardarComentarios(*comentariosRemoto.toTypedArray())
        comentariosLocalInvalido = false
        return comentariosRemoto
    }
}