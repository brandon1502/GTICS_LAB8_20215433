package com.example.lab8.controller;

import com.example.lab8.repository.EventoRepository;
import com.example.lab8.entity.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @GetMapping("/listarEventos")
    public List<Evento> listarEventos(@RequestParam(value = "fecha", required = false) LocalDate fecha) {
        if (fecha != null) {
            return eventoRepository.filtrarPorFechas(fecha);
        } else {
            return eventoRepository.findAll();
        }
    }

    @PostMapping("/crearEvento")
    public ResponseEntity<HashMap<String, Object>> guardarProducto(@RequestBody Evento evento,
                                                                    @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseJson = new HashMap<>();

        //validar que la fecha sea futura
        if (evento.getFecha().isBefore(LocalDate.now())) {
            responseJson.put("result","error");
            responseJson.put("estado", "Error.La fecha ingresada debe ser futura");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseJson);
        }

        //además inicializamos reservas_actuales = 0

        evento.setReservasactuales(0);
        eventoRepository.save(evento);

        if (fetchId) {
            responseJson.put("id", evento.getId());
        }
        responseJson.put("estado", "creado!");
        responseJson.put("result","ok");

        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);

    }
}
