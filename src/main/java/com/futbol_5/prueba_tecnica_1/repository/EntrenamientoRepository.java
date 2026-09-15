package com.futbol_5.prueba_tecnica_1.repository;

import com.futbol_5.prueba_tecnica_1.entity.Entrenamiento;
import com.futbol_5.prueba_tecnica_1.entity.Jugador;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrenamientoRepository extends JpaRepository<Entrenamiento, Long> {
    List<Entrenamiento> findByJugadorAndSemana(Jugador jugador, int semana);

}