package com.example.ProyectoFinalMovilesCliente.data.network


import com.example.ProyectoFinalMovilesCliente.data.model.Categoria
import com.example.ProyectoFinalMovilesCliente.data.model.Cita
import com.example.ProyectoFinalMovilesCliente.data.model.CitaRequest
import com.example.ProyectoFinalMovilesCliente.data.model.Mensaje
import com.example.ProyectoFinalMovilesCliente.data.model.Trabajador
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("client/register")
    suspend fun registrarCliente(@Body usuario: Map<String, String>): Response<Unit>

    @POST("client/login")
    suspend fun loginCliente(@Body usuario: Map<String, String>): Response<Map<String, String>>

    @GET("categories")
    suspend fun getCategorias(): Response<List<Categoria>>

    @GET("categories/{id}/workers")
    fun obtenerTrabajadoresPorCategoria(@Path("id") id: Int): Call<List<Trabajador>>

    @GET("workers/{id}")
    fun getTrabajadorDetalle(@Path("id") id: Int): Call<Trabajador>

    @POST("appointments")
    fun crearCita(@Body body: CitaRequest): Call<Cita>

    @GET("appointments/{id}/chats")
    suspend fun obtenerMensajes(@Path("id") id: Int): Response<List<Mensaje>>

    @POST("appointments/{id}/chats")
    suspend fun enviarMensaje(@Path("id") id: Int, @Body body: Map<String, String>): Response<Unit>


}