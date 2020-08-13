package com.mitocode.mitocine.datos.comentario.source.local

import com.mitocode.mitocine.comentarios.dominio.modelo.Comentario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ComentarioLocalDataSource(private val comentarioDao: ComentarioDao) {

    suspend fun obtenerComentarios(peliculaId: String): List<Comentario> = withContext(Dispatchers.IO) {
        comentarioDao.obtenerComentarios(peliculaId)
    }

    suspend fun guardarComentarios(vararg comentario: Comentario) = withContext(Dispatchers.IO) {
        comentarioDao.guardarComentarios(*comentario)
    }

    suspend fun eliminarComentarios(peliculaId: String) = withContext(Dispatchers.IO) {
        comentarioDao.eliminarComentarios(peliculaId)
    }
}