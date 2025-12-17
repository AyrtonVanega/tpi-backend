package ar.edu.utn.frc.backend.depositos.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @PrimaryKeyJoinColumn indica que
 * la PK de depositos es la misma PK 
 * de ubicaciones
 */
@Entity
@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DEPOSITOS")
@PrimaryKeyJoinColumn(name = "id_deposito")
public class Deposito extends Ubicacion{

    @Column(name = "costo_estadia")
    private double costoEstadiaDiaria;

    @OneToMany(mappedBy = "deposito", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EstadiaDeposito> estadiasDeposito;
}
