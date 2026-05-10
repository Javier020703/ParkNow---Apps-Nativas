package com.example.parknow1.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log

import com.example.parknow1.data.model.User
import com.example.parknow1.data.remote.SupabaseClient

import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.storage.storage

import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

object UserRepository {

    // Verificar si existe usuario
    suspend fun existeUsuario(userId: String): Boolean {

        return try {

            val resultado = SupabaseClient.client
                .postgrest["usuarios"]
                .select(Columns.raw("id")) {

                    filter {
                        eq("id", userId)
                    }
                }
                .decodeList<Map<String, String>>()

            resultado.isNotEmpty()

        } catch (e: Exception) {

            false
        }
    }

    // Insertar usuario
    suspend fun insertarUsuario(
        id: String,
        nombres: String,
        apellidos: String,
        correo: String,
        telefono: String
    ) {

        SupabaseClient.client
            .postgrest["usuarios"]
            .insert(

                User(
                    id = id,
                    nombres = nombres,
                    apellidos = apellidos,
                    correo = correo,
                    telefono = telefono
                )
            )
    }

    // Obtener usuario actual
    suspend fun obtenerUsuario(): User? {

        val userId =
            SupabaseClient.client.auth.currentUserOrNull()?.id
                ?: return null

        return try {

            val resultado = SupabaseClient.client
                .postgrest["usuarios"]
                .select {

                    filter {
                        eq("id", userId)
                    }
                }
                .decodeList<User>()

            Log.d("DEBUG_USER", "Usuario: $resultado")

            resultado.firstOrNull()

        } catch (e: Exception) {

            Log.e("DEBUG_USER", "Error: ${e.message}")

            null
        }
    }

    // Obtener rol actual
    suspend fun obtenerRolActual(): String {

        return try {

            val userId =
                SupabaseClient.client.auth.currentUserOrNull()?.id
                    ?: return "cliente"

            val resultado = SupabaseClient.client
                .postgrest["usuarios"]
                .select {

                    filter {
                        eq("id", userId)
                    }
                }
                .decodeList<User>()

            resultado.firstOrNull()?.rol ?: "cliente"

        } catch (e: Exception) {

            "cliente"
        }
    }

    // Actualizar perfil
    suspend fun actualizarPerfil(
        nombres: String,
        apellidos: String,
        correo: String,
        fotoUrl: String? = null
    ) {

        val userId =
            SupabaseClient.client.auth.currentUserOrNull()?.id
                ?: return

        val datos = buildJsonObject {

            put("nombres", nombres)
            put("apellidos", apellidos)
            put("correo", correo)

            if (fotoUrl != null) {
                put("foto_url", fotoUrl)
            }
        }

        SupabaseClient.client
            .postgrest["usuarios"]
            .update(datos) {

                filter {
                    eq("id", userId)
                }
            }
    }

    // Subir foto perfil
    suspend fun subirFotoPerfil(
        contexto: Context,
        uri: Uri
    ): String {

        val userId =
            SupabaseClient.client.auth.currentUserOrNull()?.id
                ?: return ""

        return try {

            val bytes = if (uri.scheme == "content") {

                contexto.contentResolver
                    .openInputStream(uri)
                    ?.readBytes()

            } else {

                java.io.File(uri.path!!).readBytes()
            } ?: return ""

            val rutaArchivo = "perfil_$userId.jpg"

            // Subir a storage
            SupabaseClient.client
                .storage["avatars"]
                .upload(
                    path = rutaArchivo,
                    data = bytes
                ) {

                    upsert = true
                }

            // Obtener URL pública
            SupabaseClient.client
                .storage["avatars"]
                .publicUrl(rutaArchivo)

        } catch (e: Exception) {

            Log.e("DEBUG_FOTO", "Error: ${e.message}")

            ""
        }
    }
}