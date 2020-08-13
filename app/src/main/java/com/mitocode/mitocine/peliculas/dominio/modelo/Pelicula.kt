package com.mitocode.mitocine.peliculas.dominio.modelo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Pelicula(
    @PrimaryKey @SerializedName("objectId") val id: String,
    @SerializedName("nombre") val titulo: String,
    val sinopsis: String,
    val imagenUrl: String,
    val fechaEstreno: Long,
    val duracion: Int,
    var favorito: Boolean
) : Parcelable