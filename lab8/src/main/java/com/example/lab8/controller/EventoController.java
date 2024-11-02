package com.example.lab8.controller;

import com.example.lab8.dao.EventoDao;
import com.example.lab8.entity.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoDao eventoDao;

    @GetMapping("/listarEventos")
    public List<Evento> listarEventos(@RequestParam(required = false) LocalDate fecha) {
        if (fecha != null) {

            return eventoDao.filtroFecha(fecha);
        }
        else{
            return eventoDao.findAll();
        }

    }
}
