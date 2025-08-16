package backend.system.reserva.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.system.reserva.Model.Mesas;

@Repository
public interface MesaRepository extends JpaRepository<Mesas, Long> {
    Optional<Mesas> findByNome(String nome);
}
