package ar.edu.utn.frc.backend.ServicioPersonas.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PERSONAS")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Persona {

    // @EmbeddedId indica que la PK es un objeto con múltiples columnas
    @EmbeddedId
    private PersonaId idPersona;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String telefono;

    @Column
    private String email;
}
