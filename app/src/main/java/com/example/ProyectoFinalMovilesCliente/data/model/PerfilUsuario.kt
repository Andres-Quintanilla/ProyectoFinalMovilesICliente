package com.example.ProyectoFinalMovilesCliente.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PerfilUsuario(
    val id: Int,
    val name: String,
    val last_name: String,
    val type: Int
) : Parcelable
