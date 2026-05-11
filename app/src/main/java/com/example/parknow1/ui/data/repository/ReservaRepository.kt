package com.example.parknow1.data.repository

import com.example.parknow1.data.model.Reserva
import com.example.parknow1.data.remote.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

object ReservaRepository {

    // OBTENER TODAS
    suspend fun obtenerReservas(): List<Reserva> {
        return try {
            SupabaseClient.client
                .postgrest["reservas"]
                .select()
                .decodeList<Reserva>()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    // CREAR
    suspend fun insertReserva(reserva: Reserva) {
        SupabaseClient.client
            .postgrest["reservas"]
            .insert(reserva)
    }

    // ELIMINAR
    suspend fun deleteReserva(id: String) {
        SupabaseClient.client
            .postgrest["reservas"]
            .delete {
                filter {
                    eq("id", id)
                }
            }
    }

    // OBTENER UNA
    suspend fun obtenerReserva(id: String): Reserva? {
        return try {
            SupabaseClient.client
                .postgrest["reservas"]
                .select {
                    filter {
                        eq("id", id)
                    }
                }
                .decodeList<Reserva>()
                .firstOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun updateReserva(
        reserva: Reserva
    ) {

        SupabaseClient.client
            .postgrest["reservas"]
            .update({

                set("cliente", reserva.cliente)
                set("parqueadero", reserva.parqueadero)
                set("espacio", reserva.espacio)
                set("hora", reserva.hora)
                set("estado", reserva.estado)

            }) {

                filter {
                    eq("id", reserva.id!!)
                }
            }
    }
}