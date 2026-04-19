package com.example.parknow1.ui.admin.tarifas

object TarifaRepository {

    val listaTarifas = mutableListOf(
        Tarifa(1, "Hora", "$5.000", "Cobro por hora"),
        Tarifa(2, "Día", "$20.000", "Cobro por día")
    )

    fun agregar(t: Tarifa) {
        listaTarifas.add(t)
    }

    fun eliminar(t: Tarifa) {
        listaTarifas.remove(t)
    }
}