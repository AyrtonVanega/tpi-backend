package ar.edu.utn.frc.backend.solicitudes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudResponseDto {
    private Long idSolicitud;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private double costoEstimado;
    private double tiempoEstimado;
    private double costoReal;
    private double tiempoReal;
    private String codigoEstadoSolicitud;
    private Long idContenedor;
    private Long idRuta;
    private String docCliente;
    private String tipoDocCliente;
}
