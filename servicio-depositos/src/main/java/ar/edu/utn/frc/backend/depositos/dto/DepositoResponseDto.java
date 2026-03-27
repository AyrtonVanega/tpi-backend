package ar.edu.utn.frc.backend.depositos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositoResponseDto {
    private UbicacionResponseDto ubicacion;
    private double costoEstadiaDiaria;
}
