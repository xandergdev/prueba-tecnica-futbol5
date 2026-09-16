package com.futbol_5.prueba_tecnica_1.dto;

import lombok.Data;

@Data 
public class EntrenamientoRequestDTO {
    private Long jugadorId;
    private int semana;
    private int potencia;
    private int velocidad;
    private int pases;
    
}
