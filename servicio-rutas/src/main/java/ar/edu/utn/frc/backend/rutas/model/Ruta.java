package ar.edu.utn.frc.backend.rutas.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "RUTAS")
public class Ruta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ruta")
    private Long idRuta;

    @Column(name = "cantidad_tramos")
    private int cantidadTramos;

    @Column(name = "cantidad_depositos")
    private int cantidadDepositos;

    @Column(name = "id_solicitud")
    private Long idSolicitud;

    @Column(name = "distancia_total")
    private double distanciaTotal;

    @Column(name = "costo_estimado")
    private double costoEstimado;

    @Column(name = "tiempo_estimado")
    private double tiempoEstimado;

    @Column(name = "costo_real")
    private double costoReal;

    @Column(name = "tiempo_real")
    private double tiempoReal;

    @OneToMany(mappedBy = "ruta", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Tramo> tramos;

    @OneToMany(mappedBy = "ruta", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DetalleCostoRuta> detallesCostoRuta;
}
