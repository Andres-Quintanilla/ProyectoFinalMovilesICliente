package com.example.ProyectoFinalMovilesCliente.data.repository

import android.content.Context
import com.example.ProyectoFinalMovilesCliente.data.model.Categoria
import com.example.ProyectoFinalMovilesCliente.data.model.Cita
import com.example.ProyectoFinalMovilesCliente.data.model.CitaRequest
import com.example.ProyectoFinalMovilesCliente.data.model.Mensaje
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

    fun crearCita(workerId: Int, categoryId: Int): Call<Cita> {
        val body = CitaRequest(workerId, categoryId)
        return api.crearCita(body)
    }


    suspend fun getMensajes(id: Int, onSuccess: (List<Mensaje>) -> Unit, onError: (String) -> Unit) {
        try {
            val res = api.obtenerMensajes(id)
            if (res.isSuccessful) {
                onSuccess(res.body() ?: listOf())
            } else {
                onError("Error al obtener mensajes")
            }
        } catch (e: Exception) {
            onError("Excepción: ${e.message}")
        }
    }

    suspend fun enviarMensaje(id: Int, mensaje: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        try {
            val res = api.enviarMensaje(id, mapOf("message" to mensaje))
            if (res.isSuccessful) {
                onSuccess()
            } else {
                onError("Error al enviar mensaje")
            }
        } catch (e: Exception) {
            onError("Excepción: ${e.message}")
        }
    }


    fun guardarToken(token: String) = gestorToken.guardarToken(token)
    fun obtenerToken() = gestorToken.obtenerToken()
    fun borrarToken() = gestorToken.borrarToken()
}