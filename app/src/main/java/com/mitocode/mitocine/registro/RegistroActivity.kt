package com.mitocode.mitocine.registro

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.mitocode.mitocine.R
import com.mitocode.mitocine.login.LoginActivity
import kotlinx.android.synthetic.main.activity_registro.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistroActivity : AppCompatActivity() {
    private val registroViewModel: RegistroViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        btnRegistrar.setOnClickListener {
            if (edtCorreo.text.toString().isEmpty() ||
                edtPassword.text.toString().isEmpty() ||
                edtNombreUsuario.text.toString().isEmpty()
            ) {
                Toast.makeText(this, "Uno o más campos se encuentran vacíos", Toast.LENGTH_SHORT)
                    .show()
            } else {
                registroViewModel.registrarUsuario(
                    edtCorreo.text.toString(),
                    edtPassword.text.toString(),
                    edtNombreUsuario.text.toString()
                )
            }
        }

        registroViewModel.mostrarProgresoEnvio.observe(this, Observer { enviando ->
            pbRegistro.visibility = if (enviando) View.VISIBLE else View.GONE
            btnRegistrar.isEnabled = !enviando
            edtCorreo.isEnabled = !enviando
            edtPassword.isEnabled = !enviando
            edtNombreUsuario.isEnabled = !enviando
        })

        edtCorreo.addTextChangedListener {
            tilCorreoReg.error =
                if (edtCorreo.text.toString().isEmpty()) "Ingrese un correo" else null

        }
        edtNombreUsuario.addTextChangedListener {
            tilNombreUsuarioReg.error = if (edtNombreUsuario.text.toString()
                    .isEmpty()
            ) "Ingrese un nombre de usuario" else null

        }

        edtPassword.addTextChangedListener {
            tilPasswordReg.error =
                if (edtPassword.text.toString().isEmpty()) "Ingrese una contraseña" else null
        }

        registroViewModel.nuevoUsuario.observe(this, Observer {
            Toast.makeText(this, "Registrado", Toast.LENGTH_LONG).show()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        })
        registroViewModel.mensajeError.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

    }
}