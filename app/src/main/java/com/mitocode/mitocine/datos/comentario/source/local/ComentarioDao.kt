package com.mitocode.mitocine.datos.comentario.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mitocode.mitocine.comentarios.dominio.modelo.Comentario

@Dao
interface ComentarioDao {

    @Query("SELECT * FROM Comentario WHERE peliculaId = :peliculaId ORDER BY creado DESC")
    suspend fun obtenerComentarios(peliculaId: String): List<Comentario>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarComentarios(vararg comentario: Comentario)

    @Query("DELETE FROM Comentario WHERE peliculaId = :peliculaId")
    suspend fun eliminarComentarios(peliculaId: String)
}