package ar.edu.utn.frc.backend.solicitudes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSolicitudDto {

    private String direccionUbicacionOrigen;
    private double latitudUbicacionOrigen;
    private double longitudUbicacionOrigen;
    private String nombreCiudadUbicacionOrigen;

    private String direccionUbicacionDestino;
    private double latitudUbicacionDestino;
    private double longitudUbicacionDestino;
    private String nombreCiudadUbicacionDestino;

    private double anchoContenedor;
    private double largoContenedor;
    private double alturaContenedor;
    private double pesoContenedor;

    private String docCliente;
    private String tipoDocCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String telefonoCliente;
    private String emailCliente;
}
