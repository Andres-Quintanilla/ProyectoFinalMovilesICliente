package com.example.ProyectoFinalMovilesCliente.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UsuarioSimple(
    val id: Int,
    val name: String,
    val email: String,
    val profile: PerfilUsuario? = null
): Parcelable