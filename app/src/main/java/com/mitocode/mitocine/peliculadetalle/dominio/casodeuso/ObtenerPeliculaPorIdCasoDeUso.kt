package com.mitocode.mitocine.peliculadetalle.dominio.casodeuso

import com.mitocode.mitocine.datos.pelicula.PeliculaRepositorio

class ObtenerPeliculaPorIdCasoDeUso(private val peliculaRepositorio: PeliculaRepositorio) {

    suspend operator fun invoke(peliculaId: String) = peliculaRepositorio.obtenerPeliculaPorId(peliculaId)
}