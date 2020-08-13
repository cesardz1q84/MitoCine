package com.mitocode.mitocine.comentarios

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitocode.mitocine.comentarios.dominio.casodeuso.EnviarComentarioCasoDeUso
import com.mitocode.mitocine.comentarios.dominio.casodeuso.ObtenerComentariosCasoDeUso
import com.mitocode.mitocine.comentarios.dominio.modelo.Comentario
import kotlinx.coroutines.launch

class ComentariosViewModel(
    private val obtenerComentariosCasoDeUso: ObtenerComentariosCasoDeUso,
    private val enviarComentarioCasoDeUso: EnviarComentarioCasoDeUso,
    private val preferences: SharedPreferences
) : ViewModel() {

    private val _comentarios = MutableLiveData<List<Comentario>>()
    val comentarios: LiveData<List<Comentario>>
        get() = _comentarios

    private val _nuevoComentario = MutableLiveData<Comentario>()
    val nuevoComentario: LiveData<Comentario>
        get() = _nuevoComentario

    private val _mostrarProgreso = MutableLiveData<Boolean>()
    val mostrarProgreso: LiveData<Boolean>
        get() = _mostrarProgreso

    private val _mostrarProgresoEnvio = MutableLiveData<Boolean>()
    val mostrarProgresoEnvio: LiveData<Boolean>
        get() = _mostrarProgresoEnvio


    fun obtenerComentarios(peliculaId: String, forzarActualizacion: Boolean) =
        viewModelScope.launch {
            _mostrarProgreso.value = true

            val comentarios = obtenerComentariosCasoDeUso(peliculaId, forzarActualizacion)

            _comentarios.value = comentarios
            _mostrarProgreso.value = false
        }

    fun enviarComentario(detalle: String, peliculaId: String) =
        viewModelScope.launch {
            _mostrarProgresoEnvio.value = true

            val usuario = preferences.getString("nombreUsuario", "Anónimo")
            val nuevoComentario = enviarComentarioCasoDeUso(usuario!!, detalle, peliculaId)
            preferences.edit {
                putString("nombreUsuario", nuevoComentario.usuario)
            }

            _nuevoComentario.value = nuevoComentario
            _mostrarProgresoEnvio.value = false
        }
}