package ar.edu.utn.frc.backend.solicitudes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {
    private String doc;
    private String tipoDoc;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
}
