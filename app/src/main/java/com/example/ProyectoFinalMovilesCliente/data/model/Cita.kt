package com.example.ProyectoFinalMovilesCliente.data.model

data class Cita(
    val id: Int,
    val worker_id: Int,
    val user_id: Int,
    val category_selected_id: Int,
    val status: String
)
