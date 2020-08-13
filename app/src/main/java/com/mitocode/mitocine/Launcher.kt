package com.mitocode.mitocine

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.mitocode.mitocine.login.LoginActivity

class Launcher : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        super.onCreate(savedInstanceState)

        val login = preferences.getBoolean("login", false)

        val intent = if (login) Intent(this, MainActivity::class.java) else Intent(
            this,
            LoginActivity::class.java
        )
        startActivity(intent)

    }
}