package SigueTuCarrera.Arancel.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import SigueTuCarrera.Arancel.model.Arancel;
import SigueTuCarrera.Arancel.service.ArancelService;

@WebMvcTest(ArancelController.class)
public class ArancelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ArancelService service;

    private Arancel arancelPrueba;

    @BeforeEach
    void setUp() {
        arancelPrueba = new Arancel();
        arancelPrueba.setEstudianteId("12345678-9");
        arancelPrueba.setArancelBaseAnual(4000000.0);
        arancelPrueba.setSaldoPendiente(500000.0);
    }

    @Test
    void testGetStatus() throws Exception {
        when(service.getAccount("12345678-9")).thenReturn(arancelPrueba);

        mockMvc.perform(get("/api/v1/arancel/12345678-9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estudianteId").value("12345678-9"))
                .andExpect(jsonPath("$.saldoPendiente").value(500000.0));
    }

    @Test
    void testReportDelay() throws Exception {
        when(service.applyAcademicDelay("12345678-9")).thenReturn(arancelPrueba);

        mockMvc.perform(post("/api/v1/arancel/report-delay/12345678-9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estudianteId").value("12345678-9"));
    }

    @Test
    void testDescontarSaldo() throws Exception {
        arancelPrueba.setSaldoPendiente(300000.0); // Simula el saldo restado
        when(service.restarSaldo("12345678-9", 200000.0)).thenReturn(arancelPrueba);

        mockMvc.perform(put("/api/v1/arancel/12345678-9/descontar-saldo")
                .param("monto", "200000.0")) // Inyección de @RequestParam
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.saldoPendiente").value(300000.0));
    }
}