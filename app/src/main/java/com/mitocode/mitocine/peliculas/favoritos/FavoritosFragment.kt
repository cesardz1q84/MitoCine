package com.mitocode.mitocine.peliculas.favoritos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mitocode.mitocine.R
import com.mitocode.mitocine.peliculas.ListaPeliculasFragment

class FavoritosFragment: ListaPeliculasFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favoritos, container, false)
    }

    override fun getPeliculas(forzarActualizacion: Boolean) {
        peliculasViewModel.obtenerPeliculasFavoritas()
    }
}