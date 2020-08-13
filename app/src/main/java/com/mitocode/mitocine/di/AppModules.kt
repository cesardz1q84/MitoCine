package com.mitocode.mitocine.di

import android.location.Geocoder
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.mitocode.mitocine.ajustes.AjustesViewModel
import com.mitocode.mitocine.ajustes.dominio.casodeuso.CambiarNombreCasoDeUso
import com.mitocode.mitocine.comentarios.ComentariosViewModel
import com.mitocode.mitocine.comentarios.dominio.casodeuso.EnviarComentarioCasoDeUso
import com.mitocode.mitocine.comentarios.dominio.casodeuso.ObtenerComentariosCasoDeUso
import com.mitocode.mitocine.datos.ajustes.AjustesRepositorio
import com.mitocode.mitocine.datos.ajustes.source.remote.AjustesRemotoDataSource
import com.mitocode.mitocine.datos.comentario.ComentarioRepositorio
import com.mitocode.mitocine.datos.comentario.source.local.ComentarioLocalDataSource
import com.mitocode.mitocine.datos.comentario.source.remoto.ComentarioRemotoDataSource
import com.mitocode.mitocine.datos.local.MIGRATION_1_2
import com.mitocode.mitocine.datos.local.MitoCineDB
import com.mitocode.mitocine.datos.login.LoginRepositorio
import com.mitocode.mitocine.datos.login.source.remote.LoginRemotoDataSource
import com.mitocode.mitocine.datos.pelicula.PeliculaRepositorio
import com.mitocode.mitocine.datos.pelicula.source.local.PeliculaLocalDataSource
import com.mitocode.mitocine.datos.pelicula.source.remoto.PeliculaRemotoDataSource
import com.mitocode.mitocine.datos.registro.RegistroRepositorio
import com.mitocode.mitocine.datos.registro.source.remote.RegistroRemotoDataSource
import com.mitocode.mitocine.datos.remoto.MitoCineApi
import com.mitocode.mitocine.login.LoginViewModel
import com.mitocode.mitocine.login.dominio.casodeuso.LogearUsuarioCasoDeUso
import com.mitocode.mitocine.mapa.MapaViewModel
import com.mitocode.mitocine.peliculadetalle.PeliculaDetalleViewModel
import com.mitocode.mitocine.peliculadetalle.dominio.casodeuso.ActualizarFavoritoCasoDeUso
import com.mitocode.mitocine.peliculadetalle.dominio.casodeuso.ObtenerPeliculaPorIdCasoDeUso
import com.mitocode.mitocine.peliculas.dominio.casodeuso.ObtenerPeliculasCarteleraCaseDeUso
import com.mitocode.mitocine.peliculas.dominio.casodeuso.ObtenerPeliculasFavoritasCaseDeUso
import com.mitocode.mitocine.peliculas.dominio.casodeuso.ObtenerPeliculasProximasCaseDeUso
import com.mitocode.mitocine.registro.RegistroViewModel
import com.mitocode.mitocine.registro.dominio.casodeuso.RegistrarUsuarioCasoDeUso
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.*

val appModule = module {

    single {
        val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.tag("Okhttp").d(message)
            }
        }
        ).also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(MitoCineApi.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MitoCineApi::class.java)
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            MitoCineDB::class.java,
            "MitoCine.db"
        )
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
    }

    single { PeliculaLocalDataSource(get<MitoCineDB>().peliculaDao()) }

    single { PeliculaRemotoDataSource(get()) }

    single { RegistroRemotoDataSource(get()) }

    single { RegistroRepositorio(get()) }

    single { LoginRemotoDataSource(get()) }

    single { LoginRepositorio(get()) }

    single { AjustesRemotoDataSource(get()) }

    single { AjustesRepositorio(get()) }

    single { PeliculaRepositorio(get(), get()) }

    single { ComentarioLocalDataSource(get<MitoCineDB>().comentarioDao()) }

    single { ComentarioRemotoDataSource(get()) }

    single { ComentarioRepositorio(get(), get()) }

    factory { RegistrarUsuarioCasoDeUso(get()) }

    factory { LogearUsuarioCasoDeUso(get()) }

    factory { CambiarNombreCasoDeUso(get()) }

    factory { ObtenerPeliculasCarteleraCaseDeUso(get()) }

    factory { ObtenerPeliculasProximasCaseDeUso(get()) }

    factory { ObtenerPeliculasFavoritasCaseDeUso(get()) }

    factory { ObtenerPeliculaPorIdCasoDeUso(get()) }

    factory { ActualizarFavoritoCasoDeUso(get()) }

    factory { EnviarComentarioCasoDeUso(get()) }

    factory { ObtenerComentariosCasoDeUso(get()) }

    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }

    single { Geocoder(androidContext(), Locale("es")) }

    viewModel { AjustesViewModel(get(), get()) }

    viewModel { PeliculaDetalleViewModel(get(), get()) }

    viewModel { RegistroViewModel(get(), get()) }

    viewModel { LoginViewModel(get(), get()) }

    viewModel { ComentariosViewModel(get(), get(), get()) }

    viewModel { MapaViewModel(get()) }
}

