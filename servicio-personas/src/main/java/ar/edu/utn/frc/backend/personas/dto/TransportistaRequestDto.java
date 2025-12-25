package ar.edu.utn.frc.backend.personas.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportistaRequestDto {
    private String docTransportista;
    private String tipoDocTransportista;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private LocalDateTime vencimientoLicencia;

    private String patenteCamion;
    private double volumenCamion;
    private double pesoCamion;
    private double costoBaseKmCamion;
    private double consumoCombustiblePromedioCamion;
}
