package com.example.ProyectoFinalMovilesCliente.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ProyectoFinalMovilesCliente.data.model.Trabajador
import com.example.ProyectoFinalMovilesCliente.ui.adapters.TrabajadorAdapter
import com.example.ProyectoFinalMovilesCliente.viewModel.TrabajadorViewModel
import com.example.proyectofinalmovilescliente.R
import com.example.proyectofinalmovilescliente.databinding.FragmentTrabajadorBinding


class TrabajadorFragment : Fragment() {
    private lateinit var binding: FragmentTrabajadorBinding
    private lateinit var viewModel: TrabajadorViewModel
    private lateinit var adapter: TrabajadorAdapter
    private var categoriaId: Int = 0
    private var listaCompleta = listOf<Trabajador>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoriaId = arguments?.getInt("categoriaId") ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrabajadorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = TrabajadorAdapter(emptyList()) { trabajadorSeleccionado ->
            val bundle = Bundle().apply {
                putInt("trabajadorId", trabajadorSeleccionado.id)
            }
            findNavController().navigate(R.id.action_trabajadorFragment_to_infoTrabajadorFragment, bundle)
        }
        binding.recyclerTrabajadores.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerTrabajadores.adapter = adapter

        viewModel = ViewModelProvider(this)[TrabajadorViewModel::class.java]

        viewModel.trabajadores.observe(viewLifecycleOwner) {
            listaCompleta = it
            adapter.actualizarLista(it)
        }

        binding.editTextBuscar.addTextChangedListener { texto ->
            val filtro = texto.toString().lowercase()
            val listaFiltrada = listaCompleta.filter {
                it.user.name.lowercase().contains(filtro)
            }
            adapter.actualizarLista(listaFiltrada)
        }


        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

        viewModel.cargarTrabajadores(requireContext(), categoriaId)

    }

}