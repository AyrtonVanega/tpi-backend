package ar.edu.utn.frc.backend.solicitudes.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequestDto {
    private String docCliente;
    private String tipoDocCliente;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
}
