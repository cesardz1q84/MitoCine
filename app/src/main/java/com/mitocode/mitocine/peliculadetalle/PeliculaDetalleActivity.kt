package com.mitocode.mitocine.peliculadetalle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.mitocode.mitocine.R
import com.mitocode.mitocine.comentarios.ComentariosActivity
import com.mitocode.mitocine.peliculas.dominio.modelo.Pelicula
import com.mitocode.mitocine.utils.cargarPorUrl
import kotlinx.android.synthetic.main.activity_pelicula_detalle.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PeliculaDetalleActivity : AppCompatActivity() {

    private val peliculaDetalleViewModel by viewModel<PeliculaDetalleViewModel>()

    private var pelicula: Pelicula? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.v("Ciclo de Vida", "onCreate")

        setContentView(R.layout.activity_pelicula_detalle)

        val peliculaId = intent.getStringExtra("peliculaId")

        peliculaId?.let {
            peliculaDetalleViewModel.obtenerPeliculaPorId(it)
        }

        btnComentar.setOnClickListener {
            val intent = Intent(this, ComentariosActivity::class.java)
            intent.putExtra("peliculaId", peliculaId)
            startActivity(intent)
        }

        btnFavorito.setOnClickListener {
            pelicula?.let {
                it.favorito = !it.favorito
                setupBtnFavorito(it.favorito)
                peliculaDetalleViewModel.actualizarFavorito(it.id, it.favorito)
            }
        }

        peliculaDetalleViewModel.pelicula.observe(this, Observer { pelicula ->
            this.pelicula = pelicula
            with(pelicula) {
                txtPeliculaTitulo.text = titulo
                txtPeliculaSinopsis.text = sinopsis
                setupBtnFavorito(favorito)
                imgPeliculaPoster.cargarPorUrl(imagenUrl)
            }
        })
    }

    private fun setupBtnFavorito(esFavorito: Boolean) {
        val buttonRes = if (esFavorito) R.drawable.ic_favorito else R.drawable.ic_favorito_borde
        btnFavorito.setImageResource(buttonRes)
    }

    override fun onStart() {
        super.onStart()
        Log.v("Ciclo de Vida", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.v("Ciclo de Vida", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.v("Ciclo de Vida", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.v("Ciclo de Vida", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("Ciclo de Vida", "onDestroy")
    }
}
