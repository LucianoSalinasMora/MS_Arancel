package SigueTuCarrera.Arancel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import SigueTuCarrera.Arancel.model.Arancel;
import SigueTuCarrera.Arancel.service.ArancelService;

@RestController
@RequestMapping("/api/v1/arancel")
public class ArancelController {
    @Autowired
    private ArancelService service;

    @GetMapping("/{rut}")
    public ResponseEntity<Arancel> getStatus(@PathVariable String rut) {
        return ResponseEntity.ok(service.getAccount(rut));
    }

    @PostMapping("/report-delay/{rut}")
    public ResponseEntity<Arancel> reportDelay(@PathVariable String rut) {
        return ResponseEntity.ok(service.applyAcademicDelay(rut));
    }
    @PutMapping("/{rut}/descontar-saldo")
    public ResponseEntity<Arancel> descontarSaldo(@PathVariable String rut, @RequestParam Double monto) {
    return ResponseEntity.ok(service.restarSaldo(rut, monto)); 
}
}
