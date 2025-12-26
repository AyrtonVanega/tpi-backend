package ar.edu.utn.frc.backend.rutas.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TramoId representa la PK completa 
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
public class TramoId implements Serializable {
    
    @Column(name = "id_ruta")
    private Long idRuta;

    @Column
    private Integer orden;
}
