package ar.edu.utn.frc.backend.rutas.model;

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
@Table(name = "TARIFAS")
public class Tarifa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarifa")
    private Long idTarifa;

    @Column(name = "rango_peso_min")
    private double rangoPesoMin;

    @Column(name = "rango_peso_max")
    private double rangoPesoMax;

    @Column(name = "rango_volumen_min")
    private double rangoVolumenMin;

    @Column(name = "rango_volumen_max")
    private double rangoVolumenMax;

    @Column(name = "valor_litro_combustible")
    private double valorLitroCombustible;

    @Column(name = "costo_gestion_base")
    private double costoGestionBase;

    @Column(name = "costo_base_km_volumen")
    private double costoBaseKmVolumen;

    @Column(name = "consumo_combustible_general_aprox")
    private double consumoCombustibleGralAprox;
}
