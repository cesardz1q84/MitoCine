package com.mitocode.mitocine.ajustes.dominio.modelo

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class NuevoNombreResponse(
    @PrimaryKey @SerializedName("objectId") val id: String = "",
    @SerializedName("email")val correo: String = "",
    @SerializedName("password")val password: String = "",
    @SerializedName("name")val nombre: String = ""
)

data class NuevoNombreRequest(
    @SerializedName("name")val nombreUsuario: String = ""
)
