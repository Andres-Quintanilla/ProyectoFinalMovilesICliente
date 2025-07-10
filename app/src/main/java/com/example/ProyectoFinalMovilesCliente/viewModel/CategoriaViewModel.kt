package com.example.ProyectoFinalMovilesCliente.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ProyectoFinalMovilesCliente.data.model.Categoria
import com.example.ProyectoFinalMovilesCliente.data.repository.Repository
import kotlinx.coroutines.launch

class CategoriaViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(application)

    private val _categorias = MutableLiveData<List<Categoria>>()
    val categorias: LiveData<List<Categoria>> = _categorias

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun cargarCategorias() {
        viewModelScope.launch {
            try {
                val response = repository.obtenerCategorias()
                if (response.isSuccessful) {
                    _categorias.value = response.body()
                } else {
                    _error.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Excepción: ${e.localizedMessage}"
            }
        }
    }
}