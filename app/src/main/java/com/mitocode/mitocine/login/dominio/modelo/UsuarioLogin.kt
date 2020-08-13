package com.mitocode.mitocine.login.dominio.modelo

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class UsuarioLoginResponse(
    @PrimaryKey @SerializedName("objectId") val id: String = "",
    @SerializedName("user-token") val token: String = "",
    @SerializedName("email") val correo: String = "",
    @SerializedName("name") val nombre: String = ""
)

data class UsuarioLoginRequest(
    @SerializedName("login") val correo: String = "",
    @SerializedName("password") val password: String = ""
)
