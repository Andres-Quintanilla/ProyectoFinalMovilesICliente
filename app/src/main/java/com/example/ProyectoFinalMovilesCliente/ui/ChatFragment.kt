package com.example.ProyectoFinalMovilesCliente.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectofinalmovilescliente.databinding.FragmentChatBinding
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.ProyectoFinalMovilesCliente.viewModel.ChatViewModel


class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private lateinit var viewModel: ChatViewModel
    private var appointmentId: Int = 0
    private val handler = Handler()
    private val refreshRunnable = object : Runnable {
        override fun run() {
            viewModel.cargarMensajes(requireContext(), appointmentId)
            handler.postDelayed(this, 30000)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        appointmentId = requireArguments().getInt("appointmentId")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnEnviar.setOnClickListener {
            val mensaje = binding.editMensaje.text.toString().trim()
            if (mensaje.isNotEmpty()) {
                viewModel.enviarMensaje(requireContext(), appointmentId, mensaje)
                binding.editMensaje.setText("")
            }
        }

        binding.btnConcretar.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Concretar cita")
                .setMessage("¿Estás seguro que deseas concretar esta cita?")
                .setPositiveButton("Sí") { _, _ ->
                    // TODO: Navegar a seleccionar ubicación
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        viewModel.mensajes.observe(viewLifecycleOwner) {
            val texto = it.joinToString("\n\n") { m ->
                val autor = if (m.from_client) "Yo" else m.worker?.user?.name ?: "Trabajador"
                "$autor: ${m.message}"
            }
            binding.textMensajes.text = texto
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.cargarMensajes(requireContext(), appointmentId)
        handler.postDelayed(refreshRunnable, 30000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(refreshRunnable)
    }

}