package com.futbol_5.prueba_tecnica_1.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.futbol_5.prueba_tecnica_1.dto.TitularesDTO;
import com.futbol_5.prueba_tecnica_1.entity.Entrenamiento;
import com.futbol_5.prueba_tecnica_1.entity.Jugador;
import com.futbol_5.prueba_tecnica_1.repository.EntrenamientoRepository;
import com.futbol_5.prueba_tecnica_1.repository.JugadorRepository;
import java.util.Comparator;

import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor

public class EntrenamientoService {

    private final EntrenamientoRepository entrenamientoRepository;
    private final JugadorRepository jugadorRepository;

    public Entrenamiento registroEntrenamiento(Entrenamiento entrenamiento) {

        int potencia = entrenamiento.getPotencia();
        int pases = entrenamiento.getPases();
        int velocidad = entrenamiento.getVelocidad();

        double resultado = potencia * 0.20 + pases * 0.50 + velocidad * 0.30;

        entrenamiento.setResultado(resultado);

        return entrenamientoRepository.save(entrenamiento);
    }

    public ResponseEntity<?> obtenerTodos(int semana) {
        List<Jugador> listajugadores = jugadorRepository.findAll();
        List<TitularesDTO> listatitulare = new java.util.ArrayList<>();

        for (Jugador jugador : listajugadores) {
            List<Entrenamiento> entrenamientos = entrenamientoRepository.findByJugadorAndSemana(jugador, semana);
            if (entrenamientos.size() != 3) {
                return ResponseEntity.badRequest().body(
                        "No hay suficiente información: no todos los jugadores completaron los 3 entrenamientos de la semana.");
            }
            double totalPotencia = 0;
            for (Entrenamiento entrenamiento : entrenamientos) {

                totalPotencia = totalPotencia + entrenamiento.getPotencia();
            }
            double promedioPotencia = totalPotencia / entrenamientos.size();

            double totalPases = 0;
            for (Entrenamiento entrenamiento : entrenamientos) {

                totalPases = totalPases + entrenamiento.getPases();
            }
            double promedioPases = totalPases / entrenamientos.size();

            double totalVelocidad = 0;
            for (Entrenamiento entrenamiento : entrenamientos) {

                totalVelocidad = totalVelocidad + entrenamiento.getVelocidad();
            }
            double promedioVelocidad = totalVelocidad / entrenamientos.size();

            double totalResultado = 0;
            for (Entrenamiento entrenamiento : entrenamientos) {

                totalResultado = totalResultado + entrenamiento.getResultado();
            }
            double promedioResultado = totalResultado / entrenamientos.size();

            TitularesDTO dto = new TitularesDTO(jugador.getNombre(), promedioPotencia, promedioPases, promedioVelocidad,
                    promedioResultado);
            listatitulare.add(dto);
        }
        listatitulare.sort(Comparator.comparingDouble(TitularesDTO::getPromedio).reversed());
        List<TitularesDTO> titulares = listatitulare.subList(0, 5);
        return ResponseEntity.ok(titulares);

    }

}
