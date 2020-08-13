package com.mitocode.mitocine.peliculas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitocode.mitocine.datos.pelicula.PeliculaRepositorio
import com.mitocode.mitocine.peliculas.dominio.casodeuso.ObtenerPeliculasCarteleraCaseDeUso
import com.mitocode.mitocine.peliculas.dominio.casodeuso.ObtenerPeliculasFavoritasCaseDeUso
import com.mitocode.mitocine.peliculas.dominio.casodeuso.ObtenerPeliculasProximasCaseDeUso
import com.mitocode.mitocine.peliculas.dominio.modelo.Pelicula
import kotlinx.coroutines.launch

class PeliculasViewModel(
    private val obtenerPeliculasCarteleraCaseDeUso: ObtenerPeliculasCarteleraCaseDeUso,
    private val obtenerPeliculasProximasCaseDeUso: ObtenerPeliculasProximasCaseDeUso,
    private val obtenerPeliculasFavoritasCaseDeUso: ObtenerPeliculasFavoritasCaseDeUso
) : ViewModel() {

    private val _peliculas: MutableLiveData<List<Pelicula>> = MutableLiveData()
    val peliculas: LiveData<List<Pelicula>>
        get() = _peliculas

    private val _mostrarProgreso = MutableLiveData<Boolean>()
    val mostrarProgreso: LiveData<Boolean>
        get() = _mostrarProgreso

    fun obtenerPeliculasEnCartelera(forzarActualizacion: Boolean) = viewModelScope.launch {
        _mostrarProgreso.value = true

        val listaPeliculas = obtenerPeliculasCarteleraCaseDeUso(forzarActualizacion)

        _peliculas.value = listaPeliculas
        _mostrarProgreso.value = false
    }

    fun obtenerPeliculasProximas(forzarActualizacion: Boolean) = viewModelScope.launch {
        _mostrarProgreso.value = true

        val listaPeliculas = obtenerPeliculasProximasCaseDeUso(forzarActualizacion)

        _peliculas.value = listaPeliculas
        _mostrarProgreso.value = false
    }

    fun obtenerPeliculasFavoritas() = viewModelScope.launch {
        _mostrarProgreso.value = true

        val listaPeliculas = obtenerPeliculasFavoritasCaseDeUso()

        _peliculas.value = listaPeliculas
        _mostrarProgreso.value = false
    }
}