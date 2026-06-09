package com.joel.spiderbiblioteca.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.joel.spiderbiblioteca.data.api.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "favoritos")

class FavoritosRepository(private val context: Context) {

    private val KEY_FAVORITOS = stringSetPreferencesKey("ids_favoritos")
    private val api = RetrofitInstance.favoritoApi

    val favoritosFlow: Flow<Set<Long>> = context.dataStore.data.map { prefs ->
        prefs[KEY_FAVORITOS]?.mapNotNull { it.toLongOrNull() }?.toSet() ?: emptySet()
    }

    suspend fun sincronizarDesdeBackend() {
        val personajes = api.obtenerFavoritos()
        val ids = personajes.mapNotNull { it.id }.map { it.toString() }.toSet()
        context.dataStore.edit { prefs ->
            prefs[KEY_FAVORITOS] = ids
        }
    }

    suspend fun toggleFavorito(id: Long) {
        context.dataStore.edit { prefs ->
            val actual = prefs[KEY_FAVORITOS]?.toMutableSet() ?: mutableSetOf()
            val idStr = id.toString()
            if (idStr in actual) actual.remove(idStr) else actual.add(idStr)
            prefs[KEY_FAVORITOS] = actual
        }
    }

    suspend fun agregarRemoto(id: Long) {
        api.agregar(id)
    }

    suspend fun eliminarRemoto(id: Long) {
        api.eliminar(id)
    }
}
