package ar.edu.utn.frc.backend.tarifas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "CAMIONES_VIEW")
public class CamionView {

    @Id
    @Column
    private String patente;

    @Column
    private double peso;

    @Column
    private double volumen;

    @Column(name = "consumo_combustible_promedio")
    private double consumoCombustiblePromedio;
}
