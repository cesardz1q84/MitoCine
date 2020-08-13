package com.mitocode.mitocine.peliculas.dominio.casodeuso

import com.mitocode.mitocine.datos.pelicula.PeliculaRepositorio
import com.mitocode.mitocine.peliculas.dominio.modelo.Pelicula

class ObtenerPeliculasProximasCaseDeUso(private val peliculaRepositorio: PeliculaRepositorio) {

    suspend operator fun invoke(forzarActualizacion: Boolean): List<Pelicula> {
        peliculaRepositorio.dataLocalInvalidaProximas = forzarActualizacion
        return peliculaRepositorio.obtenerPeliculasProximas()
    }
}