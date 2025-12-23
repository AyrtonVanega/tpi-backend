package ar.edu.utn.frc.backend.depositos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @Inheritance Define el uso de herencia.
 * Cada subclase tendrá su propia tabla, 
 * pero compartiendo el ID.
 */
@Entity
@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "UBICACIONES")
@Inheritance(strategy = InheritanceType.JOINED)
public class Ubicacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ubicacion")
    private Long id;

    @Column
    private String direccion;
    
    @Column
    private double latitud;
   
    @Column
    private double longitud;
    
    @Column(name = "nombre_ciudad")
    private String nombreCiudad;
}
