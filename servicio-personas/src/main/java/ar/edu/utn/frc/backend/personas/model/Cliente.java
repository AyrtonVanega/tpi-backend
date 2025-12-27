package ar.edu.utn.frc.backend.personas.model;

import java.util.List;

import jakarta.persistence.Entity;
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
@Table(name = "CLIENTES")
@PrimaryKeyJoinColumns({
    @PrimaryKeyJoinColumn(name = "doc", referencedColumnName = "doc"),
    @PrimaryKeyJoinColumn(name = "tipo_doc", referencedColumnName = "tipo_doc")
})
public class Cliente extends Persona {

    
    private List<Long> idSolicitudes;
}
