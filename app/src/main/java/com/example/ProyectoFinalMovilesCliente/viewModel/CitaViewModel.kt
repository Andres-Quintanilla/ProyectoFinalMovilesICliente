package com.example.ProyectoFinalMovilesCliente.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ProyectoFinalMovilesCliente.data.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CitaViewModel : ViewModel() {
    private val _citaCreada = MutableLiveData<Boolean>()
    val citaCreada: LiveData<Boolean> = _citaCreada

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun crearCita(context: Context, trabajadorId: Int, categoriaId: Int) {
        val repository = Repository(context)
        repository.crearCita(trabajadorId, categoriaId).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                _citaCreada.value = response.isSuccessful
                if (!response.isSuccessful) _error.value = "Error al crear cita"
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                _error.value = "Fallo de conexión: ${t.message}"
            }
        })
    }
}