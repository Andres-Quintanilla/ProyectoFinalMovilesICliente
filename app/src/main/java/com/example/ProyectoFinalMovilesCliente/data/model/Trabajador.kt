package com.example.ProyectoFinalMovilesCliente.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Trabajador(
    val id: Int,
    val picture_url: String?,
    val average_rating: String?,
    val reviews_count: Int,
    val user: UsuarioSimple,
    val categories: List<CategoriaSimple>?,
    val reviews: List<Resena>?
) : Parcelable
