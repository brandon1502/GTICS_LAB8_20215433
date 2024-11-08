package com.example.lab8.repository;

import com.example.lab8.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
    @Query(value = "SELECT * FROM eventos e WHERE e.fecha >= :fecha ORDER BY e.fecha ASC", nativeQuery = true)
    List<Evento> filtrarPorFechas(@Param("fecha") LocalDate fecha);
}
