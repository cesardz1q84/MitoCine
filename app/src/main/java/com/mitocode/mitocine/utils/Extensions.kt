package com.mitocode.mitocine.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

fun ImageView.cargarPorUrl(url: String) {
    val anchoPantalla = context.resources.displayMetrics.widthPixels

    val options = RequestOptions()
        .override(anchoPantalla / 2, anchoPantalla / 2 * 1000 / 674)

    Glide.with(this)
        .load(url)
        .apply(options)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}