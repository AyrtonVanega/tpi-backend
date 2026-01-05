package ar.edu.utn.frc.backend.rutas.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TramoResponseDto {
    private int orden;
    private Long idUbicacionOrigen;
    private Long idUbicacionDestino;
    private String tipo;
    private double distancia;
    private double costoEstimado;
    private double costoReal;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private String codigoEstado;
    private String patenteCamion;
}
