package com.mitocode.mitocine.peliculas.cartelera

import com.mitocode.mitocine.peliculas.ListaPeliculasFragment

class PeliculasCarteleraFragment : ListaPeliculasFragment() {

    override fun getPeliculas(forzarActualizacion: Boolean) {
        peliculasViewModel.obtenerPeliculasEnCartelera(forzarActualizacion)
    }
}