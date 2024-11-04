package com.example.lab8.repository;

import com.example.lab8.entity.Evento;
import com.example.lab8.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
}
