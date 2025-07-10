package com.example.ProyectoFinalMovilesCliente.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ProyectoFinalMovilesCliente.data.model.Trabajador
import com.example.ProyectoFinalMovilesCliente.data.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfoTrabajadorViewModel : ViewModel() {
    private val _trabajador = MutableLiveData<Trabajador>()
    val trabajador: LiveData<Trabajador> = _trabajador

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun cargarTrabajadorDetalle(context: Context, id: Int) {
        val repository = Repository(context)
        repository.obtenerTrabajadorDetalle(id).enqueue(object : Callback<Trabajador> {
            override fun onResponse(call: Call<Trabajador>, response: Response<Trabajador>) {
                if (response.isSuccessful && response.body() != null) {
                    _trabajador.value = response.body()
                } else {
                    _error.value = "Error al cargar el trabajador (sin datos)"
                }
            }

            override fun onFailure(call: Call<Trabajador>, t: Throwable) {
                _error.value = "Error de conexión: ${t.message}"
            }
        })
    }
}