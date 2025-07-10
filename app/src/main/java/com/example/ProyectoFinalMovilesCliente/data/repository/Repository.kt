package com.example.ProyectoFinalMovilesCliente.data.repository

import android.content.Context
import com.example.ProyectoFinalMovilesCliente.data.model.Categoria
import com.example.ProyectoFinalMovilesCliente.data.model.Trabajador
import com.example.ProyectoFinalMovilesCliente.data.network.InstanciaRetrofit
import com.example.ProyectoFinalMovilesCliente.util.GestorToken
import retrofit2.Call
import retrofit2.Response

class Repository(private val context: Context) {
    private val api = InstanciaRetrofit.getInstance(context)
    private val gestorToken = GestorToken(context)

    suspend fun registro(nombre: String, apellido: String, email: String, password: String) =
        api.registrarCliente(mapOf("name" to nombre, "lastName" to apellido, "email" to email, "password" to password))

    suspend fun login(email: String, password: String) =
        api.loginCliente(mapOf("email" to email, "password" to password))

    suspend fun obtenerCategorias(): Response<List<Categoria>> =
        api.getCategorias()

    fun obtenerTrabajadoresPorCategoria(idCategoria: Int): Call<List<Trabajador>> =
        api.obtenerTrabajadoresPorCategoria(idCategoria)

    fun obtenerTrabajadorDetalle(id: Int): Call<Trabajador> {
        return api.getTrabajadorDetalle(id)
    }

    fun crearCita(workerId: Int, categoryId: Int): Call<Unit> {
        val body = mapOf(
            "worker_id" to workerId,
            "category_selected_id" to categoryId
        )
        return api.crearCita(body)
    }

    fun guardarToken(token: String) = gestorToken.guardarToken(token)
    fun obtenerToken() = gestorToken.obtenerToken()
    fun borrarToken() = gestorToken.borrarToken()
}