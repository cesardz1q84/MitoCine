package com.mitocode.mitocine.peliculas.cartelera

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CarteleraPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> PeliculasCarteleraFragment()
            1 -> PeliculasProximamenteFragment()
            else -> Fragment()
        }
    }
}