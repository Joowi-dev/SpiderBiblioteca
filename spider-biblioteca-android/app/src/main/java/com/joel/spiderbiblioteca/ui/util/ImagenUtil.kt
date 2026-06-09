package com.joel.spiderbiblioteca.ui.util

import android.content.Context
import java.text.Normalizer

fun resolverImagen(context: Context, nombre: String, urlFallback: String): Any {
    return resolverImagenConPrefijo(context, "char_", nombre, urlFallback) ?: urlFallback
}

fun resolverImagenConPrefijo(
    context: Context,
    prefijo: String,
    nombre: String,
    urlFallback: String?
): Any? {
    val normalizado = Normalizer.normalize(nombre, Normalizer.Form.NFD)
        .replace(Regex("\\p{InCombiningDiacriticalMarks}"), "")
    val key = prefijo + normalizado.lowercase()
        .replace(" ", "_")
        .replace("-", "_")
        .replace(".", "")
        .replace("'", "")
        .replace("!", "")
        .replace("/", "_")
        .replace("(", "")
        .replace(")", "")
        .replace(",", "")
    val resId = context.resources.getIdentifier(key, "drawable", context.packageName)
    return if (resId != 0) resId else urlFallback
}
