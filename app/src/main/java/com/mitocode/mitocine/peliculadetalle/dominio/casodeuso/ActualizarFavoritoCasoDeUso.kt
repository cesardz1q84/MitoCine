package com.mitocode.mitocine.peliculadetalle.dominio.casodeuso

import com.mitocode.mitocine.datos.pelicula.PeliculaRepositorio

class ActualizarFavoritoCasoDeUso(private val peliculaRepositorio: PeliculaRepositorio) {

    suspend operator fun invoke(peliculaId: String, esFavorito: Boolean) =
        peliculaRepositorio.actualizarFavorito(peliculaId, esFavorito)
}