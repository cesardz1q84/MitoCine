package com.mitocode.mitocine.datos.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mitocode.mitocine.comentarios.dominio.modelo.Comentario
import com.mitocode.mitocine.datos.comentario.source.local.ComentarioDao
import com.mitocode.mitocine.datos.pelicula.source.local.PeliculaDao
import com.mitocode.mitocine.peliculas.dominio.modelo.Pelicula

@Database(entities = [Pelicula::class, Comentario::class], version = 4, exportSchema = false)
abstract class MitoCineDB: RoomDatabase() {

    abstract fun peliculaDao(): PeliculaDao

    abstract fun comentarioDao(): ComentarioDao

    companion object {

        private var INSTANCIA: MitoCineDB? = null

        fun obtenerInstancia(context: Context): MitoCineDB {
            return INSTANCIA
                ?: synchronized(this) {
                INSTANCIA
                    ?: crearDB(
                        context
                    ).also {
                    INSTANCIA = it
                }
            }
        }

        private fun crearDB(context: Context): MitoCineDB {
            return Room.databaseBuilder(
                context.applicationContext,
                MitoCineDB::class.java,
                "MitoCine.db"
            )
                .addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}