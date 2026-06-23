package SigueTuCarrera.Arancel.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import SigueTuCarrera.Arancel.model.Arancel;
import SigueTuCarrera.Arancel.repository.ArancelRepository;

@ExtendWith(MockitoExtension.class)
public class ArancelServiceTest {

    @Mock
    private ArancelRepository repository;

    @InjectMocks
    private ArancelService service;

    private Arancel arancelPrueba;

    @BeforeEach
    void setUp() {
        arancelPrueba = new Arancel();
        arancelPrueba.setEstudianteId("12345678-9"); // Mapeado al "rut"
        arancelPrueba.setArancelBaseAnual(4000000.0);
        arancelPrueba.setSemestresExtra(0);
        arancelPrueba.setFactorReajuste(0.05); // 5% de reajuste
        arancelPrueba.setCostoAcumuladoTotal(0.0);
        arancelPrueba.setSaldoPendiente(500000.0);
    }

    @Test
    void testGetAccount_Exitoso() {
        when(repository.findByEstudianteId("12345678-9")).thenReturn(Optional.of(arancelPrueba));

        Arancel resultado = service.getAccount("12345678-9");

        assertNotNull(resultado);
        assertEquals("12345678-9", resultado.getEstudianteId());
    }

    @Test
    void testGetAccount_NoEncontrado() {
        when(repository.findByEstudianteId("99999999-9")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.getAccount("99999999-9");
        });

        assertEquals("Cuenta financiera no encontrada", exception.getMessage());
    }

    @Test
    void testApplyAcademicDelay() {
        when(repository.findByEstudianteId("12345678-9")).thenReturn(Optional.of(arancelPrueba));
        when(repository.save(any(Arancel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Arancel resultado = service.applyAcademicDelay("12345678-9");

        assertNotNull(resultado);
        assertEquals(1, resultado.getSemestresExtra());
        // (4.000.000 / 2) * (1 + 0.05)^1 = 2.100.000
        assertEquals(2100000.0, resultado.getCostoAcumuladoTotal());
        assertEquals(2600000.0, resultado.getSaldoPendiente()); // 500.000 original + 2.100.000
    }

    @Test
    void testRestarSaldo_Normal() {
        when(repository.findByEstudianteId("12345678-9")).thenReturn(Optional.of(arancelPrueba));
        when(repository.save(any(Arancel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Arancel resultado = service.restarSaldo("12345678-9", 200000.0);

        assertNotNull(resultado);
        assertEquals(300000.0, resultado.getSaldoPendiente());
    }

    @Test
    void testRestarSaldo_SaldoNegativoA_Cero() {
        when(repository.findByEstudianteId("12345678-9")).thenReturn(Optional.of(arancelPrueba));
        when(repository.save(any(Arancel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Arancel resultado = service.restarSaldo("12345678-9", 600000.0); // Descuento más de lo pendiente

        assertNotNull(resultado);
        assertEquals(0.0, resultado.getSaldoPendiente());
    }
}