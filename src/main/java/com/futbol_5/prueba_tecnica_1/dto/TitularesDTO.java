package com.futbol_5.prueba_tecnica_1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class TitularesDTO {

    private String nombre;
    private double potencia;
    private double pases;
    private double velocidad;
    private double promedio;

}
