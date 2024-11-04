package com.example.lab8.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva", nullable = false)
    private Integer id;

    @Column(name = "nombre_reserva")
    private String nombre_reserva;

    @Column(name = "correo_reserva")
    private String correo_reserva;

    @Column(name = "numero_cupos", nullable = false)
    private Integer numero_cupos;

    @ManyToOne
    @JoinColumn(name = "id_evento")
    private Evento evento;
}