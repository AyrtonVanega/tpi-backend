package ar.edu.utn.frc.backend.depositos.model;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ESTADIAS_DEPOSITO")
public class EstadiaDeposito {

    // @EmbeddedId indica que la PK es un objeto con múltiples columnas
    @EmbeddedId
    private EstadiaDepositoId idEstadiaDeposito;

    /**
     * @MapsId
     * 1. Vincula parte del objeto PK con una relación ManyToOne.
     * 2. Sincroniza FK y PK automáticamente.
     * 
     * De esta forma
     * estadia.idEstadiaDeposito.idDeposito  ==  estadia.deposito.getId()
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idDeposito")
    @JoinColumn(name = "id_deposito")
    private Deposito deposito;

    // No se usa @MapsId porque la entidad Solicitud esta en otro MicroServicio, con otra DB
    @Column(name = "id_solicitud", insertable = false, updatable = false)
    private Long idSolicitud;

    @Column(name = "fecha_hora_entrada")
    private LocalDateTime fechaHoraEntrada;

    @Column(name = "fecha_hora_salida")
    private LocalDateTime fechaHoraSalida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado")
    private EstadoEstadiaDeposito estado;
}