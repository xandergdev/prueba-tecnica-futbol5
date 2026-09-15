package com.futbol_5.prueba_tecnica_1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.futbol_5.prueba_tecnica_1.entity.Entrenamiento;
import com.futbol_5.prueba_tecnica_1.service.EntrenamientoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/entrenamientos")
@RequiredArgsConstructor

public class EntrenamientoController {
    private final EntrenamientoService entrenamientoService;

    @PostMapping
    public ResponseEntity<Entrenamiento> registrar(@RequestBody Entrenamiento entrenamiento) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(entrenamientoService.registroEntrenamiento(entrenamiento));
    }

    @GetMapping("/titulares")
    public ResponseEntity<?> obtenerTitulares(@RequestParam int semana) {
        return entrenamientoService.obtenerTodos(semana);
    }

}
