package com.mitocode.mitocine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.mitocode.mitocine.ajustes.AjustesFragment
import com.mitocode.mitocine.peliculas.cartelera.CarteleraFragment
import com.mitocode.mitocine.peliculas.favoritos.FavoritosFragment
import kotlinx.android.synthetic.main.activity_main_drawer.*

class MainActivityDrawer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_drawer)
        toolbar.setNavigationOnClickListener {
            drawer.openDrawer(navView)
        }

        navView.setNavigationItemSelectedListener {
            val fragment = when (it.itemId) {
                R.id.navCartelera -> CarteleraFragment()
                R.id.navFavoritos -> FavoritosFragment()
                R.id.navAjustes -> AjustesFragment()
                else -> Fragment()
            }
            supportFragmentManager.commit {
                replace(R.id.contenedor, fragment)
            }

            drawer.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(
                    R.id.contenedor,
                    CarteleraFragment()
                )
            }
        }
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}