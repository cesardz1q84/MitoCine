package com.mitocode.mitocine.peliculadetalle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitocode.mitocine.datos.pelicula.PeliculaRepositorio
import com.mitocode.mitocine.peliculadetalle.dominio.casodeuso.ActualizarFavoritoCasoDeUso
import com.mitocode.mitocine.peliculadetalle.dominio.casodeuso.ObtenerPeliculaPorIdCasoDeUso
import com.mitocode.mitocine.peliculas.dominio.modelo.Pelicula
import kotlinx.coroutines.launch

class PeliculaDetalleViewModel(
    private val obtenerPeliculaPorIdCasoDeUso: ObtenerPeliculaPorIdCasoDeUso,
    private val actualizarFavoritoCasoDeUso: ActualizarFavoritoCasoDeUso
) : ViewModel() {

    private val _pelicula = MutableLiveData<Pelicula>()
    val pelicula: LiveData<Pelicula>
        get() = _pelicula

    fun obtenerPeliculaPorId(peliculaId: String) = viewModelScope.launch {
        val peliculaEncontrada = obtenerPeliculaPorIdCasoDeUso(peliculaId)
        _pelicula.value = peliculaEncontrada
    }

    fun actualizarFavorito(peliculaId: String, esFavorito: Boolean) = viewModelScope.launch {
        actualizarFavoritoCasoDeUso(peliculaId, esFavorito)
    }
}