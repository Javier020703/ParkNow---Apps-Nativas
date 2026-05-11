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
import java.io.File

object UserRepository {

    // EXISTE USUARIO

    suspend fun existeUsuario(userId: String): Boolean {
        return try {

            val resultado = SupabaseClient.client
                .postgrest["usuarios"]
                .select(Columns.raw("id")) {
                    filter { eq("id", userId) }
                }
                .decodeList<Map<String, String>>()

            resultado.isNotEmpty()

        } catch (e: Exception) {
            false
        }
    }


    // INSERTAR USUARIO (legacy)

    suspend fun insertarUsuario(
        id: String,
        nombres: String,
        apellidos: String,
        correo: String,
        telefono: String,
        rol: String = "cliente"
    ) {

        SupabaseClient.client
            .postgrest["usuarios"]
            .insert(
                User(
                    id = id,
                    nombres = nombres,
                    apellidos = apellidos,
                    correo = correo,
                    telefono = telefono,
                    rol = rol
                )
            )
    }

    // OBTENER USUARIO LOGUEADO

    suspend fun obtenerUsuario(): User? {

        val userId =
            SupabaseClient.client.auth.currentUserOrNull()?.id ?: return null

        return try {

            SupabaseClient.client
                .postgrest["usuarios"]
                .select {
                    filter { eq("id", userId) }
                }
                .decodeList<User>()
                .firstOrNull()

        } catch (e: Exception) {

            Log.e("DEBUG_USER", "Error: ${e.message}")
            null
        }
    }

    // OBTENER ROL

    suspend fun obtenerRolActual(): String {

        val userId =
            SupabaseClient.client.auth.currentUserOrNull()?.id ?: return "cliente"

        return try {

            SupabaseClient.client
                .postgrest["usuarios"]
                .select {
                    filter { eq("id", userId) }
                }
                .decodeList<User>()
                .firstOrNull()
                ?.rol ?: "cliente"

        } catch (e: Exception) {
            "cliente"
        }
    }

    // ACTUALIZAR PERFIL PROPIO

    suspend fun actualizarPerfil(
        nombres: String,
        apellidos: String,
        correo: String,
        fotoUrl: String? = null
    ) {

        val userId =
            SupabaseClient.client.auth.currentUserOrNull()?.id ?: return

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
                filter { eq("id", userId) }
            }
    }

    // SUBIR FOTO PERFIL

    suspend fun subirFotoPerfil(
        contexto: Context,
        uri: Uri
    ): String {

        val userId =
            SupabaseClient.client.auth.currentUserOrNull()?.id ?: return ""

        return try {

            val bytes = if (uri.scheme == "content") {
                contexto.contentResolver.openInputStream(uri)?.readBytes()
            } else {
                File(uri.path!!).readBytes()
            } ?: return ""

            val rutaArchivo = "perfil_$userId.jpg"

            SupabaseClient.client
                .storage["avatars"]
                .upload(path = rutaArchivo, data = bytes) {
                    upsert = true
                }

            SupabaseClient.client
                .storage["avatars"]
                .publicUrl(rutaArchivo)

        } catch (e: Exception) {
            Log.e("DEBUG_FOTO", "Error: ${e.message}")
            ""
        }
    }

    // CRUD ADMIN (USUARIOS)

    suspend fun obtenerUsuarios(): List<User> {
        return SupabaseClient.client
            .postgrest["usuarios"]
            .select()
            .decodeList()
    }

    suspend fun obtenerUsuarioPorId(id: String): User? {
        return SupabaseClient.client
            .postgrest["usuarios"]
            .select {
                filter { eq("id", id) }
            }
            .decodeList<User>()
            .firstOrNull()
    }

    suspend fun insertarUsuario(user: User) {
        SupabaseClient.client
            .postgrest["usuarios"]
            .insert(user)
    }

    suspend fun actualizarUsuario(user: User) {

        val datos = buildJsonObject {
            put("nombres", user.nombres)
            put("apellidos", user.apellidos)
            put("correo", user.correo)
            put("telefono", user.telefono)
            put("rol", user.rol)
        }

        SupabaseClient.client
            .postgrest["usuarios"]
            .update(datos) {
                filter { eq("id", user.id) }
            }
    }

    suspend fun eliminarUsuario(id: String) {
        SupabaseClient.client
            .postgrest["usuarios"]
            .delete {
                filter { eq("id", id) }
            }
    }
}