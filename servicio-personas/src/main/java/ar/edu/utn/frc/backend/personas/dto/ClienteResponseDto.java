package ar.edu.utn.frc.backend.personas.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseDto {
    private String docCliente;
    private char tipoDocCliente;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private List<Long> idSolicitudes;
}
