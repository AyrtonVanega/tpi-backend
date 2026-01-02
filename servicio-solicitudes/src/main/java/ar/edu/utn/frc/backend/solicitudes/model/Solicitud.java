package ar.edu.utn.frc.backend.solicitudes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SOLICITUDES")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud")
    private Long idSolicitud;

    @Column(name = "id_ubicacion_origen")
    private Long idUbicacionOrigen;
    
    @Column(name = "id_ubicacion_destino")
    private Long idUbicacionDestino;

    @Column(name = "fecha_hora_inicio")
    private LocalDateTime fechaHoraInicio;

    @Column(name = "fecha_hora_fin")
    private LocalDateTime fechaHoraFin;

    @Column(name = "costo_estimado")
    private double costoEstimado;

    @Column(name = "tiempo_estimado")
    private double tiempoEstimado;

    @Column(name = "costo_real")
    private double costoReal;

    @Column(name = "tiempo_real")
    private double tiempoReal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado")
    private EstadoSolicitud estadoSolicitud;

    @OneToOne
    @JoinColumn(name = "id_contenedor")
    private Contenedor contenedor;

    @Column(name = "doc_cliente")
    private String docCliente;
    
    @Column(name = "tipo_doc_cliente")
    private String tipoDocCliente;
    
    //private Long idRuta;
}
