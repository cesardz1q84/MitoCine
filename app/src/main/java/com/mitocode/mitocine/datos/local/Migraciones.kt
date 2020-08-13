package com.mitocode.mitocine.datos.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE Pelicula ADD COLUMN favorito INTEGER NOT NULL DEFAULT 0")
    }
}