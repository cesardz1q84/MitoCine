package com.mitocode.mitocine.peliculas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mitocode.mitocine.databinding.ItemPeliculaBinding
import com.mitocode.mitocine.peliculas.dominio.modelo.Pelicula
import com.mitocode.mitocine.utils.cargarPorUrl

class PeliculasAdapter : RecyclerView.Adapter<PeliculasAdapter.PeliculaViewHolder>() {

    private var peliculas: List<Pelicula>? = null

    private var listener: ((pelicula: Pelicula) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPeliculaBinding.inflate(inflater, parent, false)
        return PeliculaViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int = peliculas?.size ?: 0

    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
        peliculas?.let {
            holder.bind(it[position], listener)
        }
    }

    fun setPeliculas(peliculas: List<Pelicula>) {
        this.peliculas = peliculas
        notifyDataSetChanged()
    }

    fun setPeliculaClickListener(listener: (pelicula: Pelicula) -> Unit) {
        this.listener = listener
    }

    class PeliculaViewHolder(private val binding: ItemPeliculaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pelicula: Pelicula, listener: ((pelicula: Pelicula) -> Unit)?) {
            binding.pelicula = pelicula

            binding.imgPelicula.cargarPorUrl(pelicula.imagenUrl)

            binding.root.setOnClickListener {
                listener?.let {
//                    listener.invoke(pelicula)
                    listener(pelicula)
                }
            }
        }
    }
}