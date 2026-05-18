package SigueTuCarrera.Arancel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SigueTuCarrera.Arancel.model.Arancel;
import SigueTuCarrera.Arancel.repository.ArancelRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ArancelService {
    @Autowired
    private ArancelRepository repository;

    public Arancel getAccount(String rut) {
        return repository.findByEstudianteId(rut)
                .orElseThrow(() -> new RuntimeException("Cuenta financiera no encontrada"));
    }

    // LÓGICA DE CÁLCULO INCREMENTAL
    public Arancel applyAcademicDelay(String rut) {
        Arancel account = getAccount(rut);
        
        // 1. Incrementamos los semestres de retraso
        account.setSemestresExtra(account.getSemestresExtra() + 1);
        
        // 2. Aplicamos la fórmula: costo = arancelBase * (1 + reajuste)^semestresExtra
        double factorReajuste = Math.pow(1 + account.getFactorReajuste(), account.getSemestresExtra());
        double costoSemestreExtra = (account.getArancelBaseAnual() / 2) * factorReajuste;
        
        // 3. Actualizamos el costo acumulado total
        account.setCostoAcumuladoTotal(account.getCostoAcumuladoTotal() + costoSemestreExtra);
        account.setSaldoPendiente(account.getSaldoPendiente() + costoSemestreExtra);
        
        return repository.save(account);
    }
    public Arancel restarSaldo(String rut, Double monto) {
        Arancel account = getAccount(rut);
        double nuevoSaldo = account.getSaldoPendiente() - monto;
        if (nuevoSaldo < 0) {
            nuevoSaldo = 0.0;
        }
        account.setSaldoPendiente(nuevoSaldo);
        return repository.save(account);
}
}
