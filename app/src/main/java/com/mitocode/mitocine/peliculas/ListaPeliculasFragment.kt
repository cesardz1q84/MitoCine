package com.mitocode.mitocine.peliculas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.mitocode.mitocine.R
import com.mitocode.mitocine.datos.pelicula.PeliculaRepositorio
import com.mitocode.mitocine.peliculadetalle.PeliculaDetalleActivity
import com.mitocode.mitocine.peliculas.dominio.casodeuso.ObtenerPeliculasCarteleraCaseDeUso
import com.mitocode.mitocine.peliculas.dominio.casodeuso.ObtenerPeliculasFavoritasCaseDeUso
import com.mitocode.mitocine.peliculas.dominio.casodeuso.ObtenerPeliculasProximasCaseDeUso
import com.mitocode.mitocine.peliculas.dominio.modelo.Pelicula
import kotlinx.android.synthetic.main.fragment_lista_peliculas.*
import org.koin.android.ext.android.inject

abstract class ListaPeliculasFragment : Fragment() {

    private val obtenerPeliculasCarteleraCaseDeUso: ObtenerPeliculasCarteleraCaseDeUso by inject()
    private val obtenerPeliculasProximasCaseDeUso: ObtenerPeliculasProximasCaseDeUso by inject()
    private val obtenerPeliculasFavoritasCaseDeUso: ObtenerPeliculasFavoritasCaseDeUso by inject()

    protected val peliculasViewModel by viewModels<PeliculasViewModel>(
        factoryProducer = {
            PeliculasViewModelFactory(
                obtenerPeliculasCarteleraCaseDeUso,
                obtenerPeliculasProximasCaseDeUso,
                obtenerPeliculasFavoritasCaseDeUso
            )
        }
    )

    private lateinit var adapter: PeliculasAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lista_peliculas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = PeliculasAdapter()

        adapter.setPeliculaClickListener { pelicula ->
            abrirPeliculaDetalle(pelicula)
        }

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        swipeRefresh.setOnRefreshListener {
            getPeliculas(true)
        }

        peliculasViewModel.peliculas.observe(viewLifecycleOwner, Observer { peliculas ->
            adapter.setPeliculas(peliculas)
        })

        peliculasViewModel.mostrarProgreso.observe(viewLifecycleOwner, Observer { mostrarProgreso ->
            swipeRefresh.isRefreshing = mostrarProgreso
        })
    }

    override fun onResume() {
        super.onResume()
        getPeliculas()
    }

    private fun abrirPeliculaDetalle(pelicula: Pelicula) {
        val intent = Intent(requireContext(), PeliculaDetalleActivity::class.java)
        intent.putExtra("peliculaId", pelicula.id)
        startActivity(intent)
    }

    abstract fun getPeliculas(forzarActualizacion: Boolean = false)
}