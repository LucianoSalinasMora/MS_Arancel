package SigueTuCarrera.Arancel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Arancel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cuentaId;
    
    private String estudianteId;
    private Double arancelBaseAnual;
    private Double costoAcumuladoTotal;
    private Double saldoPendiente;
    private Integer semestresExtra;
    private Double factorReajuste;
    
    @Enumerated(EnumType.STRING)
    private EstadoCuenta estadoCuenta;

    public enum EstadoCuenta {
        AL_DIA, MOROSO, EN_CONVENIO
    }
}
