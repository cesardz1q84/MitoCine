package com.mitocode.mitocine.datos.pelicula.source.local

import androidx.room.*
import com.mitocode.mitocine.peliculas.dominio.modelo.Pelicula

@Dao
interface PeliculaDao {

    @Query("SELECT * FROM Pelicula WHERE fechaEstreno <= strftime('%s', 'now')*1000 ORDER BY fechaEstreno DESC")
    suspend fun obtenerPeliculasEnCartelera(): List<Pelicula>

    @Query("SELECT * FROM Pelicula WHERE fechaEstreno > strftime('%s', 'now')*1000 ORDER BY fechaEstreno DESC")
    suspend fun obtenerPeliculasProximas(): List<Pelicula>

    @Query("SELECT * FROM Pelicula WHERE favorito = 1 ORDER BY fechaEstreno DESC")
    suspend fun obtenerPeliculasFavoritas(): List<Pelicula>

    @Query("SELECT * FROM Pelicula WHERE id = :peliculaId")
    suspend fun obtenerPeliculaPorId(peliculaId: String): Pelicula

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarPeliculas(peliculas: List<Pelicula>)

//    @Update
//    suspend fun actualizarPelicula(pelicula: Pelicula)

    @Query("UPDATE Pelicula SET favorito = :esFavorito WHERE id = :peliculaId")
    suspend fun actualizarFavorito(peliculaId: String, esFavorito: Boolean)
}