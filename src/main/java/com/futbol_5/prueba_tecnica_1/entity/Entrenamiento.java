package com.futbol_5.prueba_tecnica_1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data

public class Entrenamiento {
    @ManyToOne
    @JoinColumn(name = "jugador_id")
    private Jugador jugador;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    private int semana;
    private int potencia;
    private int velocidad;
    private int pases;
    private double resultado;

}
