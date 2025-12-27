package ar.edu.utn.frc.backend.personas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PutClienteDto {
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
}
