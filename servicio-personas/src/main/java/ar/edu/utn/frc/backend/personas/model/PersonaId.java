package ar.edu.utn.frc.backend.personas.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PersonaId representa la PK completa 
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
public class PersonaId implements Serializable {
    
    @Column
    private String doc;

    @Column(name = "tipo_doc")
    private String tipoDoc;
}
