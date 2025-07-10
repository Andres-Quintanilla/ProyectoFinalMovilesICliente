package com.example.ProyectoFinalMovilesCliente.util

import android.content.Context
import android.content.SharedPreferences

class GestorToken(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("mis_preferencias", Context.MODE_PRIVATE)

    fun guardarToken(token: String) {
        prefs.edit().putString("access_token", token).apply()
    }

    fun obtenerToken(): String? {
        return prefs.getString("access_token", null)
    }

    fun borrarToken() {
        prefs.edit().remove("access_token").apply()
    }
}