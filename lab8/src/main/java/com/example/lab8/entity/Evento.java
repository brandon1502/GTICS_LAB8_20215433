package com.example.lab8.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name="eventos")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento", nullable = false)
    private Integer id;

    @Column(name = "nombre", length = 45)
    private String nombre;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "categoria", length = 45)
    private String categoria;

    @Column(name="capacidad_maxima")
    private Integer capacidadmaxima;

    @Column(name="reservas_actuales")
    private int reservasactuales;
}
