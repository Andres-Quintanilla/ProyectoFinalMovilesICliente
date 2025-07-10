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



class TrabajadorViewModel : ViewModel() {
    private val _trabajadores = MutableLiveData<List<Trabajador>>()
    val trabajadores: LiveData<List<Trabajador>> = _trabajadores

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun cargarTrabajadores(context: Context, idCategoria: Int) {
       val repository = Repository(context)
        repository.obtenerTrabajadoresPorCategoria(idCategoria)
            .enqueue(object : Callback<List<Trabajador>> {
                override fun onResponse(call: Call<List<Trabajador>>, response: Response<List<Trabajador>>) {
                    if (response.isSuccessful) {
                        _trabajadores.value = response.body()
                    } else {
                        _error.value = "Error al obtener trabajadores"
                    }
                }

                override fun onFailure(call: Call<List<Trabajador>>, t: Throwable) {
                    _error.value = "Error: ${t.message}"
                }
            })
    }
}