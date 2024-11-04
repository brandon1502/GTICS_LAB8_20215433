package com.example.lab8.controller;

import com.example.lab8.entity.Evento;
import com.example.lab8.entity.Reserva;
import com.example.lab8.repository.EventoRepository;
import com.example.lab8.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;


@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @PostMapping("/reservarCupoEnEvento")
    public ResponseEntity<HashMap<String, Object>> reservarCupoEnEvento(@RequestBody Reserva reserva) {

        HashMap<String, Object> responseJson = new HashMap<>();

        // Verificar que el evento exista

        Optional<Evento> optEvento = eventoRepository.findById(reserva.getEvento().getId());
        if (!optEvento.isPresent()) {
            responseJson.put("result", "error");
            responseJson.put("estado", "Error. El evento no existe.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseJson);
        }

        Evento evento = optEvento.get();

        // Validamos los cupos

        int cuposDisponibles = evento.getCapacidadmaxima() - evento.getReservasactuales();
        if (reserva.getNumero_cupos() > cuposDisponibles) {
            responseJson.put("result", "error");
            responseJson.put("estado", "Error. No hay suficientes cupos disponibles.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseJson);
        }

        // Si tod0 es correcto
        evento.setReservasactuales(evento.getReservasactuales() + reserva.getNumero_cupos());
        eventoRepository.save(evento);

        reserva.setEvento(evento);
        reservaRepository.save(reserva);

        responseJson.put("result", "ok");
        responseJson.put("estado", "Reserva realizada con éxito");
        responseJson.put("reservaId", reserva.getId());
        responseJson.put("nombre", reserva.getNombre_reserva());
        responseJson.put("correo", reserva.getCorreo_reserva());
        responseJson.put("numCupos", reserva.getNumero_cupos());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }

    @DeleteMapping("/cancelarReserva/{id}")
    public ResponseEntity<HashMap<String, Object>> cancelarReserva(@PathVariable Integer id) {

        HashMap<String, Object> responseJson = new HashMap<>();

        //Buscamos la reserva
        Optional<Reserva> optReserva = reservaRepository.findById(id);
        if (!optReserva.isPresent()) {
            responseJson.put("result", "error");
            responseJson.put("estado", "Error. La reserva no existe.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseJson);
        }

        Reserva reserva = optReserva.get();
        Evento evento = reserva.getEvento();

        // Actualizar el número de reservas actuales
        int nuevasReservasActuales = evento.getReservasactuales() - reserva.getNumero_cupos();

        evento.setReservasactuales(nuevasReservasActuales);
        eventoRepository.save(evento);

        // Eliminar la reserva
        reservaRepository.delete(reserva);

        // Respuesta exitosa
        responseJson.put("result", "ok");
        responseJson.put("estado", "Reserva cancelada con éxito");
        responseJson.put("eventoId", evento.getId());
        responseJson.put("nombreEvento", evento.getNombre());
        responseJson.put("reservasActuales", evento.getReservasactuales());

        return ResponseEntity.status(HttpStatus.OK).body(responseJson);
    }
}
