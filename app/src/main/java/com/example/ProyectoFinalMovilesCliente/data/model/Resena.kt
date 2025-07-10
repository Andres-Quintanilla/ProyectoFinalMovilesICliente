package com.example.ProyectoFinalMovilesCliente.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Resena(
    val id: Int,
    val worker_id: Int,
    val user_id: Int,
    val appointment_id: Int,
    val rating: Int,
    val comment: String?,
    val is_done: Int,
    val user: UsuarioCliente?
): Parcelable
