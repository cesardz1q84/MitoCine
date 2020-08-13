package com.mitocode.mitocine.peliculas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mitocode.mitocine.peliculas.dominio.casodeuso.ObtenerPeliculasCarteleraCaseDeUso
import com.mitocode.mitocine.peliculas.dominio.casodeuso.ObtenerPeliculasFavoritasCaseDeUso
import com.mitocode.mitocine.peliculas.dominio.casodeuso.ObtenerPeliculasProximasCaseDeUso

class PeliculasViewModelFactory(
    private val obtenerPeliculasCarteleraCaseDeUso: ObtenerPeliculasCarteleraCaseDeUso,
    private val obtenerPeliculasProximasCaseDeUso: ObtenerPeliculasProximasCaseDeUso,
    private val obtenerPeliculasFavoritasCaseDeUso: ObtenerPeliculasFavoritasCaseDeUso
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass != PeliculasViewModel::class.java) {
            throw IllegalArgumentException("Clase ViewModel desconocida")
        }
        return PeliculasViewModel(
            obtenerPeliculasCarteleraCaseDeUso,
            obtenerPeliculasProximasCaseDeUso,
            obtenerPeliculasFavoritasCaseDeUso
        ) as T
    }
}