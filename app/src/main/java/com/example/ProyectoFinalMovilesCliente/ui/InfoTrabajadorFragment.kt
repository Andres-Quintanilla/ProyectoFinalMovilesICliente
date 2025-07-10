package com.example.ProyectoFinalMovilesCliente.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.ProyectoFinalMovilesCliente.data.model.Trabajador
import com.example.ProyectoFinalMovilesCliente.viewModel.CitaViewModel
import com.example.ProyectoFinalMovilesCliente.viewModel.InfoTrabajadorViewModel
import com.example.proyectofinalmovilescliente.R
import com.example.proyectofinalmovilescliente.databinding.FragmentInfoTrabajadorBinding


class InfoTrabajadorFragment : Fragment() {

    private lateinit var binding: FragmentInfoTrabajadorBinding
    private lateinit var viewModel: InfoTrabajadorViewModel
    private var trabajadorId: Int = 0
    private lateinit var citaViewModel: CitaViewModel
    private var categoriaIdSeleccionada: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trabajadorId = arguments?.getInt("trabajadorId") ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoTrabajadorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[InfoTrabajadorViewModel::class.java]
        citaViewModel = ViewModelProvider(this)[CitaViewModel::class.java]

        viewModel.trabajador.observe(viewLifecycleOwner) { trabajador ->
            val imagen = if (trabajador.picture_url == "null") null else trabajador.picture_url
            Glide.with(requireContext())
                .load(imagen)
                .placeholder(R.drawable.ic_user)
                .into(binding.imageFoto)

            binding.textNombre.text = trabajador.user?.name ?: "Nombre no disponible"
            val rating = trabajador.average_rating ?: "0"
            val trabajos = trabajador.reviews_count
            binding.textCalificacion.text = "$rating ★ - $trabajos trabajos"
            val categoriasTexto = trabajador.categories?.joinToString(", ") { it.name } ?: "Sin categorías"
            binding.textOficios.text = categoriasTexto

            val textoResenas = trabajador.reviews?.joinToString("\n\n") {
                val cliente = it.user?.name ?: "Cliente"
                val comentario = it.comment ?: "Sin comentario"
                "$cliente: $comentario"
            } ?: "Sin reseñas"
            binding.textResenas.text = textoResenas

            binding.btnContactar.setOnClickListener {
                citaViewModel.crearCita(requireContext(), trabajador.id, categoriaIdSeleccionada)
            }
        }

        citaViewModel.citaCreada.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "Cita creada correctamente", Toast.LENGTH_SHORT).show()
                val bundle = Bundle().apply {
                    putInt("trabajadorId", trabajadorId)
                }
                //findNavController().navigate(R.id.action_infoTrabajadorFragment_to_chatFragment, bundle)
            }
        }

        citaViewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }


        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

        viewModel.cargarTrabajadorDetalle(requireContext(), trabajadorId)

    }


}