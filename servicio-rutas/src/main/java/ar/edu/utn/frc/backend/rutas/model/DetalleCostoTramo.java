package ar.edu.utn.frc.backend.rutas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
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
@Table(name = "DETALLES_COSTO_TRAMO")
public class DetalleCostoTramo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetalle;

    @Column
    private String concepto;
    
    @Column(name = "sub_total")
    private double subTotal;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "id_ruta", referencedColumnName = "id_ruta"),
        @JoinColumn(name = "orden", referencedColumnName = "orden")
    })
    private Tramo tramo;
}
