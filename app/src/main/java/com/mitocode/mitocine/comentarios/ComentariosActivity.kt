package com.mitocode.mitocine.comentarios

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mitocode.mitocine.R
import kotlinx.android.synthetic.main.activity_comentarios.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ComentariosActivity : AppCompatActivity() {

    private val comentariosViewModel: ComentariosViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comentarios)

        val peliculaId = intent.getStringExtra("peliculaId")

        toolbar.setNavigationOnClickListener {
            finish()
        }

        val adapter = ComentariosAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        recyclerView.adapter = adapter

        comentariosViewModel.obtenerComentarios(peliculaId!!, true)

        comentariosViewModel.comentarios.observe(this, Observer { comentarios ->
            adapter.setComentarios(comentarios)
        })

        comentariosViewModel.mostrarProgreso.observe(this, Observer {
            swipeRefresh.isRefreshing = it
        })

        swipeRefresh.setOnRefreshListener {
            comentariosViewModel.obtenerComentarios(peliculaId, true)
        }

        btnEnviar.setOnClickListener {
            comentariosViewModel.enviarComentario(
                edtComentario.text.toString(),
                peliculaId
            )
        }

        comentariosViewModel.mostrarProgresoEnvio.observe(this, Observer { enviando ->
            btnEnviar.isEnabled = !enviando
            edtComentario.isEnabled = !enviando
        })

        comentariosViewModel.nuevoComentario.observe(this, Observer { nuevoComentario ->
            adapter.agregarComentario(nuevoComentario)
            edtComentario.setText("")
        })
    }
}