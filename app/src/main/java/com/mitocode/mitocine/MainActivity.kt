package com.mitocode.mitocine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.mitocode.mitocine.ajustes.AjustesFragment
import com.mitocode.mitocine.peliculas.cartelera.CarteleraFragment
import com.mitocode.mitocine.peliculas.favoritos.FavoritosFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(
                    R.id.contenedor,
                    CarteleraFragment()
                )
            }
        }

        bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            val fragment = when (menuItem.itemId) {
                R.id.navCartelera -> CarteleraFragment()
                R.id.navFavoritos -> FavoritosFragment()
                R.id.navAjustes -> AjustesFragment()
                else -> Fragment()
            }
            supportFragmentManager.commit {
                replace(R.id.contenedor, fragment)
            }

            return@setOnNavigationItemSelectedListener true
        }
    }
}