package ar.edu.utn.frc.backend.rutas.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TRAMOS")
public class Tramo {
    
    @EmbeddedId
    private TramoId idTramo;

    @ManyToOne
    @MapsId("idRuta")
    @JoinColumn(name = "id_ruta")
    private Ruta ruta;

    @Column(name = "id_ubicacion_origen")
    private Long idUbicacionOrigen;

    @Column(name = "id_ubicacion_destino")
    private Long idUbicacionDestino;

    @Column(name = "tipo_tramo")
    private TipoTramo tipoTramo;
    
    @Column
    private double distancia;

    @Column(name = "fecha_hora_inicio")
    private LocalDateTime fechaHoraInicio;

    @Column(name = "fecha_hora_fin")
    private LocalDateTime fechaHoraFin;

    @Column(name = "costo_estimado")
    private double costoEstimado;

    @Column(name = "costo_real")
    private double costoReal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado")
    private EstadoTramo estado;

    @Column(name = "patente_camion")
    private String patenteCamion;

    public enum TipoTramo {
        ORIGEN_DEPOSITO,
        DEPOSITO_DEPOSITO,
        DEPOSITO_DESTINO,
        ORIGEN_DESTINO
    }
}
