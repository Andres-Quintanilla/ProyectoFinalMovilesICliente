package com.example.ProyectoFinalMovilesCliente.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ProyectoFinalMovilesCliente.data.repository.Repository
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(application)

    fun registrarCliente(
        nombre: String,
        apellido: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = repository.registro(nombre, apellido, email, password)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                onError("Excepción: ${e.localizedMessage}")
            }
        }
    }
}