package com.mitocode.mitocine.datos.pelicula.source.remoto

import com.mitocode.mitocine.datos.remoto.MitoCineApi
import com.mitocode.mitocine.peliculas.dominio.modelo.Pelicula
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class PeliculaRemotoDataSource(private val mitoCineApi: MitoCineApi) {

    suspend fun obtenerPeliculasEnCartelera(): List<Pelicula> = withContext(Dispatchers.IO) {
        val fechaActual = Calendar.getInstance().timeInMillis

        val response = mitoCineApi.obtenerPeliculas("fechaEstreno<=$fechaActual") // peliculas en cartelera

        if(response.isSuccessful) {
            return@withContext response.body() ?: emptyList<Pelicula>()
        }
        return@withContext emptyList<Pelicula>()
    }

    suspend fun obtenerPeliculasProximas(): List<Pelicula> = withContext(Dispatchers.IO) {
        val fechaActual = Calendar.getInstance().timeInMillis

        val response = mitoCineApi.obtenerPeliculas("fechaEstreno>$fechaActual") // peliculas en cartelera

        if(response.isSuccessful) {
            return@withContext response.body() ?: emptyList<Pelicula>()
        }
        return@withContext emptyList<Pelicula>()
    }
}