package com.mitocode.mitocine.registro.dominio.modelo

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class UsuarioRegistroResponse(
    @PrimaryKey @SerializedName("objectId") val id: String = "",
    @SerializedName("email") val correo: String = "",
    @SerializedName("password") val password: String = "",
    @SerializedName("name") val nombre: String = ""
)

data class UsuarioRegistroRequest(
    @SerializedName("email") val correo: String = "",
    @SerializedName("password") val password: String = "",
    @SerializedName("name") val nombre: String = ""
)
