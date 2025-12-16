package ar.edu.utn.frc.backend.ServicioPersonas.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.PrimaryKeyJoinColumns;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CLIENTES")
@PrimaryKeyJoinColumns({
    @PrimaryKeyJoinColumn(name = "doc", referencedColumnName = "doc"),
    @PrimaryKeyJoinColumn(name = "tipo_doc", referencedColumnName = "tipo_doc")
})
public class Transportista extends Persona {
    private LocalDateTime vencimientoLicencia;
}
