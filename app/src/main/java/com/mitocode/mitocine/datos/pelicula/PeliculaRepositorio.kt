package com.mitocode.mitocine.datos.pelicula

import com.mitocode.mitocine.datos.pelicula.source.local.PeliculaLocalDataSource
import com.mitocode.mitocine.datos.pelicula.source.remoto.PeliculaRemotoDataSource
import com.mitocode.mitocine.peliculas.dominio.modelo.Pelicula

class PeliculaRepositorio(
    private val peliculaRemotoDataSource: PeliculaRemotoDataSource,
    private val peliculaLocalDataSource: PeliculaLocalDataSource
) {
    var dataLocalInvalidaCartelera = false
    var dataLocalInvalidaProximas = false

    suspend fun obtenerPeliculasEnCartelera(): List<Pelicula> {
        return if (dataLocalInvalidaCartelera) {
            obtenerPeliculasEnCarteleraRemoto()
        } else {
            val peliculasLocal = peliculaLocalDataSource.obtenerPeliculasEnCartelera()
            if (peliculasLocal.isNullOrEmpty()) {
                obtenerPeliculasEnCarteleraRemoto()
            } else {
                peliculasLocal
            }
        }
    }

    suspend fun obtenerPeliculasProximas(): List<Pelicula> {
        return if (dataLocalInvalidaProximas) {
            obtenerPeliculasProximasRemoto()
        } else {
            val peliculasLocal = peliculaLocalDataSource.obtenerPeliculasProximas()
            if (peliculasLocal.isNullOrEmpty()) {
                obtenerPeliculasProximasRemoto()
            } else {
                peliculasLocal
            }
        }
    }

    suspend fun obtenerPeliculasFavoritas(): List<Pelicula> {
        return peliculaLocalDataSource.obtenerPeliculasFavoritas()
    }

    suspend fun obtenerPeliculaPorId(peliculaId: String): Pelicula {
        return peliculaLocalDataSource.obtenerPeliculaPorId(peliculaId)
    }

    suspend fun actualizarFavorito(peliculaId: String, esFavorito: Boolean) {
        peliculaLocalDataSource.actualizarFavorito(peliculaId, esFavorito)
    }

    private suspend fun obtenerPeliculasEnCarteleraRemoto(): List<Pelicula> {
        val peliculasRemoto = peliculaRemotoDataSource.obtenerPeliculasEnCartelera()

        val peliculasHashMap = HashMap<String, Pelicula>()
        peliculaLocalDataSource.obtenerPeliculasEnCartelera().forEach {
            peliculasHashMap[it.id] = it
        }

        peliculasRemoto.forEach {
            it.favorito = peliculasHashMap[it.id]?.favorito ?:false
        }

        peliculaLocalDataSource.guardarPeliculas(peliculasRemoto)
        dataLocalInvalidaCartelera = false
        return peliculasRemoto
    }

    private suspend fun obtenerPeliculasProximasRemoto(): List<Pelicula> {
        val peliculasRemoto = peliculaRemotoDataSource.obtenerPeliculasProximas()

        val peliculasHashMap = HashMap<String, Pelicula>()
        peliculaLocalDataSource.obtenerPeliculasEnCartelera().forEach {
            peliculasHashMap[it.id] = it
        }

        peliculasRemoto.forEach {
            it.favorito = peliculasHashMap[it.id]?.favorito ?:false
        }

        peliculaLocalDataSource.guardarPeliculas(peliculasRemoto)
        dataLocalInvalidaCartelera = false
        return peliculasRemoto
    }
}