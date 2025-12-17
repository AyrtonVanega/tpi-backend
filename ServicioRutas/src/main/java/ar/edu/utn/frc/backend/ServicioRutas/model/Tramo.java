package ar.edu.utn.frc.backend.ServicioRutas.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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

    @Column(name = "tipo")
    private TipoTramo tipoTramo;

    @Column
    private double distancia;

    private enum TipoTramo {
        ORIGEN_DEPOSITO,
        DEPOSITO_DEPOSITO,
        DEPOSITO_DESTINO,
        ORIGEN_DESTINO
    }
}
