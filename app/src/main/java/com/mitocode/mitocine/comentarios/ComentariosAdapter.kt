package com.mitocode.mitocine.comentarios

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mitocode.mitocine.comentarios.dominio.modelo.Comentario
import com.mitocode.mitocine.databinding.ItemComentarioBinding

class ComentariosAdapter : RecyclerView.Adapter<ComentariosAdapter.ViewHolder>() {

    private var comentarios: MutableList<Comentario>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemComentarioBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = comentarios?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        comentarios?.let {
            holder.bind(it[position])
        }
    }

    fun setComentarios(comentarios: List<Comentario>) {
        this.comentarios = comentarios.toMutableList()
        notifyDataSetChanged()
    }

    fun agregarComentario(comentario: Comentario) {
        this.comentarios?.add(0, comentario)
        notifyItemInserted(0)
    }

    class ViewHolder(private val binding: ItemComentarioBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(comentario: Comentario) {
            binding.comentario = comentario
        }
    }
}