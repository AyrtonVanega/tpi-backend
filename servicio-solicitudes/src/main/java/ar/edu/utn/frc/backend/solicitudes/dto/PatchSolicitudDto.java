package ar.edu.utn.frc.backend.solicitudes.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchSolicitudDto {
    private LocalDateTime fechaHoraFin;
    private double costoEstimado;
    private double tiempoEstimado;
    private double costoReal;
    private double tiempoReal;
    private String codigoEstadoSolicitud;
    private Long idRuta;
}
