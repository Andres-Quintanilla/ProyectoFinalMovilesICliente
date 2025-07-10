package com.example.ProyectoFinalMovilesCliente.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ProyectoFinalMovilesCliente.ui.adapters.CategoriaAdapter
import com.example.ProyectoFinalMovilesCliente.viewModel.CategoriaViewModel
import com.example.proyectofinalmovilescliente.R
import com.example.proyectofinalmovilescliente.databinding.FragmentCategoriaBinding


class CategoriaFragment : Fragment() {
    private lateinit var binding: FragmentCategoriaBinding
    private lateinit var adapter: CategoriaAdapter
    private lateinit var viewModel: CategoriaViewModel
    private var listaCompleta = listOf<com.example.ProyectoFinalMovilesCliente.data.model.Categoria>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[CategoriaViewModel::class.java]

        adapter = CategoriaAdapter(emptyList()) { categoriaSeleccionada ->
            val bundle = Bundle().apply {
                putInt("categoriaId", categoriaSeleccionada.id)
            }
            findNavController().navigate(R.id.action_categoriaFragment_to_trabajadorFragment, bundle)
        }
        binding.recyclerCategorias.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerCategorias.adapter = adapter

        viewModel.categorias.observe(viewLifecycleOwner) {
            listaCompleta = it
            adapter.actualizarLista(it)
        }

        binding.editTextBuscar.addTextChangedListener { texto ->
            val filtro = texto.toString().lowercase()
            val listaFiltrada = listaCompleta.filter {
                it.name.lowercase().contains(filtro)
            }
            adapter.actualizarLista(listaFiltrada)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

        binding.fabNuevaCita.setOnClickListener {
            //findNavController().navigate(R.id.irACitasFragment)
        }

        viewModel.cargarCategorias()
    }

}