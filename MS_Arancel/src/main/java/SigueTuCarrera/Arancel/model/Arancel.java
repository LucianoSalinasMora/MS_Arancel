package SigueTuCarrera.Arancel.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@Schema(description = "Modelo que representa la cuenta de aranceles y la situación financiera de un estudiante")
public class Arancel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la cuenta de arancel", example = "1054")
    private Long cuentaId;
    
    @Schema(description = "Identificador exclusivo del estudiante asociado a la cuenta", example = "EST-2026-88")
    private String estudianteId;

    @Schema(description = "Valor base anual del arancel de la carrera sin descuentos ni becas", example = "3850000.0")
    private Double arancelBaseAnual;

    @Schema(description = "Suma histórica total de los costos cargados a la cuenta", example = "11550000.0")
    private Double costoAcumuladoTotal;

    @Schema(description = "Monto financiero que el estudiante adeuda actualmente", example = "450000.0")
    private Double saldoPendiente;

    @Schema(description = "Cantidad de semestres cursados en exceso sobre la duración formal de la carrera", example = "1")
    private Integer semestresExtra;

    @Schema(description = "Porcentaje o factor aplicado para el reajuste anual del arancel", example = "1.045")
    private Double factorReajuste;
    
    @Enumerated(EnumType.STRING)
    @Schema(description = "Estado de cumplimiento financiero de la cuenta", example = "AL_DIA")
    private EstadoCuenta estadoCuenta;

    public enum EstadoCuenta {
        AL_DIA, MOROSO, EN_CONVENIO
    }
}