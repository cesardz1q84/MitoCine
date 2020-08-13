package com.mitocode.mitocine.login

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import com.mitocode.mitocine.MainActivity
import com.mitocode.mitocine.R
import com.mitocode.mitocine.registro.RegistroActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val txtRegistrar = SpannableString("¿No tienes una cuenta aún? Regístrate").also {
            it.setSpan(UnderlineSpan(), 26, 37, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            it.setSpan(
                ForegroundColorSpan(Color.RED),
                26, 37,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        tvRegistrar.text = txtRegistrar

        edtCorreo.setText(preferences.getString("correo", ""))

        btnIngresar.setOnClickListener {
            if (edtCorreo.text.toString().isEmpty() ||
                edtPassword.text.toString().isEmpty()
            ) {
                Toast.makeText(this, "Uno o más campos se encuentran vacíos", Toast.LENGTH_SHORT)
                    .show()
            } else {
                loginViewModel.logearUsuario(
                    edtCorreo.text.toString(),
                    edtPassword.text.toString()
                )
            }
        }

        loginViewModel.nuevoLogin.observe(this, Observer {
            val login = preferences.getBoolean("login", false)
            if (login) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        })
        loginViewModel.mensajeError.observe(this, Observer { mensajeError ->
            Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show()
        })


        edtCorreo.addTextChangedListener {
            tilCorreoLogin.error =
                if (edtCorreo.text.toString().isEmpty()) "Ingrese un correo" else null

        }

        edtPassword.addTextChangedListener {
            tilPasswordLogin.error =
                if (edtPassword.text.toString().isEmpty()) "Ingrese una contraseña" else null
        }

        tvRegistrar.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        loginViewModel.mostrarProgresoEnvio.observe(this, Observer { enviando ->
            pbLogin.visibility = if (enviando) View.VISIBLE else View.GONE
            btnIngresar.isEnabled = !enviando
            edtCorreo.isEnabled = !enviando
            edtPassword.isEnabled = !enviando
            tvRegistrar.isEnabled = !enviando
        })
    }
}