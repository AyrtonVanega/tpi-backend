package ar.edu.utn.frc.backend.ServicioPersonas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.OneToOne;
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
@Table(name = "CAMIONES")
public class Camion {
    
    @Id
    @Column
    private String patente;

    @Column
    private double volumen;

    @Column
    private double peso;

    @Column
    private boolean disponibilidad;

    @Column(name = "costo_base_km")
    private double costoBaseKm;

    @Column(name = "consumo_combustible_promedio")
    private double consumoCombustiblePromedio;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "doc_transportista", referencedColumnName = "doc"),
        @JoinColumn(name = "tipo_doc_transportista", referencedColumnName = "tipo_doc")
    })
    private Transportista transportista;
}
