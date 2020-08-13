package com.mitocode.mitocine.peliculas.cartelera

import com.mitocode.mitocine.peliculas.ListaPeliculasFragment

class PeliculasProximamenteFragment : ListaPeliculasFragment() {

    override fun getPeliculas(forzarActualizacion: Boolean) {
        peliculasViewModel.obtenerPeliculasProximas(forzarActualizacion)
    }
}