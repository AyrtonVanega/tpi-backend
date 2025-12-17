package ar.edu.utn.frc.backend.solicitudes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ESTADOS_SOLICITUD")
public class EstadoSolicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private Long idEstadoSolicitud;

    @Column
    private String codigo;

    @Column
    private String descripcion;

    @OneToMany(mappedBy = "estadoSolicitud", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Solicitud> solicitudes;
}
