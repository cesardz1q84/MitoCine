package com.mitocode.mitocine.comentarios.dominio.modelo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.mitocode.mitocine.peliculas.dominio.modelo.Pelicula

@Entity(
    foreignKeys = [ForeignKey(
        entity = Pelicula::class,
        parentColumns = ["id"],
        childColumns = ["peliculaId"]
    )]
)
data class Comentario(
    @PrimaryKey @SerializedName("objectId") val id: String = "",
    val usuario: String = "",
    val peliculaId: String = "",
    val detalle: String = "",
    @SerializedName("created") val creado: Long = 0
)

data class ComentarioBody(
    val usuario: String = "",
    val peliculaId: String = "",
    val detalle: String = ""
)