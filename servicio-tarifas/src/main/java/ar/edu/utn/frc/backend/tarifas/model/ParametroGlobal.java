package ar.edu.utn.frc.backend.tarifas.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "PARAMETROS_GLOBALES")
public class ParametroGlobal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parametro_global")
    private Long idParametro;

    @Column(name = "valor_litro_combustible")
    private double valorLitroCombustible;

    @Column(name = "costo_gestion_base")
    private double costoGestionBase;

    @Column(name = "ultima_modificacion")
    private LocalDate ultimaModificacion;
}
