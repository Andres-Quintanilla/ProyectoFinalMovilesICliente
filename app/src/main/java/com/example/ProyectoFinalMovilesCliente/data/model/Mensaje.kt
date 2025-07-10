package com.example.ProyectoFinalMovilesCliente.data.model

data class Mensaje(
    val id: Int,
    val appointment_id: Int,
    val message: String,
    val from_client: Boolean,
    val worker: WorkerInfo?,
    val created_at: String
)
