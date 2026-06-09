package com.joel.spiderbiblioteca.data.local

import android.content.Context

object SessionManager {

    @Volatile var token: String? = null
        private set

    @Volatile var username: String? = null
        private set

    @Volatile var avatarNombre: String? = null
        private set

    @Volatile var avatarUrl: String = ""
        private set

    private const val PREFS_NAME = "auth_session"
    private const val KEY_TOKEN = "jwt_token"
    private const val KEY_USERNAME = "username"
    private const val KEY_AVATAR_NOMBRE = "avatar_nombre"
    private const val KEY_AVATAR_URL = "avatar_url"

    fun init(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        token = prefs.getString(KEY_TOKEN, null)
        username = prefs.getString(KEY_USERNAME, null)
        avatarNombre = prefs.getString(KEY_AVATAR_NOMBRE, null)
        avatarUrl = prefs.getString(KEY_AVATAR_URL, "") ?: ""
    }

    fun saveSession(context: Context, newToken: String, newUsername: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        token = newToken
        username = newUsername
        // Restaura el avatar que sobrevivió al logout (si existía)
        avatarNombre = prefs.getString(KEY_AVATAR_NOMBRE, null)
        avatarUrl = prefs.getString(KEY_AVATAR_URL, "") ?: ""
        prefs.edit()
            .putString(KEY_TOKEN, newToken)
            .putString(KEY_USERNAME, newUsername)
            .apply()
    }

    fun saveAvatar(context: Context, nombre: String, url: String) {
        avatarNombre = nombre
        avatarUrl = url
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
            .putString(KEY_AVATAR_NOMBRE, nombre)
            .putString(KEY_AVATAR_URL, url)
            .apply()
    }

    fun clearAvatar(context: Context) {
        avatarNombre = null
        avatarUrl = ""
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
            .remove(KEY_AVATAR_NOMBRE)
            .remove(KEY_AVATAR_URL)
            .apply()
    }

    fun clearSession(context: Context) {
        token = null
        username = null
        avatarNombre = null
        avatarUrl = ""
        // Solo borra token y username; el avatar se conserva en prefs para el próximo login
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
            .remove(KEY_TOKEN)
            .remove(KEY_USERNAME)
            .apply()
    }

    fun isLoggedIn() = token != null
}
