package com.example.ProyectoFinalMovilesCliente.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ProyectoFinalMovilesCliente.data.model.Mensaje
import com.example.ProyectoFinalMovilesCliente.data.repository.Repository
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel(){
    private val _mensajes = MutableLiveData<List<Mensaje>>()
    val mensajes: LiveData<List<Mensaje>> = _mensajes

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun cargarMensajes(context: Context, appointmentId: Int) {
        viewModelScope.launch {
            Repository(context).getMensajes(
                appointmentId,
                onSuccess = { _mensajes.postValue(it) },
                onError = { _error.postValue(it) }
            )
        }
    }

    fun enviarMensaje(context: Context, appointmentId: Int, mensaje: String) {
        viewModelScope.launch {
            Repository(context).enviarMensaje(
                appointmentId,
                mensaje,
                onSuccess = { cargarMensajes(context, appointmentId) },
                onError = { _error.postValue(it) }
            )
        }
    }
}