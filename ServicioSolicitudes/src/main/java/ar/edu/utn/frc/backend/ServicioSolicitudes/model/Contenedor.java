package ar.edu.utn.frc.backend.ServicioSolicitudes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CONTENEDORES")
public class Contenedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contenedor")
    private Long idContenedor;

    @Column
    private double ancho;

    @Column
    private double alto;

    @Column
    private double largo;

    @Column
    private double peso;

    @OneToOne(mappedBy = "contenedor")
    private Solicitud solicitud;

    @OneToMany(mappedBy = "contenedor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<HistorialEstadoContenedor> historialesEstadoContenedor;

    public double calcularVolumen() {
        return ancho * alto * largo;
    }
}
