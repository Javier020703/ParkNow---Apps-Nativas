package com.example.parknow1.data.repository

import com.example.parknow1.data.model.Parqueadero
import com.example.parknow1.data.remote.SupabaseClient

import io.github.jan.supabase.postgrest.postgrest

import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.add

object ParqueaderoRepository {

    // =========================
    // OBTENER TODOS
    // =========================
    suspend fun obtenerParqueaderos(): List<Parqueadero> {

        return try {

            SupabaseClient.client
                .postgrest["parqueaderos"]
                .select()
                .decodeList<Parqueadero>()

        } catch (e: Exception) {

            e.printStackTrace()
            emptyList()
        }
    }

    // =========================
    // OBTENER UNO
    // =========================
    suspend fun obtenerParqueadero(
        id: String
    ): Parqueadero? {

        return try {

            val resultado =
                SupabaseClient.client
                    .postgrest["parqueaderos"]
                    .select {

                        filter {
                            eq("id", id)
                        }
                    }
                    .decodeList<Parqueadero>()

            resultado.firstOrNull()

        } catch (e: Exception) {

            e.printStackTrace()
            null
        }
    }

    // =========================
    // CREAR
    // =========================
    suspend fun crearParqueadero(
        parqueadero: Parqueadero
    ) {

        SupabaseClient.client
            .postgrest["parqueaderos"]
            .insert(parqueadero)
    }

    // =========================
    // ACTUALIZAR
    // =========================
    suspend fun actualizarParqueadero(
        parqueadero: Parqueadero
    ) {

        val datos = buildJsonObject {

            put("nombre", parqueadero.nombre)

            put(
                "direccion",
                parqueadero.direccion
            )

            put(
                "descripcion",
                parqueadero.descripcion
            )

            put(
                "cupos_totales",
                parqueadero.cupos_totales
            )

            put(
                "cupos_disponibles",
                parqueadero.cupos_disponibles
            )

            put(
                "tarifa_hora",
                parqueadero.tarifa_hora
            )

            // LISTA text[]
            put(
                "caracteristicas",
                buildJsonArray {

                    (parqueadero.caracteristicas ?: emptyList()).forEach {
                        add(it)
                    }
                }
            )

            put(
                "latitud",
                parqueadero.latitud
            )

            put(
                "longitud",
                parqueadero.longitud
            )

            put(
                "estado",
                parqueadero.estado
            )
        }

        SupabaseClient.client
            .postgrest["parqueaderos"]
            .update(datos) {

                filter {

                    eq(
                        "id",
                        parqueadero.id!!
                    )
                }
            }
    }

    // =========================
    // ELIMINAR
    // =========================
    suspend fun eliminarParqueadero(
        id: String
    ) {

        SupabaseClient.client
            .postgrest["parqueaderos"]
            .delete {

                filter {

                    eq("id", id)
                }
            }
    }
}