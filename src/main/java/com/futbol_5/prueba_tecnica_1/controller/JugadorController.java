package com.futbol_5.prueba_tecnica_1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.futbol_5.prueba_tecnica_1.entity.Jugador;
import com.futbol_5.prueba_tecnica_1.repository.JugadorRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/jugadores")
@RequiredArgsConstructor
public class JugadorController {

    private final JugadorRepository jugadorRepository;

    @PostMapping
    public ResponseEntity<Jugador> registrar(@RequestBody Jugador jugador) {
    return ResponseEntity.status(HttpStatus.CREATED).body(jugadorRepository.save(jugador));
    }

    
}
