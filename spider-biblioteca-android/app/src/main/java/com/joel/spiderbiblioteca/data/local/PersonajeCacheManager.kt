package com.joel.spiderbiblioteca.data.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joel.spiderbiblioteca.data.model.Personaje

object PersonajeCacheManager {

    private const val PREFS_NAME = "spider_cache"
    private const val KEY_PERSONAJES = "personajes_json"
    private val gson = Gson()

    fun guardar(context: Context, lista: List<Personaje>) {
        val json = gson.toJson(lista)
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().putString(KEY_PERSONAJES, json).apply()
    }

    fun cargar(context: Context): List<Personaje> {
        val json = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_PERSONAJES, null) ?: return emptyList()
        return try {
            val type = object : TypeToken<List<Personaje>>() {}.type
            gson.fromJson<List<Personaje>>(json, type) ?: emptyList()
        } catch (_: Exception) {
            emptyList()
        }
    }
}
