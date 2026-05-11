package com.example.parknow1.ui.admin_disabled.reservations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parknow1.data.model.Reserva
import com.example.parknow1.data.repository.ParqueaderoRepository
import com.example.parknow1.data.repository.ReservaRepository
import com.example.parknow1.data.repository.UserRepository
import kotlinx.coroutines.launch

class ReservaViewModel : ViewModel() {

    val reservas = MutableLiveData<List<Reserva>>()

    fun load() {

        viewModelScope.launch {

            val reservasDB =
                ReservaRepository.obtenerReservas()

            val usuarios =
                UserRepository.obtenerUsuarios()

            val parqueaderos =
                ParqueaderoRepository.obtenerParqueaderos()

            reservasDB.forEach { reserva ->

                reserva.nombreCliente =
                    usuarios.find {
                        it.id == reserva.cliente
                    }?.nombres ?: "Sin cliente"

                reserva.nombreParqueadero =
                    parqueaderos.find {
                        it.id == reserva.parqueadero
                    }?.nombre ?: "Sin parqueadero"
            }

            reservas.value = reservasDB
        }
    }

    fun delete(id: String) {

        viewModelScope.launch {

            ReservaRepository.deleteReserva(id)

            load()
        }
    }
}