package SigueTuCarrera.Arancel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SigueTuCarrera.Arancel.model.Arancel;

@Repository
public interface ArancelRepository extends JpaRepository<Arancel, Long>{
        Optional<Arancel> findByEstudianteId(String rut);
    
}
