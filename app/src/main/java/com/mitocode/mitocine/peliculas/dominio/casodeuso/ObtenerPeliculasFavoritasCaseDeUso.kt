package com.mitocode.mitocine.peliculas.dominio.casodeuso

import com.mitocode.mitocine.datos.pelicula.PeliculaRepositorio
import com.mitocode.mitocine.peliculas.dominio.modelo.Pelicula

class ObtenerPeliculasFavoritasCaseDeUso(private val peliculaRepositorio: PeliculaRepositorio) {

    suspend operator fun invoke() = peliculaRepositorio.obtenerPeliculasFavoritas()
}