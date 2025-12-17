package ar.edu.utn.frc.backend.personas.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransportistaResponseDto {
    private String doc;
    private char tipoDoc;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
}
