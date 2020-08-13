package com.mitocode.mitocine.ajustes

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import com.mitocode.mitocine.MainActivity
import com.mitocode.mitocine.R
import com.mitocode.mitocine.login.LoginActivity
import com.mitocode.mitocine.mapa.MapaActivity
import kotlinx.android.synthetic.main.fragment_ajustes.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import kotlin.math.min

const val REQUEST_CODE_TOMAR_FOTO = 10
const val REQUEST_CODE_SELECCIONAR_FOTO = 11
const val REQUEST_CODE_DIRECCION = 12

const val REQUEST_CODE_PERMISO_CAMARA = 20
const val REQUEST_CODE_PERMISO_GALERIA = 21

class AjustesFragment : Fragment() {

    private val ajustesViewModel: AjustesViewModel by viewModel()

    private val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireContext())
    }

    private val archivoFoto: File by lazy {
        File(requireContext().filesDir, "perfil.jpg")
    }

    private val rutaArchivoFoto: String by lazy {
        archivoFoto.absolutePath
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ajustes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        savedInstanceState?.apply {
            edtNombreUsuario.setText(getString("nombreUsuario", ""))
            edtTelefono.setText(getString("telefono", ""))
        }

        toolbarAjustes.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_guardar -> {
                    cambiarNombreServidor()
                }
            }
            true
        }

        swtTema.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            preferences.edit {
                putBoolean("temaOscuro", isChecked)
            }
        }


        edtNombreUsuario.setText(preferences.getString("nombreUsuario", ""))
        edtTelefono.setText(preferences.getString("telefono", ""))
        swtTema.isChecked = preferences.getBoolean("temaOscuro", false)
        txtDireccionUsuario.text = preferences.getString("direccion", "")


        ajustesViewModel.nuevoNombre.observe(viewLifecycleOwner, Observer { nuevoNombre ->
            Toast.makeText(requireContext(), "Cambios guardados", Toast.LENGTH_SHORT).show()
        })

        ajustesViewModel.mensajeError.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        btnDeslogear.setOnClickListener {
            ajustesViewModel.cerrarSesion()
        }

        ajustesViewModel.estadoLogin.observe(viewLifecycleOwner, Observer {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        })

        edtNombreUsuario.addTextChangedListener {
            tilNombreUsuarioAjustes.error =
                if (edtNombreUsuario.text.toString().isEmpty()) "Ingrese un nombre válido" else null
        }

        edtTelefono.addTextChangedListener {
            preferences.edit {
                putString("telefono", it.toString())
            }
        }
        ajustesViewModel.mostrarProgresoEnvio.observe(viewLifecycleOwner, Observer { enviando ->
            edtNombreUsuario.isEnabled = !enviando
        })

        fabCamara.setOnClickListener {
            mostrarAlertaFuenteImagen()
        }

        imgPerfil.post {
            mostrarImagen()
        }

        btnCambiarDireccion.setOnClickListener {
            Intent(requireContext(), MapaActivity::class.java).also {
                startActivityForResult(it, REQUEST_CODE_DIRECCION)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("nombreUsuario", edtNombreUsuario.text.toString())
        outState.putString("telefono", edtTelefono.text.toString())
    }

    private fun cambiarNombreServidor() {
        val id = preferences.getString("id", "")
        val token = preferences.getString("token", "")
        val nombre = edtNombreUsuario.text.toString()

        if (nombre.isEmpty()) {
            Toast.makeText(requireContext(), "Ingrese un nombre", Toast.LENGTH_SHORT).show()
        } else {
            ajustesViewModel.cambiarNombre(id!!, token!!, nombre)

        }

    }

    private fun mostrarAlertaFuenteImagen() {
        AlertDialog.Builder(requireContext())
            .setItems(arrayOf("Camara", "Galeria")) { _, posicion ->
                when (posicion) {
                    0 -> verificarPermisosCamara()
                    1 -> verificarPermisosGaleria()
                }
            }
            .setTitle("Seleccionar fuente de la imagen")
            .show()
    }

    private fun verificarPermisosGaleria() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Snackbar.make(
                    ajustesContenedor,
                    "Se necesita permisos para acceder a la galeria y poder obtener fotos",
                    Snackbar.LENGTH_LONG
                ).setAction("Permitir") {
                    requestPermissions(
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_CODE_PERMISO_GALERIA
                    )
                }.show()
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE_PERMISO_GALERIA
                )
            }
        } else {
            abrirGaleria()
        }
    }

    private fun abrirGaleria() {
        Intent(Intent.ACTION_PICK).also { seleccionarFotoIntent ->
            seleccionarFotoIntent.type = "image/*"
            seleccionarFotoIntent.resolveActivity(requireContext().packageManager)?.also {
                startActivityForResult(seleccionarFotoIntent, REQUEST_CODE_SELECCIONAR_FOTO)
            }
        }
    }

    private fun verificarPermisosCamara() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                Snackbar.make(
                    ajustesContenedor,
                    "Se necesita permisos para acceder a la camara y poder tomar fotos",
                    Snackbar.LENGTH_LONG
                ).setAction("Permitir") {
                    requestPermissions(
                        arrayOf(Manifest.permission.CAMERA),
                        REQUEST_CODE_PERMISO_CAMARA
                    )
                }.show()
            } else {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CODE_PERMISO_CAMARA)
            }
        } else {
            abrirCamara()
        }
    }

    private fun abrirCamara() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { camaraIntent ->
            camaraIntent.resolveActivity(requireContext().packageManager)?.also {
                val fotoUri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.mitocode.mitocine",
                    archivoFoto
                )
                camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri)
                startActivityForResult(camaraIntent, REQUEST_CODE_TOMAR_FOTO)
            }
        }
    }

    private fun mostrarImagen() {
        if (!archivoFoto.exists()) {
            imgPerfil.setImageResource(R.mipmap.ic_launcher)
            return
        }

        val targetAncho = imgPerfil.width
        val targeAlto = imgPerfil.height

        val opciones = BitmapFactory.Options().apply {
            inJustDecodeBounds = true

            BitmapFactory.decodeFile(rutaArchivoFoto, this)

            val (fotoAncho: Int, fotoAlto: Int) = outWidth to outHeight

            Log.d("Escala", "Foto: ancho: $fotoAncho alto: $fotoAlto")

            val factorEscala: Int = min(fotoAncho / targetAncho, fotoAlto / targeAlto)

            inJustDecodeBounds = false

            inSampleSize = factorEscala
        }

        BitmapFactory.decodeFile(rutaArchivoFoto, opciones)?.also {
            imgPerfil.setImageBitmap(it)
        }
    }

    private fun copiarImageDeUri(uri: Uri) {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(File(rutaArchivoFoto))
        inputStream?.use {
            val buf = ByteArray(1024)
            var len: Int
            outputStream.use { out ->
                while (it.read(buf).also { bytesRead -> len = bytesRead } > 0) {
                    out.write(buf, 0, len)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_TOMAR_FOTO -> {
                if (resultCode == Activity.RESULT_OK) {
                    mostrarImagen()
                }
            }
            REQUEST_CODE_SELECCIONAR_FOTO -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.data?.let { uri ->
//                        imgPerfil.setImageURI(uri)
                        copiarImageDeUri(uri)
                        mostrarImagen()
                    }
                }
            }
            REQUEST_CODE_DIRECCION -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.let {
                        val direccion = it.getStringExtra("direccion")
                        txtDireccionUsuario.text = direccion
                        preferences.edit {
                            putString("direccion", direccion)
                        }
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_PERMISO_CAMARA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    abrirCamara()
                }
            }
            REQUEST_CODE_PERMISO_GALERIA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    abrirGaleria()
                }
            }
        }
    }
}