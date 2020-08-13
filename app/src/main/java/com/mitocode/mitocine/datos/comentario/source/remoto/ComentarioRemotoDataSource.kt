package com.mitocode.mitocine.datos.comentario.source.remoto

import com.mitocode.mitocine.comentarios.dominio.modelo.Comentario
import com.mitocode.mitocine.comentarios.dominio.modelo.ComentarioBody
import com.mitocode.mitocine.datos.remoto.MitoCineApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ComentarioRemotoDataSource(private val mitoCineApi: MitoCineApi) {

    suspend fun obtenerComentarios(peliculaId: String): List<Comentario> =
        withContext(Dispatchers.IO) {
            val response = mitoCineApi.obtenerComentarios("peliculaId='$peliculaId'")
            if (response.isSuccessful) {
                return@withContext response.body() ?: emptyList()
            }
            return@withContext emptyList<Comentario>()
        }

    suspend fun guardarComentario(comentario: Comentario): Comentario =
        withContext(Dispatchers.IO) {
            val response = mitoCineApi.guardarComentario(
                ComentarioBody(
                    comentario.usuario,
                    comentario.peliculaId,
                    comentario.detalle
                )
            )
            if (response.isSuccessful) {
                return@withContext response.body() ?: Comentario()
            }
            return@withContext Comentario()
        }
}