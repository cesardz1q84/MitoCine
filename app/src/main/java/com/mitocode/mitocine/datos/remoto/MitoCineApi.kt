package com.mitocode.mitocine.datos.remoto

import com.mitocode.mitocine.ajustes.dominio.modelo.NuevoNombreResponse
import com.mitocode.mitocine.ajustes.dominio.modelo.NuevoNombreRequest
import com.mitocode.mitocine.comentarios.dominio.modelo.Comentario
import com.mitocode.mitocine.comentarios.dominio.modelo.ComentarioBody
import com.mitocode.mitocine.login.dominio.modelo.UsuarioLoginResponse
import com.mitocode.mitocine.login.dominio.modelo.UsuarioLoginRequest
import com.mitocode.mitocine.peliculas.dominio.modelo.Pelicula
import com.mitocode.mitocine.registro.dominio.modelo.UsuarioRegistroResponse
import com.mitocode.mitocine.registro.dominio.modelo.UsuarioRegistroRequest
import retrofit2.Response
import retrofit2.http.*

interface MitoCineApi {
    companion object {
        const val URL_BASE =
            "https://api.backendless.com/ACE797F5-67DB-A92F-FFCF-1C52D55B7500/B3C30BCD-F2B7-9ED6-FF64-B99EA5D1C400/"
    }

    @GET("data/peliculas?sortBy=fechaEstreno%20desc")
    suspend fun obtenerPeliculas(@Query("where") fechaActual: String): Response<List<Pelicula>>

    @GET("data/comentarios?sortBy=created%20desc")
    suspend fun obtenerComentarios(@Query("where") peliculaId: String): Response<List<Comentario>>

    @POST("data/comentarios")
    suspend fun guardarComentario(@Body comentario: ComentarioBody): Response<Comentario>

    @POST("users/register")
    suspend fun registrarUsuario(@Body usuario: UsuarioRegistroRequest): Response<UsuarioRegistroResponse>

    @POST("users/login")
    suspend fun logearUsuario(@Body login: UsuarioLoginRequest): Response<UsuarioLoginResponse>

    @PUT("users/{id}")
    suspend fun cambiarNombre(
        @Path("id") userId: String,
        @Header("user-token") token: String,
        @Body nuevoNombre: NuevoNombreRequest
    ): Response<NuevoNombreResponse>
}