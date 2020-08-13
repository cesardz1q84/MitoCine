package com.mitocode.mitocine.datos.pelicula.source.local

import com.mitocode.mitocine.peliculas.dominio.modelo.Pelicula
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PeliculaLocalDataSource(private val peliculaDao: PeliculaDao) {

    suspend fun obtenerPeliculasEnCartelera(): List<Pelicula> = withContext(Dispatchers.IO) {
        return@withContext peliculaDao.obtenerPeliculasEnCartelera()
    }

    suspend fun obtenerPeliculasProximas(): List<Pelicula> = withContext(Dispatchers.IO) {
        return@withContext peliculaDao.obtenerPeliculasProximas()
    }

    suspend fun obtenerPeliculasFavoritas(): List<Pelicula> = withContext(Dispatchers.IO) {
        return@withContext peliculaDao.obtenerPeliculasFavoritas()
    }

    suspend fun obtenerPeliculaPorId(peliculaId: String): Pelicula = withContext(Dispatchers.IO) {
        peliculaDao.obtenerPeliculaPorId(peliculaId)
    }

    suspend fun guardarPeliculas(peliculas: List<Pelicula>) = withContext(Dispatchers.IO) {
        peliculaDao.guardarPeliculas(peliculas)
    }

    suspend fun actualizarFavorito(peliculaId: String, esFavorito: Boolean) = withContext(Dispatchers.IO) {
        peliculaDao.actualizarFavorito(peliculaId, esFavorito)
    }
}