package com.joel.spiderbiblioteca.data.local

import android.content.Context

object ProfilePreferences {
    private const val PREFS = "profile_prefs"

    private fun prefs(ctx: Context) = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun getQuizzesCompletados(ctx: Context): Int = prefs(ctx).getInt("quizzes_completados", 0)

    fun incrementarQuizzes(ctx: Context) {
        prefs(ctx).edit().putInt("quizzes_completados", getQuizzesCompletados(ctx) + 1).apply()
    }

    fun getMejorPuntuacion(ctx: Context): Int = prefs(ctx).getInt("mejor_puntuacion", 0)

    fun actualizarMejorPuntuacion(ctx: Context, porcentaje: Int) {
        if (porcentaje > getMejorPuntuacion(ctx)) {
            prefs(ctx).edit().putInt("mejor_puntuacion", porcentaje).apply()
        }
    }

    fun getPersonajeFavorito(ctx: Context): String = prefs(ctx).getString("personaje_fav", "") ?: ""
    fun setPersonajeFavorito(ctx: Context, nombre: String) {
        prefs(ctx).edit().putString("personaje_fav", nombre).apply()
    }

    fun getTrajeFavorito(ctx: Context): String = prefs(ctx).getString("traje_fav", "") ?: ""
    fun setTrajeFavorito(ctx: Context, nombre: String) {
        prefs(ctx).edit().putString("traje_fav", nombre).apply()
    }

    fun getUniversoFavorito(ctx: Context): String = prefs(ctx).getString("universo_fav", "") ?: ""
    fun setUniversoFavorito(ctx: Context, nombre: String) {
        prefs(ctx).edit().putString("universo_fav", nombre).apply()
    }

    fun calcularRango(quizzesCompletados: Int, mejorPuntuacion: Int): String = when {
        quizzesCompletados == 0 -> "Recién Llegado"
        quizzesCompletados >= 16 && mejorPuntuacion >= 90 -> "Maestro del Spiderverso"
        quizzesCompletados >= 16 && mejorPuntuacion >= 80 -> "True Believer"
        quizzesCompletados >= 6 && mejorPuntuacion >= 70 -> "Spider-Experto"
        quizzesCompletados >= 1 && mejorPuntuacion >= 60 -> "Spider-Fan"
        else -> "Aprendiz del Spiderverso"
    }

    fun emojiRango(rango: String): String = when (rango) {
        "Maestro del Spiderverso" -> "🏆"
        "True Believer" -> "🕷️"
        "Spider-Experto" -> "⭐"
        "Spider-Fan" -> "🕸️"
        "Aprendiz del Spiderverso" -> "📖"
        else -> "🆕"
    }
}
