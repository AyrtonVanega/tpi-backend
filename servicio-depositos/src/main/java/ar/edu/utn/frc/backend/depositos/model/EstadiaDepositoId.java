package ar.edu.utn.frc.backend.depositos.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * EstadiaDepositoId representa la PK completa 
 * como un objeto unico
 * 
 * La interfaz Serializable 
 * permite que JPA mueva la clave entre 
 * sesiones/caches.
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadiaDepositoId implements Serializable {

    @Column(name = "id_deposito")
    private Long idDeposito;

    @Column(name = "id_solicitud")
    private Long idSolicitud;
}
