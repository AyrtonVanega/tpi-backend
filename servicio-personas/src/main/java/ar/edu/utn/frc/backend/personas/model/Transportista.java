package ar.edu.utn.frc.backend.personas.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.PrimaryKeyJoinColumns;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "TRANSPORTISTAS")
@PrimaryKeyJoinColumns({
    @PrimaryKeyJoinColumn(name = "doc", referencedColumnName = "doc"),
    @PrimaryKeyJoinColumn(name = "tipo_doc", referencedColumnName = "tipo_doc")
})
public class Transportista extends Persona {

    @OneToOne(mappedBy = "transportista")
    private Camion camion;

    @Column(name = "vencimiento_licencia")
    private LocalDateTime vencimientoLicencia;
}
