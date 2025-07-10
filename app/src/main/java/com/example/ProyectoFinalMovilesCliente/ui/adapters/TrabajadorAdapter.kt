package com.example.ProyectoFinalMovilesCliente.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ProyectoFinalMovilesCliente.data.model.Trabajador
import com.example.proyectofinalmovilescliente.R
import com.example.proyectofinalmovilescliente.databinding.ItemTrabajadorBinding

class TrabajadorAdapter(
    private var lista: List<Trabajador>,
    private val onItemClick: (Trabajador) -> Unit

) : RecyclerView.Adapter<TrabajadorAdapter.TrabajadorViewHolder>()  {

    inner class TrabajadorViewHolder(val binding: ItemTrabajadorBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrabajadorViewHolder {
        val binding = ItemTrabajadorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrabajadorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrabajadorViewHolder, position: Int) {
        val trabajador = lista[position]
        holder.binding.apply {
            textNombre.text = trabajador.user.name
            textStats.text = "${trabajador.average_rating}% - ${trabajador.reviews_count}"
            val imagen = if (trabajador.picture_url == "null") null else trabajador.picture_url

            Glide.with(imageFoto.context)
                .load(imagen)
                .placeholder(R.drawable.ic_user)
                .into(imageFoto)

            root.setOnClickListener {
                onItemClick(trabajador)
            }
        }
    }

    override fun getItemCount(): Int = lista.size

    fun actualizarLista(nuevaLista: List<Trabajador>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}